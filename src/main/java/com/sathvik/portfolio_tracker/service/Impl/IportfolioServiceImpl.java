package com.sathvik.portfolio_tracker.service.Impl;

import com.sathvik.portfolio_tracker.dto.PortfolioResponseDto;
import com.sathvik.portfolio_tracker.dto.StockAddDto;
import com.sathvik.portfolio_tracker.dto.StockPortfolioDto;
import com.sathvik.portfolio_tracker.dto.StockResponseDto;
import com.sathvik.portfolio_tracker.entity.Portfolio;
import com.sathvik.portfolio_tracker.entity.Stock;
import com.sathvik.portfolio_tracker.entity.User;
import com.sathvik.portfolio_tracker.exception.ResourceAlreadyExistsException;
import com.sathvik.portfolio_tracker.exception.ResourceNotFoundException;
import com.sathvik.portfolio_tracker.repository.PortfolioRepository;
import com.sathvik.portfolio_tracker.repository.StockRepository;
import com.sathvik.portfolio_tracker.repository.UserRepository;
import com.sathvik.portfolio_tracker.service.IAlphavantageService;
import com.sathvik.portfolio_tracker.service.IportfolioService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IportfolioServiceImpl implements IportfolioService {

    private UserRepository userRepository;
    private PortfolioRepository portfolioRepository;

    private IAlphavantageService alphavantageService;

    private StockRepository stockRepository;

    public IportfolioServiceImpl(UserRepository userRepository, PortfolioRepository portfolioRepository, IAlphavantageService alphavantageService, StockRepository stockRepository) {
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
        this.alphavantageService = alphavantageService;
        this.stockRepository = stockRepository;
    }

    @Override
    public PortfolioResponseDto getPortfolio(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        boolean alphaVantageError = false;
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "email", email);
        }

        User user = optionalUser.get();
        PortfolioResponseDto portfolioResponseDto = new PortfolioResponseDto();
        portfolioResponseDto.setName(user.getFirstName() + " " + user.getLastName());
        portfolioResponseDto.setUserId(user.getUserId());

        List<StockPortfolioDto> stockPortfolioDto = getPortfolioData(user);

        BigDecimal investedValue = new BigDecimal(0);
        BigDecimal currentValue = new BigDecimal(0);
        BigDecimal profitLoss = new BigDecimal(0);

        for (StockPortfolioDto stock : stockPortfolioDto) {
            investedValue = investedValue.add((stock.getInvestedValue()).multiply(BigDecimal.valueOf(stock.getQuantity())));
            BigDecimal price = new BigDecimal(0);
            if(!alphaVantageError) {
                try {
                    price = (alphavantageService.getStockPrice(stock.getTicker())).multiply(BigDecimal.valueOf(stock.getQuantity()));
                } catch (Exception e) {
                    alphaVantageError = true;
                }
            }
            currentValue = currentValue.add(price);
        }
        portfolioResponseDto.setInvestedValue(investedValue);
        portfolioResponseDto.setCurrentValue(currentValue);
        portfolioResponseDto.setProfitLoss(currentValue.subtract(investedValue));

        BigDecimal percentageChange = ((currentValue.subtract(investedValue).divide(investedValue, 2)).multiply(BigDecimal.valueOf(100))).setScale(2, BigDecimal.ROUND_HALF_UP);
        portfolioResponseDto.setPercentageChange(percentageChange);

        if(alphaVantageError)
            portfolioResponseDto.setErrorMessage("Unable to current fetch stock price");

        return portfolioResponseDto;
    }


    @Override
    public List<StockResponseDto> getStocks(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "email", email);
        }
        User user = optionalUser.get();
        List<StockPortfolioDto> stockPortfolioDto = getPortfolioData(user);

        List<StockResponseDto> stockResponseDto = new ArrayList<>();

        for (StockPortfolioDto stock : stockPortfolioDto) {
            StockResponseDto stockResponseDto1 = new StockResponseDto();
            stockResponseDto1.setTicker(stock.getTicker());
            stockResponseDto1.setStockName(stock.getStockName());
            stockResponseDto1.setQuantity(stock.getQuantity());
            stockResponseDto1.setInvestedValue(stock.getInvestedValue());
            try {
                stockResponseDto1.setCurrentValue((alphavantageService.getStockPrice(stock.getTicker())));
            } catch (Exception e) {
                stockResponseDto1.setCurrentValue(BigDecimal.valueOf(0));
                stockResponseDto1.setErrorMessage("Unable to current fetch stock price");
            }
            stockResponseDto.add(stockResponseDto1);
        }
        return stockResponseDto;
    }

    @Override
    public StockResponseDto addStock(StockAddDto stockDto) {
        Optional<User> optionalUser = userRepository.findById(stockDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", Integer.toString(stockDto.getUserId()));
        }
        User user = optionalUser.get();

        Optional<Stock> optionalStock = stockRepository.findByTicker(stockDto.getTicker());
        if (optionalStock.isPresent()) {
            throw new ResourceAlreadyExistsException("Stock", "ticker", stockDto.getTicker());
        }

        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUserIdAndStockTicker(user.getUserId(), stockDto.getTicker());
        if (optionalPortfolio.isPresent()) {
            throw new ResourceAlreadyExistsException("Stock", "ticker", stockDto.getTicker());
        }

        Stock newStock = stockRepository.save(new Stock(stockDto.getTicker(),stockDto.getStockName()));

        Portfolio newPortfolio = portfolioRepository.save(new Portfolio(user, newStock, stockDto.getQuantity(), stockDto.getInvestedValue(),stockDto.getPurchaseDate()));

        StockResponseDto result = new StockResponseDto();

        result.setTicker(newPortfolio.getStock().getTicker());
        result.setStockName(newStock.getStockName());
        result.setQuantity(newPortfolio.getQuantity());
        result.setInvestedValue(newPortfolio.getBuyPrice());
        try{
            result.setCurrentValue(alphavantageService.getStockPrice(newPortfolio.getStock().getTicker()));
        } catch (Exception e) {
            result.setCurrentValue(BigDecimal.valueOf(0));
            result.setErrorMessage("Unable to fetch current stock price");
        }

        result.setPurchaseDate(newPortfolio.getPurchaseDate());

        return result;
    }

    @Override
    public boolean updateStock(StockAddDto stockDto) {
        boolean isUpdated = false;
        Optional<User> optionalUser = userRepository.findById(stockDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", Integer.toString(stockDto.getUserId()));
        }
        User user = optionalUser.get();

        Optional<Stock> optionalStock = stockRepository.findByTicker(stockDto.getTicker());
        if (optionalStock.isEmpty()) {
            throw new ResourceNotFoundException("Stock", "ticker", stockDto.getTicker());
        }

        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUserIdAndStockTicker(user.getUserId(), stockDto.getTicker());
        if (optionalPortfolio.isEmpty()) {
            throw new ResourceNotFoundException("Stock", "ticker", stockDto.getTicker());
        }

        Portfolio portfolio = optionalPortfolio.get();
        Stock stock = optionalStock.get();

        portfolio.setQuantity(stockDto.getQuantity());
        portfolio.setBuyPrice(stockDto.getInvestedValue());
        portfolio.setPurchaseDate(stockDto.getPurchaseDate());

        stock.setStockName(stockDto.getStockName());

        stockRepository.save(stock);

        portfolioRepository.save(portfolio);
        isUpdated = true;

        return isUpdated;
    }

    @Override
    public boolean deleteStock(int userId, String ticker) {
        boolean isDeleted = false;
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "Id", Integer.toString(userId));
        }
        User user = optionalUser.get();

        Optional<Stock> optionalStock = stockRepository.findByTicker(ticker);
        if (optionalStock.isEmpty()) {
            throw new ResourceNotFoundException("Stock", "ticker", ticker);
        }
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUserIdAndStockTicker(user.getUserId(), ticker);
        if (optionalPortfolio.isEmpty()) {
            throw new ResourceNotFoundException("Stock", "ticker", ticker);
        }
        Stock stock = optionalStock.get();
        stockRepository.delete(stock);
        Portfolio portfolio = optionalPortfolio.get();
        portfolioRepository.delete(portfolio);
        isDeleted = true;
        return isDeleted;
    }

    public List<StockPortfolioDto> getPortfolioData(User user) {

        List<StockPortfolioDto> StockPortfolioDto = new ArrayList<>();

        List<Object[]> objectData = portfolioRepository.getPortfoliosByUserId(user.getUserId());

        objectData.forEach(p -> {
            StockPortfolioDto.add(new StockPortfolioDto((String) p[0], p[1].toString(), (double) p[2], (BigDecimal) p[3]));
        });
        return StockPortfolioDto;
    }
}

package com.sathvik.portfolio_tracker.service;

import com.sathvik.portfolio_tracker.dto.PortfolioResponseDto;
import com.sathvik.portfolio_tracker.dto.StockAddDto;
import com.sathvik.portfolio_tracker.dto.StockResponseDto;

import java.util.List;

public interface IportfolioService {

    PortfolioResponseDto getPortfolio(String email);

    List<StockResponseDto> getStocks(String email);

    StockResponseDto addStock(StockAddDto stock);

    boolean updateStock(StockAddDto stock);

    boolean deleteStock(int userId,String ticker);
}

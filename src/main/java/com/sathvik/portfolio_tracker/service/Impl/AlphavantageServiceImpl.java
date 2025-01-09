package com.sathvik.portfolio_tracker.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sathvik.portfolio_tracker.dto.GlobalQuoteResponseDto;
import com.sathvik.portfolio_tracker.exception.RateLimitReachedException;
import com.sathvik.portfolio_tracker.service.IAlphavantageService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class AlphavantageServiceImpl implements IAlphavantageService {

    private RestTemplate restTemplate;

    public AlphavantageServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String apiKey = "SBIU8957FKS813NI";
    private final String baseUrl = "https://www.alphavantage.co/query";

    @Override
    @Cacheable(value = "stockPrices", key = "#symbol")
    public BigDecimal getStockPrice(String symbol) {

        String function = "GLOBAL_QUOTE";
        String url = String.format(
                "%s?function=%s&symbol=%s&apikey=%s",
                baseUrl, function, symbol, apiKey
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String response;
        try {
            response = restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (response.contains("Information")) {
//            throw new RateLimitReachedException("Alphavantage requests limit reached, Please try after some time.");
            throw new RuntimeException();
        }

        GlobalQuoteResponseDto globalQuoteResponseDto;
        try {
            globalQuoteResponseDto = objectMapper.readValue(response, GlobalQuoteResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new BigDecimal(globalQuoteResponseDto.getGlobalQuote().getOpen());

    }
}

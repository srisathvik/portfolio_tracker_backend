package com.sathvik.portfolio_tracker.service;

import java.math.BigDecimal;

public interface IAlphavantageService {

    BigDecimal getStockPrice(String symbol);
}

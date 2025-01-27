package com.sathvik.portfolio_tracker.repository;

import com.sathvik.portfolio_tracker.entity.Portfolio;
import com.sathvik.portfolio_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {

    @Query(value = "SELECT " +
            "s.ticker,s.StockName, sum(p.quantity), SUM(p.buyPrice) " +
            "FROM Portfolio p " +
            "join  Stock s " +
            "ON p.stock.stockId = s.stockId " +
            "GROUP BY p.user.userId, s.ticker " +
            "HAVING p.user.userId = :userId")
    List<Object[]> getPortfoliosByUserId(@Param("userId") int userId);

    @Query(value = "SELECT p " +
//            "p.user as user ,p.stock as stock,p.quantity as quantity,p.buyPrice as buyPrice " +
            "FROM Portfolio p " +
            "join  Stock s " +
            "ON p.stock.stockId = s.stockId " +
            "WHERE p.user.userId = :userId AND s.ticker = :ticker")
    Optional<Portfolio> findByUserIdAndStockTicker(@Param("userId") int userId, @Param("ticker") String ticker);
}

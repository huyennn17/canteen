package canteen.demo.service;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import canteen.demo.entity.ConfigPrice;

public class PriceService {
    private DataSource dataSource;
    
    public PriceService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<ConfigPrice> getCurrentPrices() throws SQLException {
        String sql = "SELECT cp.* FROM config_prices cp " +
                    "INNER JOIN (" +
                    "    SELECT meal_type, MAX(effective_datetime) as max_datetime " +
                    "    FROM config_prices " +
                    "    GROUP BY meal_type" +
                    ") latest ON cp.meal_type = latest.meal_type " +
                    "AND cp.effective_datetime = latest.max_datetime " +
                    "ORDER BY cp.meal_type, cp.effective_datetime DESC";
        
        List<ConfigPrice> prices = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                ConfigPrice price = new ConfigPrice();
                price.setId(rs.getInt("id"));
                price.setMealType(rs.getString("meal_type"));
                price.setPrice(rs.getBigDecimal("price"));
                price.setEffectiveDateTime(rs.getTimestamp("effective_datetime"));
                prices.add(price);
            }
        }
        return prices;
    }

    public List<ConfigPrice> getPriceHistory(String mealType) throws SQLException {
        String sql = "SELECT * FROM config_prices WHERE meal_type = ? " +
                    "ORDER BY effective_datetime DESC LIMIT 10";
                    
        List<ConfigPrice> prices = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, mealType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ConfigPrice price = new ConfigPrice();
                    price.setId(rs.getInt("id"));
                    price.setMealType(rs.getString("meal_type"));
                    price.setPrice(rs.getBigDecimal("price"));
                    price.setEffectiveDateTime(rs.getTimestamp("effective_datetime"));
                    prices.add(price);
                }
            }
        }
        return prices;
    }
    
    public void updatePrice(ConfigPrice config) throws SQLException {
        String sql = "INSERT INTO config_prices (meal_type, price, effective_datetime) VALUES (?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, config.getMealType());
            stmt.setBigDecimal(2, config.getPrice());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Use current time
            stmt.executeUpdate();
        }
    }
}

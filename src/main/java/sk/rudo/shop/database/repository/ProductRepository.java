package sk.rudo.shop.database.repository;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import sk.rudo.shop.database.mapper.ProductRowMapper;
import sk.rudo.shop.database.service.api.request.UpdateProductRequest;
import sk.rudo.shop.domain.Product;

import java.sql.*;
import java.time.Instant;
import java.util.List;
@Component
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    private final ProductRowMapper productRowMapper = new ProductRowMapper();

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product getProductById(int id) {
        final String sql = "SELECT * FROM product WHERE product.id = " + id;
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer addProduct(Product product) {
        final String sql = "INSERT INTO product (merchant_id, name, description, price, created, available) values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, product.getMerchantId());
                ps.setString(2, product.getName());
                ps.setString(3, product.getDescription());
                ps.setDouble(4, product.getPrice());
                if (product.getCreated() == null) {
                    product.setCreated(Timestamp.from(Instant.now()));
                }
                ps.setTimestamp(5, product.getCreated());
                ps.setInt(6, product.getAvailable());
                return ps;
            }
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    public List<Product> getAllProducts() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public void updateProduct (int id, UpdateProductRequest updateProductRequest) {
        final String sql = "UPDATE product SET name = ?, description = ?, price = ?, available = ? where id = ?";
        jdbcTemplate.update(sql, updateProductRequest.getName(), updateProductRequest.getDescription(), updateProductRequest.getPrice(), updateProductRequest.getAvailable(), id);
    }

    public void deleteProduct (int id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateAvailable (int id, int newAvailable) {
        final String sql = "UPDATE product SET available = ? WHERE id = ?";
        jdbcTemplate.update(sql, newAvailable, id);
    }
}

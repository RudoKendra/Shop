package sk.rudo.shop.database.mapper;

import org.springframework.jdbc.core.RowMapper;
import sk.rudo.shop.domain.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setMerchantId(rs.getInt("merchant_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setCreated(rs.getTimestamp("created"));
        product.setAvailable(rs.getInt("available"));
        return product;
    }
}

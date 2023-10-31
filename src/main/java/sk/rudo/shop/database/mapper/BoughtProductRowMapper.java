package sk.rudo.shop.database.mapper;

import org.springframework.jdbc.core.RowMapper;
import sk.rudo.shop.domain.BoughtProduct;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoughtProductRowMapper implements RowMapper<BoughtProduct> {
    @Override
    public BoughtProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        BoughtProduct boughtProduct = new BoughtProduct();
        boughtProduct.setProductId(rs.getInt("product_id"));
        boughtProduct.setCustomerId(rs.getInt("customer_id"));
        boughtProduct.setQuantity(rs.getInt("quantity"));
        boughtProduct.setBoughtAt(rs.getTimestamp("bought_at"));
        return boughtProduct;
    }
}

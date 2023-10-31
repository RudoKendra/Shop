package sk.rudo.shop.database.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import sk.rudo.shop.database.mapper.CustomerRowMapper;
import sk.rudo.shop.domain.Customer;

import java.sql.*;
import java.util.List;

@Component
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    public CustomerRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer getCustomerById (int id) {
        final String sql = "SELECT * FROM customer WHERE customer.id = " + id;
        try {
            return jdbcTemplate.queryForObject(sql, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer addNewCustomer (Customer customer) {
        final String sql = "INSERT INTO customer (name, surname, email, address, age, phone_number) values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSurname());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                if (customer.getAge() != null) {
                    ps.setInt(5, customer.getAge());
                } else {
                    ps.setNull (5, Types.INTEGER);
                }
                ps.setString(6, customer.getPhoneNumber());
                return ps;
            }
        }, keyHolder );
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    public List<Customer> getAllCustomers() {
        final String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, customerRowMapper);
    }
}

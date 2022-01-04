package net.javaguides.springboot.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationRowMapper implements RowMapper<Application> {

    @Override
    public Application mapRow(ResultSet rs, int rowNum) throws SQLException {

        Application customer = new Application();
        customer.setId(rs.getLong("id"));
        customer.setName(rs.getString("name"));
        customer.setName(rs.getString("identity"));
        customer.setName(rs.getString("place"));

        return customer;

    }
}
package com.xit.apbboard.dao;

import com.xit.apbboard.model.db.Price;
import com.xit.apbboard.dao.mappers.PriceRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by
 *
 * @author homer
 * @since 12.09.14.
 */
@Service
public class PricesDAO {

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Price> getPriceList(){
        return namedParameterJdbcTemplate.query("select * from prices", new HashMap<String, Object>(), new PriceRowMapper());
    }

    public int getPriceItemId(double price, double priceInRUR){
        HashMap<String, Object> params = new HashMap<>();
        params.put("priceInRUR", priceInRUR);
        params.put("price", price);
        return namedParameterJdbcTemplate.queryForObject("select priceId from prices where price=:price and priceInRUR = :priceInRUR", params, Integer.class);
    }
}

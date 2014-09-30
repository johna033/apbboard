package com.xit.apbboard.dao.mappers;

import com.xit.apbboard.model.db.Price;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by
 *
 * @author homer
 * @since 12.09.14.
 */
public class PriceRowMapper implements RowMapper<Price> {
    @Override
    public Price mapRow(ResultSet rs, int rowNum) throws SQLException {
        Price price = new Price();
        price.priceId = rs.getInt("priceid");
        price.price = rs.getDouble("price");
        price.priceInRUR = rs.getDouble("priceInRUR");
        price.stringOffer = "post for "+price.price+"$ get a "+price.price + "rub itunes gift card";
        return price;
    }
}

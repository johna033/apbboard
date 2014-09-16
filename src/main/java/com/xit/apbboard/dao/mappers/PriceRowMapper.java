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
        price.amountOfSymbols = rs.getInt("amountofsymbols");
        price.price = rs.getDouble("price");
        price.reward = rs.getBoolean("reward");
        price.stringOffer = price.amountOfSymbols +" symbols for "+price.price+"$ "+(price.reward ? "reward included" :"");
        price.priceInRUR = rs.getDouble("priceInRUR");
        return price;
    }
}

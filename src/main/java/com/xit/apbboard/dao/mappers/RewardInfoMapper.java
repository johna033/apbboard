package com.xit.apbboard.dao.mappers;

import com.xit.apbboard.model.app.UserRewardInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by
 *
 * @author homer
 * @since 16.09.14.
 */
public class RewardInfoMapper implements RowMapper<UserRewardInfo> {

    @Override
    public UserRewardInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRewardInfo uri = new UserRewardInfo();
        uri.email = rs.getString("email");
        uri.amount = rs.getDouble("priceInRUR");
        return uri;
    }
}

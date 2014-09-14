package com.xit.apbboard.dao.mappers;

import com.xit.apbboard.controller.dto.AdminUsersResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
public class AdminUserMapper implements RowMapper<AdminUsersResponse> {


    @Override
    public AdminUsersResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdminUsersResponse adminUsersResponse = new AdminUsersResponse();
        adminUsersResponse.email = rs.getString("email");
        adminUsersResponse.userId = rs.getInt("userId");
        adminUsersResponse.time = rs.getLong("creationTime");
        adminUsersResponse.title = rs.getString("bulletinTitle");
        adminUsersResponse.text = rs.getString("bulletinText");
        adminUsersResponse.reviewed = rs.getBoolean("reviewed");
        adminUsersResponse.post = rs.getBoolean("posted");
        adminUsersResponse.reward = rs.getDouble("priceInRUR");
        adminUsersResponse.rewardSent = rs.getBoolean("rewardSent");
        return adminUsersResponse;
    }
}

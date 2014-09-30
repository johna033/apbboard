package com.xit.apbboard.dao.mappers;

import com.xit.apbboard.controller.dto.AdminUsersListResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
public class AdminUserMapper implements RowMapper<AdminUsersListResponse> {


    @Override
    public AdminUsersListResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdminUsersListResponse adminUsersResponse = new AdminUsersListResponse();
        adminUsersResponse.userId = rs.getInt("userId");
        adminUsersResponse.email = rs.getString("email");
        adminUsersResponse.uuid = rs.getString("uuid");
        adminUsersResponse.time = rs.getLong("creationTime");
        adminUsersResponse.username = rs.getString("username");
        adminUsersResponse.paid = rs.getBoolean("paid");
        adminUsersResponse.posted = rs.getBoolean("posted");
        adminUsersResponse.reward = rs.getDouble("priceInRUR");
        adminUsersResponse.rewardSent = rs.getBoolean("rewardSent");
        return adminUsersResponse;
    }
}

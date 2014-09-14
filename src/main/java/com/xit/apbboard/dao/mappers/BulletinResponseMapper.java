package com.xit.apbboard.dao.mappers;

import com.xit.apbboard.controller.dto.BulletinResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by
 *
 * @author homer
 * @since 12.09.14.
 */
public class BulletinResponseMapper implements RowMapper<BulletinResponse> {
    @Override
    public BulletinResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        BulletinResponse bulletinResponse = new BulletinResponse();
        bulletinResponse.text = rs.getString("bulletintext");
        bulletinResponse.title = rs.getString("bulletintitle");
        return bulletinResponse;
    }
}

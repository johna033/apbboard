package com.xit.apbboard.dao;

import com.xit.apbboard.controller.dto.AdminUsersResponse;
import com.xit.apbboard.dao.mappers.AdminUserMapper;
import com.xit.apbboard.dao.mappers.RewardInfoMapper;
import com.xit.apbboard.model.app.UserRewardInfo;
import com.xit.apbboard.model.db.BoardUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
@Service
public class BoardUsersDAO {

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void add(BoardUser boardUser){

        HashMap<String, Object> params = new HashMap<>();
        params.put("email", boardUser.email);
        params.put("uuid", boardUser.uuid);
        params.put("time", boardUser.time);
        params.put("priceItem", boardUser.priceItem);
        namedParameterJdbcTemplate.update("insert into boardusers (email, uuid, creationTime, priceItem) values (:email, :uuid, :time, :priceItem)",params);
    }

    public List<AdminUsersResponse> getUsers(){
       return namedParameterJdbcTemplate.query("select userId, boardusers.uuid, email, creationTime, rewardSent, bulletinText, bulletinTitle," +
               "paid, posted, priceInRUR from boardusers" +
               " inner join bulletins on bulletins.uuid = boardusers.uuid " +
               " inner join prices on boardusers.priceItem = prices.priceid", new HashMap<String, Object>(), new AdminUserMapper());
    }

    public String getEmail(String uuid){
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return namedParameterJdbcTemplate.queryForObject("select email from boardusers where uuid = :uuid",params, String.class);
    }

    public UserRewardInfo getRewardInfo(String uuid){
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return namedParameterJdbcTemplate.query("select email, priceInRUR from boardusers " +
                "join prices on boardusers.priceItem = prices.priceid and boardusers.uuid=:uuid", params, new RewardInfoMapper()).get(0);
    }

    public void updatePaid(String uuid){
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        namedParameterJdbcTemplate.update("update boardusers set paid=true where uuid=:uuid", params);
    }
}

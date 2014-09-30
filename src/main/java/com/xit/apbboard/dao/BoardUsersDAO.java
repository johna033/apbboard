package com.xit.apbboard.dao;

import com.xit.apbboard.controller.dto.AdminUsersListResponse;
import com.xit.apbboard.dao.mappers.AdminUserMapper;
import com.xit.apbboard.dao.mappers.RewardInfoMapper;
import com.xit.apbboard.model.app.UserRewardInfo;
import com.xit.apbboard.model.db.BoardUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        params.put("username", boardUser.username);
        params.put("uuid", boardUser.uuid);
        params.put("time", boardUser.time);
        params.put("priceItem", boardUser.priceItem);
        namedParameterJdbcTemplate.update("insert into boardusers (email, username, uuid, creationTime, priceItem) values (:email, :username, :uuid, :time, :priceItem)",params);
    }
    public void deleteUser(String uuid){
        namedParameterJdbcTemplate.update("delete from boardusers where uuid=:uuid", Collections.singletonMap("uuid", uuid));
    }
    public List<AdminUsersListResponse> getUsers(){
       return namedParameterJdbcTemplate.query("select userId, uuid, email, username, creationTime, rewardSent, " +
               "paid, priceInRUR from boardusers"  +
               " inner join prices on boardusers.priceItem = prices.priceid" +
               " order by creationTime desc",
               new HashMap<String, Object>(), new AdminUserMapper());
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

    public void setPaymentId(String uuid, String paymentID){
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        params.put("paymentID", paymentID);
        namedParameterJdbcTemplate.update("update boardusers set paymentID=:paymentID where uuid=:uuid", params);
    }

    public String getPaymentID(String uuid){
        return namedParameterJdbcTemplate.queryForObject("select paymentID from boardusers where uuid=:uuid", Collections.singletonMap("uuid", uuid), String.class);
    }

    public void updatePaid(String uuid){
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        namedParameterJdbcTemplate.update("update boardusers set paid=true where uuid=:uuid", params);
    }
}

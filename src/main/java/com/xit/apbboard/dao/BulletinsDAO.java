package com.xit.apbboard.dao;

import com.xit.apbboard.controller.dto.BulletinResponse;
import com.xit.apbboard.dao.mappers.BulletinResponseMapper;
import com.xit.apbboard.model.db.BoardUser;
import com.xit.apbboard.model.db.Bulletin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by
 *
 * @author homer
 * @since 12.09.14.
 */
@Service
public class BulletinsDAO {

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void add(Bulletin bulletin){
        HashMap<String, Object> params = new HashMap<>();
        params.put("bulletinTitle", bulletin.bulletinTitle);
        params.put("bulletinText", bulletin.bulletidText);
        params.put("uuid", bulletin.uuid);
        namedParameterJdbcTemplate.update("insert into bulletins (bulletintitle, bulletintext, uuid) values(:bulletinTitle, :bulletinText, :uuid)", params);
    }

    @Transactional
    public List<BulletinResponse> getPartialList(int size, int offset, long currentTime){
        HashMap<String, Object> params = new HashMap<>();
        params.put("size", size);
        params.put("offset", offset);
        params.put("currentTime", currentTime);
        namedParameterJdbcTemplate.update("delete from bulletins where expirationDate <= :currentTime and expirationDate <> -1", params);
        return namedParameterJdbcTemplate.query("select bulletintitle, bulletintext from bulletins where posted=true order by bulletins.expirationDate desc limit :size, :offset", params, new BulletinResponseMapper());
    }

    public int countBulletins(){
        return namedParameterJdbcTemplate.queryForObject("select count(*) from bulletins where posted=true", new HashMap<String, Object>(), Integer.class);
    }

    public void postBulletin(String uuid, long expirationDate){
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        params.put("expires", expirationDate);
        namedParameterJdbcTemplate.update("update bulletins set posted=true, expirationDate=:expires where uuid=:uuid", params);
    }

    @Transactional
    public void add(Bulletin bulletin, BoardUser boardUser){
        add(bulletin);

        HashMap<String, Object> params = new HashMap<>();
        params.put("email", boardUser.email);
        params.put("uuid", boardUser.uuid);
        params.put("time", boardUser.time);
        params.put("priceItem", boardUser.priceItem);
        namedParameterJdbcTemplate.update("insert into boardusers (email, uuid, creationTime, priceItem) values (:email, :uuid, :time, :priceItem)",params);
    }

    @Transactional
    public void deleteFromBulletinsAndUsers(String uuid){
        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        namedParameterJdbcTemplate.update("delete from boardusers where uuid = :uuid", params);
        namedParameterJdbcTemplate.update("delete from bulletins where uuid = :uuid", params);
    }
}

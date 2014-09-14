package com.xit.apbboard.dao;

import com.xit.apbboard.controller.dto.BulletinResponse;
import com.xit.apbboard.dao.mappers.BulletinResponseMapper;
import com.xit.apbboard.model.Bulletin;
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
public class BulletinsDAO {

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void addBulletin(Bulletin bulletin){
        HashMap<String, Object> params = new HashMap<>();
        params.put("bulletinTitle", bulletin.bulletinTitle);
        params.put("bulletinText", bulletin.bulletidText);

        namedParameterJdbcTemplate.update("insert into bulletins bulletintitle, bulletintext, reviewed, expirationDate values(:bulletinTitle, :bulletinText, false, -1)", params);
    }

    public List<BulletinResponse> getPartialList(int size, int offset){
        HashMap<String, Object> params = new HashMap<>();
        params.put("size", size);
        params.put("offset", offset);
        return namedParameterJdbcTemplate.query("select bulletintitle, bulletintext from bulletins where reviewed=true order by bulletins.expirationDate desc limit :size, :offset", params, new BulletinResponseMapper());
    }

    public int countBulletins(){
        return namedParameterJdbcTemplate.queryForObject("select count(*) from bulletins where reviewed=true", new HashMap<String, Object>(), Integer.class);
    }
}

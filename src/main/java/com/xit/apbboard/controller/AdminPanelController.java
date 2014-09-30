package com.xit.apbboard.controller;

import com.xit.apbboard.controller.dto.AdminUsersListResponse;
import com.xit.apbboard.controller.dto.PostBulletinRequest;
import com.xit.apbboard.controller.dto.SendRewardRequest;
import com.xit.apbboard.dao.BoardUsersDAO;
import com.xit.apbboard.dao.BulletinsDAO;
import com.xit.apbboard.model.app.UserRewardInfo;
import com.xit.apbboard.services.MailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
@RestController
@RequestMapping("/admin")
public class AdminPanelController {

    @Autowired
    public BoardUsersDAO boardUsersDAO;

    @Autowired
    public BulletinsDAO bulletinsDAO;

    @Autowired
    public MailNotificationService mns;

    private static final long BULLETIN_TTL = 30*24*3600*1000L;

    @RequestMapping(value = "/clients")
    public List<AdminUsersListResponse> getUsers(){
        return boardUsersDAO.getUsers();
    }

    @RequestMapping(value = "/sendreward", method = RequestMethod.POST)
    public void sendRewardToUser(@RequestBody SendRewardRequest sendRewardRequest){
        UserRewardInfo userRewardInfo = boardUsersDAO.getRewardInfo(sendRewardRequest.uuid);
        mns.sendReward(userRewardInfo.email, sendRewardRequest.giftCode, userRewardInfo.amount);
    }

    @RequestMapping(value="/post", method =  RequestMethod.POST)
    public void postBulletin(@RequestBody PostBulletinRequest pbr){

        bulletinsDAO.postBulletin(pbr.uuid, System.currentTimeMillis() + BULLETIN_TTL);
    }

}

package com.xit.apbboard.controller;

import com.xit.apbboard.controller.dto.AdminUsersResponse;
import com.xit.apbboard.dao.BoardUsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
@RestController
public class AdminPanelController {

    @Autowired
    public BoardUsersDAO buDAO;


    public List<AdminUsersResponse> getUsers(){
        return buDAO.getUsers();
    }



}

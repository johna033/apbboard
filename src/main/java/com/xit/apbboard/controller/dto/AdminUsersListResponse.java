package com.xit.apbboard.controller.dto;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
public class AdminUsersListResponse extends ListPageBase {
    public int userId;
    public String uuid;
    public String email;
    public long time;
    public String username;
    public boolean paid;
    public boolean posted;
    public double reward;
    public boolean rewardSent;
}

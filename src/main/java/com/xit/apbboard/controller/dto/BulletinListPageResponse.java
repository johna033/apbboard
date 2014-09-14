package com.xit.apbboard.controller.dto;

import java.util.List;

/**
 * Created by
 *
 * @author homer
 * @since 12.09.14.
 */
public class BulletinListPageResponse extends ListPageBase{

    public List<BulletinResponse> listResponse;

    public BulletinListPageResponse(List<BulletinResponse> listResponse, int bulletinsPerPage, int totalNumberOfBulletins, int offset, int size){
        this.listResponse = listResponse;
        this.itemsPerPage = bulletinsPerPage;
        this.itemsCount = totalNumberOfBulletins;
        this.offset = offset;
        this.size = size;
    }
}

package com.xit.apbboard.controller;

import com.xit.apbboard.controller.dto.BulletinListPageResponse;
import com.xit.apbboard.dao.BulletinsDAO;
import com.xit.apbboard.dao.PricesDAO;
import com.xit.apbboard.exceptions.PagingBoundsException;
import com.xit.apbboard.model.db.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by
 *
 * @author homer
 * @since 12.09.14.
 */
@RestController
public class BulletinsController {

    @Autowired
    public PricesDAO pricesDAO;

    @Autowired
    public BulletinsDAO bulletinsDAO;

    private static final int BULLETINS_PER_PAGE = 20;

    @RequestMapping(value="/prices", method = RequestMethod.GET)
    public List<Price> getPriceList(){

        return pricesDAO.getPriceList();

    }

    @RequestMapping(value="/bulletins", method = RequestMethod.GET)
    public BulletinListPageResponse getFirstPage(){
        return new BulletinListPageResponse(bulletinsDAO.getPartialList(0, BULLETINS_PER_PAGE, System.currentTimeMillis()), BULLETINS_PER_PAGE, bulletinsDAO.countBulletins(), 0, BULLETINS_PER_PAGE);
    }

    @RequestMapping(value="/bulletins/{offset}/{size}", method = RequestMethod.GET)
    public BulletinListPageResponse getFirstPage(@PathVariable("offset") int offset,
                                                 @PathVariable("size") int size){
        validatePagingParams(size, offset,bulletinsDAO.countBulletins());
        return new BulletinListPageResponse(bulletinsDAO.getPartialList(offset, size, System.currentTimeMillis()), BULLETINS_PER_PAGE, bulletinsDAO.countBulletins(), offset, size);
    }

    private void validatePagingParams(int size, int offset, int bulletinsCount){
            if(size > bulletinsCount || offset > bulletinsCount){
                throw new PagingBoundsException();
            }
    }

}

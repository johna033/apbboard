package com.xit.apbboard.controller.dto;

import java.io.Serializable;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
public class BaseResponse implements Serializable {
    public String errorDesc;

    public BaseResponse(String errorDesc){
        this.errorDesc = errorDesc;
    }

}

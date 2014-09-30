package com.xit.apbboard.controller.dto;

import java.io.Serializable;

/**
 * Created by
 *
 * @author Dmitry Ivanov
 * @since 30.09.14.
 */
public class BaseResponse implements Serializable{
    public String error;
    public BaseResponse(String error) {
        this.error = error;
    }
}

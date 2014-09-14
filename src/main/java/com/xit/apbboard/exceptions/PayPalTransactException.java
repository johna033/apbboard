package com.xit.apbboard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class PayPalTransactException extends RuntimeException {

    public PayPalTransactException(Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
}

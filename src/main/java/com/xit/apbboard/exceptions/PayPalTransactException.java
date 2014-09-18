package com.xit.apbboard.exceptions;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */

public class PayPalTransactException extends RuntimeException {

    public PayPalTransactException(Exception e){
        System.out.println(e.getCause());
        e.printStackTrace();
    }
}

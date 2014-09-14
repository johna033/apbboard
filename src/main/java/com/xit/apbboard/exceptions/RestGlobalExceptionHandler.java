package com.xit.apbboard.exceptions;

import com.xit.apbboard.controller.dto.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
@ControllerAdvice
public class RestGlobalExceptionHandler {
    @ExceptionHandler(PayPalTransactException.class)
    @ResponseBody
    public BaseResponse handlePayPalRESTError(){
        return new BaseResponse("Paypal internal error");
    }
}

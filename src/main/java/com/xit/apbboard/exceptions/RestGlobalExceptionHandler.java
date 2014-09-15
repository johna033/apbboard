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
        return new BaseResponse("PayPal internal error");
    }

    @ExceptionHandler(PagingBoundsException.class)
    @ResponseBody
    public BaseResponse handlePagingError(){
        return new BaseResponse("Incorrect paging parameters!");
    }

    @ExceptionHandler(PriceItemNotFoundException.class)
    @ResponseBody
    public BaseResponse handlePriceItemError(){
        return new BaseResponse("Incorrect number of symbols / price combination!");
    }

    @ExceptionHandler(InvalidPaymentRequestException.class)
    @ResponseBody
    public BaseResponse handlePaymentRequestError(){
        return new BaseResponse("Incorrect post form!");
    }
}

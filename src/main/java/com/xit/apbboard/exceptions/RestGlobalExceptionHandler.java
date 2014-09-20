package com.xit.apbboard.exceptions;

import com.xit.apbboard.controller.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
@ControllerAdvice
public class RestGlobalExceptionHandler {
    @ExceptionHandler(PayPalTransactException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse handlePayPalRESTError(){
        return new BaseResponse("PayPal internal error");
    }

    @ExceptionHandler(PagingBoundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handlePagingError(){
        return new BaseResponse("Incorrect paging parameters!");
    }

    @ExceptionHandler(PriceItemNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handlePriceItemError(){
        return new BaseResponse("Incorrect number of symbols / price combination!");
    }

    @ExceptionHandler(InvalidPaymentRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handlePaymentRequestError(){
        return new BaseResponse("Incorrect post form!");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse handleGeneralError(){
        return new BaseResponse("We are sorry, something went wrong while processing your request, please try again later");
    }
}

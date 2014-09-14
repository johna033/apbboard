package com.xit.apbboard.controller;

import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.*;

import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.sun.deploy.net.HttpResponse;
import com.xit.apbboard.controller.dto.BaseResponse;
import com.xit.apbboard.controller.dto.PaymentRequest;
import com.xit.apbboard.exceptions.PayPalTransactException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
@RestController
@RequestMapping("paypal")
public class PayPalController {

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public BaseResponse payWithPayPal(@RequestBody PaymentRequest pr, HttpServletResponse httpResponse){
        try{
            createPayment(26.75, "uuid", "desc");
        }catch(PayPalRESTException e){
            throw new PayPalTransactException(e);
        }
        //httpResponse.sendRedirect("/");
        return new BaseResponse("No error");
    }

    //@RequestMapping(value = "/orderapproved/{uuid}")

    public Payment createPayment(double orderAmount, String orderUuid, String orderDesc)
            throws PayPalRESTException {

        OAuthTokenCredential tokenCredential = new OAuthTokenCredential("AQEs6xCpYIYfdgMPalU6ZNbA217WAYUzmDygj7ZmglidF_AdTvMh8XD1x-wA", "EHcxsBCpFcOo0M7ioPhsIn25CN3aNCm0Mz6496w-LDGQzRjGKmMEphXKRwa6");
        String accessToken = tokenCredential.getAccessToken();

        Payment payment = new Payment();

            AmountDetails amountDetails = new AmountDetails();
            amountDetails.setShipping("0");
            amountDetails.setSubtotal(Double.toString(orderAmount));
            amountDetails.setTax("0");

            Amount amount = new Amount();
            amount.setCurrency("USD");
            amount.setTotal(String.format("%.2f", orderAmount));
            amount.setDetails(amountDetails);

            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl("http://apbboard.com/rest/paypal/cancel/"+orderUuid);
            redirectUrls.setReturnUrl("http://apbboard.com/post.html/paypal/orderapproved/"+orderUuid);

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription(orderDesc);
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");

            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setRedirectUrls(redirectUrls);
            payment.setTransactions(transactions);

        APIContext apiContext = new APIContext(accessToken, UUID.randomUUID().toString());
        return payment.create(apiContext);
    }

}

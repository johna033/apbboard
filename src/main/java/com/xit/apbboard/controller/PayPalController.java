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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class PayPalController {

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public BaseResponse payWithPayPal(@RequestBody PaymentRequest pr, HttpResponse httpResponse){

        return new BaseResponse("No error");
    }

    public static Payment createPayment(double orderAmount, String orderUuid, String orderDesc)
            throws PayPalRESTException {

        OAuthTokenCredential tokenCredential = new OAuthTokenCredential("<CLIENT_ID>", "<CLIENT_SECRET>");
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
            redirectUrls.setCancelUrl("http://apbboard.com/rest/cancel/"+orderUuid);
            redirectUrls.setReturnUrl("http://apbboard.com/post.html/orderapproved/"+orderUuid);

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

        APIContext apiContext = new APIContext(accessToken, orderUuid);
        return payment.create(apiContext);
    }

}

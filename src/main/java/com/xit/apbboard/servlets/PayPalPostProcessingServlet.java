package com.xit.apbboard.servlets;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.xit.apbboard.services.PayPalAccessToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by
 *
 * @author homer
 * @since 19.09.14.
 */
public class PayPalPostProcessingServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String payerId = request.getParameter("PayerID");
        String uuid = request.getParameter("uuid");
        if (request.getRequestURI().contains("execute")) {
            handleExecutePayment(uuid, payerId);
            response.sendRedirect("/rest/paypal/final/execute/"+uuid);
        }

        if (request.getRequestURI().contains("cancel")) {
            response.sendRedirect("/rest/paypal/final/cancel/"+uuid);
        }
    }

    private void handleExecutePayment(String orderId, String payerId) throws IOException {

        // Construct a payment for complete payment execution
        Payment payment = new Payment();
        payment.setId(orderId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        try {

            // set access token
            String accessToken = PayPalAccessToken.tokenGenerator.generateToken();
            String requestId = UUID.randomUUID().toString();
            APIContext apiContext = new APIContext(accessToken, requestId);
            payment.execute(apiContext, paymentExecute);
        } catch (PayPalRESTException pex) {
        }
    }
}

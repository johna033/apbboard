package com.xit.apbboard.servlets;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.xit.apbboard.dao.BoardUsersDAO;
import com.xit.apbboard.services.PayPalAccessToken;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
            Object myDao = context.getBean(BoardUsersDAO.class);
            handleExecutePayment(uuid, payerId, ((BoardUsersDAO)myDao).getPaymentID(uuid));
            response.sendRedirect("/rest/paypal/final/execute/" + uuid);
        }

        if (request.getRequestURI().contains("cancel")) {
            response.sendRedirect("/rest/paypal/final/cancel/"+uuid);
        }
    }

    private void handleExecutePayment(String orderId, String payerId, String paymentID) throws IOException {

        // Construct a payment for complete payment execution
        try {

            // set access token
            String accessToken = PayPalAccessToken.tokenGenerator.generateToken();
            String requestId = UUID.randomUUID().toString();

            Payment payment = Payment.get(accessToken, paymentID);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerId);
            APIContext apiContext = new APIContext(accessToken, requestId);
            payment.execute(apiContext, paymentExecute);
        } catch (PayPalRESTException pex) {
        }
    }
}

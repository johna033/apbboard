package com.xit.apbboard.controller;

import com.paypal.api.payments.*;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.PayPalResource;
import com.xit.apbboard.controller.dto.BaseResponse;
import com.xit.apbboard.controller.dto.PaymentApprovalLink;
import com.xit.apbboard.controller.dto.PaymentRequest;
import com.xit.apbboard.dao.BoardUsersDAO;
import com.xit.apbboard.dao.BulletinsDAO;
import com.xit.apbboard.dao.PricesDAO;
import com.xit.apbboard.exceptions.InvalidPaymentRequestException;
import com.xit.apbboard.exceptions.PayPalTransactException;
import com.xit.apbboard.exceptions.PriceItemNotFoundException;
import com.xit.apbboard.model.db.BoardUser;
import com.xit.apbboard.model.db.Bulletin;
import com.xit.apbboard.services.MailNotificationService;
import com.xit.apbboard.services.PayPalAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by
 *
 * @author homer
 * @since 14.09.14.
 */
@RestController
@RequestMapping("paypal")
public class PayPalController {

    @Autowired
    public PricesDAO pricesDAO;

    @Autowired
    public BoardUsersDAO boardUsersDAO;

    @Autowired
    public BulletinsDAO bulletinsDAO;

    @Autowired
    public MailNotificationService mns;


    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public PaymentApprovalLink payWithPayPal(@RequestBody PaymentRequest pr, HttpServletResponse httpResponse) {
        int priceItemId = pricesDAO.getPriceItemId(pr.numberOfSymbols, pr.payment);

        if (priceItemId == 0) {
            throw new PriceItemNotFoundException();
        }
        validatePaymentRequest(pr);
        String newBulletinId = UUID.randomUUID().toString();

        BoardUser boardUser = new BoardUser();
        boardUser.email = pr.email;
        boardUser.priceItem = priceItemId;
        boardUser.uuid = newBulletinId;
        boardUser.time = System.currentTimeMillis();

        Bulletin bulletin = new Bulletin();
        bulletin.bulletidText = pr.text;
        bulletin.bulletinTitle = pr.title;
        bulletin.uuid = newBulletinId;

        bulletinsDAO.add(bulletin, boardUser);
        try {
            Payment payment = createPayment(pr.payment, newBulletinId, "APBBoard: " + pr.numberOfSymbols + " symbols for " + pr.payment + "$");
            return new PaymentApprovalLink(getApprovalURL(payment));

        } catch (PayPalRESTException e) {
            throw new PayPalTransactException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PaymentApprovalLink("http://localhost:8080/post.html");
    }

    @RequestMapping(value = "/final/cancel/{uuid}")
    public void orderCanceled(@PathVariable("uuid") String uuid,
                              HttpServletResponse response) throws IOException {
        bulletinsDAO.deleteFromBulletinsAndUsers(uuid);
        response.sendRedirect("/index.html");
    }

    @RequestMapping(value = "/final/execute/{uuid}")
    public void orderApproved(@PathVariable("uuid") String uuid,
                                      HttpServletResponse response) throws IOException {
        mns.sendPurchaseNotification(boardUsersDAO.getEmail(uuid));
        boardUsersDAO.updatePaid(uuid);
        response.sendRedirect("/index.html");
    }

    private void validatePaymentRequest(PaymentRequest paymentRequest){
        if(paymentRequest.email == null){
            throw new InvalidPaymentRequestException();
        }
    }

    private Payment createPayment(double orderAmount, String orderUuid, String orderDesc)
            throws PayPalRESTException {

        Payment payment = new Payment();

        AmountDetails amountDetails = new AmountDetails();
        amountDetails.setShipping("0");
        amountDetails.setSubtotal(Double.toString(orderAmount));
        amountDetails.setTax("0");

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format(Locale.US, "%.2f", orderAmount));
        amount.setDetails(amountDetails);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://apbboard.com/paypal/cancel/?uuid=" + orderUuid);
        redirectUrls.setReturnUrl("http://apbboard.com/paypal/execute/?uuid=" + orderUuid);

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

        APIContext apiContext = new APIContext(PayPalAccessToken.tokenGenerator.generateToken(), UUID.randomUUID().toString());
        return payment.create(apiContext);
    }

    private String getApprovalURL(Payment payment)
            throws UnsupportedEncodingException {
        String redirectUrl = null;
        List<Link> links = payment.getLinks();
        for (Link l : links) {
            if (l.getRel().equalsIgnoreCase("approval_url")) {
                redirectUrl = URLDecoder
                        .decode(l.getHref(), "UTF-8");
                break;
            }
        }
        return redirectUrl;
    }

}

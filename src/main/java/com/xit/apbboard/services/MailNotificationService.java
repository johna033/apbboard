package com.xit.apbboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by
 *
 * @author homer
 * @since 15.09.14.
 */

@Service
public class MailNotificationService {

    @Autowired
    public MailSender mailSender;

    public void sendPurchaseNotification(String email){
        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom("noreply@apbboard.com");
                message.setTo(email);
                message.setSubject("You recent purchase at APBBoard.com");
                message.setText("<b>Dear client,</b><br/>thank you for your purchase. Your advertisement will be reviewed and posted any time now. <br/> If there was any reward" +
                        " associated with your purchase, you will be receiving in the next couple of hours. <br/> Best regards, APBBOARD.COM Support Team.", true);
            }
        };
        ((JavaMailSenderImpl)mailSender).send(mimeMessagePreparator);
    }

    public void sendReward(String email, String reward){

        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom("noreply@apbboard.com");
                message.setTo(email);
                message.setSubject("Reward from APBBoard.com");
                message.setText("<b>Dear client,</b><br/>Here is your iTunes gift card redemption code:"+reward+" <br/> Best regards, APBBOARD.COM Support Team.", true);
            }
        };
        ((JavaMailSenderImpl)mailSender).send(mimeMessagePreparator);

    }
}

package ifmo.blss.service;

import ifmo.blss.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class Worker {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendMail(Message messageInfo) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("ksumatova178@gmail.com");
        helper.setTo(messageInfo.getEmailTo());
        helper.setSubject(messageInfo.getSubject());
        helper.setText(messageInfo.getText(), true);

        javaMailSender.send(message);
    }

}


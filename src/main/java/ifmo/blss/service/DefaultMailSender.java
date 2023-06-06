package ifmo.blss.service;


import ifmo.blss.entity.Message;
import ifmo.blss.service.interfaces.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class DefaultMailSender implements SendEmail {
    @Autowired
    private JavaMailSender senderMails;

    @Override
    public void sendMail(Message message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(message.getEmailTo());
        simpleMailMessage.setSubject(message.getSubject());
        simpleMailMessage.setText(message.getText());
        senderMails.send(simpleMailMessage);
    }
}

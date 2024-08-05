package lk.earth.earthuniversity.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        mailSender.send(message);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true for multipart
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true to send as HTML

            // Add the logo image as an attachment
            ClassPathResource logo = new ClassPathResource("static/logo_img.png"); // Place your logo in src/main/resources/static
            helper.addInline("logo", logo); // "logo" is the content ID to reference in HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception
        }
    }
}

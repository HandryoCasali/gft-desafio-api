package br.com.gft.noticias.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void enviarEmail(EmailModel email) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email.getText(), true);
            helper.setSubject(email.getSubject());
            helper.setFrom(email.getEmailFrom());
            helper.setTo(email.getEmailTo());
        
            sender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

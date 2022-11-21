package br.com.gft.noticias.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void enviarEmail(EmailModel email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(email.getSubject());
        message.setFrom(email.getEmailFrom());
        message.setTo(email.getEmailTo());
        message.setText(email.getText());
        try {
            sender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

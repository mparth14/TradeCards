package dal.asdc.tradecards.Service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service class for sending emails in the Trade Cards application.
 *
 * <p>Provides functionality to send emails using the configured {@link org.springframework.mail.javamail.JavaMailSender}.</p>
 *
 * <p>Two constructors are available: a default constructor for Spring bean initialization
 * and a parameterized constructor for specifying sender, receiver, subject, and body at creation.</p>
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Service
@Getter
@Setter
public class EmailService {

    private String sender;
    private String receiver;
    private String subject;
    private String body;

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailService() {
        // Default constructor for Spring bean initialization
    }

    public EmailService(String sender, String receiver, String subject, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    // Getter and setter methods for sender, receiver, subject, and body

    public void sendEmail(EmailContent emailContent) {
        if (emailContent.getTo() != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailContent.getTo());
            message.setSubject(emailContent.getSubject());
            message.setText(emailContent.getText());
            javaMailSender.send(message);
        } else {
            // Handle the case when the 'to' address is null, e.g., throw an exception or log an error.
        }
    }
}

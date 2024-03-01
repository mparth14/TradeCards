package dal.asdc.tradecards;

import dal.asdc.tradecards.Service.EmailContent;
import dal.asdc.tradecards.Service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    private EmailContent validEmailContent;
    private EmailContent invalidEmailContent;

    @Before
    public void setUp() {
        emailService.setJavaMailSender(javaMailSender);
        validEmailContent = new EmailContent("test@example.com", "Test Subject", "Test Body");
        invalidEmailContent = new EmailContent(null, "Invalid Subject", "Invalid Body");
    }

    @Test
    public void sendEmail_ValidEmailContent_SuccessfullySent() {
        emailService.sendEmail(validEmailContent);

        // Verify that the JavaMailSender's send method was called with the expected arguments
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));

        // Additional assertions can be added based on the specific behavior you want to test
    }

    @Test
    public void sendEmail_InvalidEmailContent_HandleNullToAddress() {
        emailService.sendEmail(invalidEmailContent);

        // Verify that the JavaMailSender's send method was not called
        verify(javaMailSender, never()).send((MimeMessage) any());

        // Additional assertions can be added based on the specific behavior you want to test
    }

    // Additional test cases can be added to cover different scenarios and edge cases
}

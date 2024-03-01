package dal.asdc.tradecards.Service;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the content of an email in the Trade Cards application.
 *
 * <p>Encapsulates the recipient's address, subject, and text body.</p>
 *
 * <p>Generated getter and setter methods provided by {@link lombok.Getter} and {@link lombok.Setter}.</p>
 *
 *  @author Harshpreet Singh
 *  @author Parth Modi
 */

@Getter
@Setter
public class EmailContent {

    private String to;
    private String subject;
    private String text;

    public EmailContent() {
        // Default constructor
    }

    public EmailContent(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
}

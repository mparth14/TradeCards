package dal.asdc.tradecards.Model.DTO;


/**
 * The EditUserRequestDTO class represents a data transfer object (DTO)
 * for editing user information in the Trade Cards application.
 *
 * <p>This class encapsulates the details of user information that can be edited,
 * including email ID, first name, last name, and password.</p>
 *
 * <p>The class provides getter and setter methods for accessing and modifying these properties.</p>
 *
 * @author Parth Modi
 */

public class EditUserRequestDTO {
    private String emailID;
    private String firstName;
    private String lastName;
    private String password;

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

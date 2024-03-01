package dal.asdc.tradecards.Model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDTO {
    private String emailID;
    private String password;
    private String FirstName;
    private String LastName;
}

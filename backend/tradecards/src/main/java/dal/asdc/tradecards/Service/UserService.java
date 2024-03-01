package dal.asdc.tradecards.Service;

import java.util.HashMap;
import java.util.List;
import dal.asdc.tradecards.Model.DAO.UserDao;
import dal.asdc.tradecards.Model.DTO.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing user-related operations in the Trade Cards application.
 * Extends {@link org.springframework.security.core.userdetails.UserDetailsService}.
 * Annotated with {@link org.springframework.stereotype.Service}.
 *
 * <p>Implementations of this interface should provide concrete logic for user creation, login,
 * password reset, OTP verification, setting a new password, account verification, and user retrieval.</p>
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */

@Service
public interface UserService extends UserDetailsService {

    public HashMap<String, Object> create(UserSignUpDTO userSignUpDTO) throws Exception;

    public UserDao loadUserByEmailID(String emailID);
    public Object login(UserLoginDTO userLoginDTO) throws Exception;


    public HashMap<String, Object> forgetPasswordRequest(ForgetPasswordDTO forgetPasswordDTO) throws Exception;;

    public HashMap<String, Object> OTPVerification(VerifyOTPDTO verifyOTPDTO) throws Exception;

    public HashMap<String, Object> setPassword(NewPasswordDTO newPasswordDTO) throws Exception;

    public Object verifyAccount(String bearerToken, VerifyAccountDTO verifyAccountDTO) throws Exception;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    public List<UserDao> getAllUsers();

    public Object getUserByUsername(String emailID);

    public UserDao updateUser(EditUserRequestDTO updateUser);

    public UserDao getUserByUserId(int userid);

}

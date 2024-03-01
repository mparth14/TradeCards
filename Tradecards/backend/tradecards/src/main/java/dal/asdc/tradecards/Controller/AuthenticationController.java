package dal.asdc.tradecards.Controller;

import java.util.HashMap;

import dal.asdc.tradecards.Model.DTO.*;
import dal.asdc.tradecards.Service.EmailContent;
import dal.asdc.tradecards.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import dal.asdc.tradecards.Exception.DuplicateEntryException;
import dal.asdc.tradecards.Exception.InvalidAccountCredentialsException;
import dal.asdc.tradecards.Service.EmailService;

/**
 * The AuthenticationController class handles authentication-related operations
 * such as user signup, login, account verification, password recovery, OTP verification,
 * and setting a new password.
 *
 * @author Parth Modi
 */

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private EmailContent emailContent = new EmailContent();

    @PostMapping("/signup")
    public ResponseEntity<?> doSignup(@RequestBody UserSignUpDTO userSignUpDTO) throws Exception {
        try {
            HashMap<String, Object> claims = userService.create(userSignUpDTO);
            emailContent.setTo((String) claims.get("email"));
            emailContent.setSubject("Trade Cards Team - Verify your account");
            emailContent.setText("Your OTP for account verification is: " + claims.get("otp"));
            emailService.sendEmail(emailContent);
            return ResponseEntity.status(HttpStatus.CREATED).body(claims);
        } catch(DuplicateEntryException error) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch(Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody UserLoginDTO userLoginDTO) throws Exception {
        try {
            authenticate(userLoginDTO.getEmailId(), userLoginDTO.getPassword());
            return ResponseEntity.ok(userService.login(userLoginDTO));
        } catch (InvalidAccountCredentialsException error) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        } catch(Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestHeader("Authorization") String bearerToken, @RequestBody VerifyAccountDTO verifyAccountDTO) throws Exception {
        try {
            return ResponseEntity.ok(userService.verifyAccount(bearerToken, verifyAccountDTO));
        } catch(Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/forget-password-request")
    public ResponseEntity<?> forgetPasswordRequest(@RequestBody ForgetPasswordDTO forgetPasswordDTO) throws Exception {
        try {
            HashMap<String, Object> tokenClaims = userService.forgetPasswordRequest(forgetPasswordDTO);
            emailContent.setTo((String) tokenClaims.get("emailID"));
            emailContent.setSubject("TradeCards - Forget Password Request");
            emailContent.setText("Your OTP for forget password is: " + tokenClaims.get("otp"));
            emailService.sendEmail(emailContent);
            HashMap<String, Object> responseClaims = new HashMap<String, Object>();
            responseClaims.put("token", tokenClaims.get("token"));
            responseClaims.put("email", tokenClaims.get("emailID"));
            return ResponseEntity.ok(responseClaims);
        } catch(Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> OTPVerification(@RequestBody VerifyOTPDTO verifyOTPDTO) throws Exception {
        try {
            return ResponseEntity.ok(userService.OTPVerification(verifyOTPDTO));
        } catch(Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/set-new-password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDTO newPasswordDTO) {
        try {
            HashMap<String, Object> response = userService.setPassword(newPasswordDTO);
            return ResponseEntity.ok(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User is disabled", e);
        } catch (BadCredentialsException e) {
            throw new InvalidAccountCredentialsException("Invalid Credentials", e);
        }
    }
}

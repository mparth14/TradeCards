package dal.asdc.tradecards.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import dal.asdc.tradecards.Exception.DuplicateEntryException;
import dal.asdc.tradecards.Exception.OTPVerificationFailed;
import dal.asdc.tradecards.Exception.OTPVerificationFailedException;
import dal.asdc.tradecards.Model.DTO.*;
import dal.asdc.tradecards.Service.UserService;
import dal.asdc.tradecards.Utility.JWTTokenUtil;
import dal.asdc.tradecards.Utility.UtilityFunctions;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dal.asdc.tradecards.Model.DAO.UserDao;
import dal.asdc.tradecards.Repository.UserRepository;

/**
 * Implementation of the {@link dal.asdc.tradecards.Service.UserService} interface
 * providing functionality related to user management.
 *
 *
 * @author Harshpreet Singh
 * @author Parth Modi
 */


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    private UtilityFunctions utilityFunctions;

    private int userOTP = 0;

    public void setUserOTP(int otp){
        this.userOTP = otp;
    }

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //public UserServiceImpl () {}
    @Autowired
    public UserServiceImpl(UserRepository userRepository, JWTTokenUtil jwtTokenUtil, UtilityFunctions utilityFunctions) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.utilityFunctions = utilityFunctions;
    }

    public UserServiceImpl() {

    }

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    @Override
    public HashMap<String, Object> create(UserSignUpDTO userSignUpDTO) throws Exception {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userSignUpDTO.setPassword(passwordEncoder.encode(userSignUpDTO.getPassword()));
            UserDao userDao = new UserDao(userSignUpDTO.getFirstName(), userSignUpDTO.getLastName(),
                    userSignUpDTO.getEmailID(), userSignUpDTO.getPassword());
            userRepository.save(userDao);
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("emailID", userSignUpDTO.getEmailID());
            claims.put("first name", userSignUpDTO.getFirstName());
            claims.put("last name", userSignUpDTO.getLastName());
            int otp = (int) Math.floor(100000 + Math.random() * 900000);
            claims.put("otp", otp);
            String jwtToken = jwtTokenUtil.generateToken(claims);
            claims.put("token", jwtToken);
            return claims;
        } catch(DataIntegrityViolationException error) {
            throw new DuplicateEntryException("User with same email id already exists", new Exception(error.getMessage()));
        }
    }


    @Override
    public Object getUserByUsername(String emailID) {
        UserDao userDao = userRepository.findByEmailID(emailID);
        return userRepository.findById(userDao.getEmailID());
    }

    @Override
    public Object login(UserLoginDTO userLoginDTO) throws Exception {
        UserDao userDao = userRepository.findByEmailID(userLoginDTO.getEmailId());
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", userDao.getEmailID());
        claims.put("firstName", userDao.getFirstName());
        claims.put("lastName", userDao.getLastName());
        String jwtToken = jwtTokenUtil.generateToken(claims);
        claims.put("token", jwtToken);
        claims.put("userId", userDao.getUserid());
        return claims;
    }

    @Override
    public HashMap<String, Object> forgetPasswordRequest(ForgetPasswordDTO forgetPasswordDTO) throws Exception {
        UserDao userDao = userRepository.findByEmailID(forgetPasswordDTO.getEmailID());
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("emailID", userDao.getEmailID());
        claims.put("otp", utilityFunctions.generateOTP());
        claims.put("first name", userDao.getFirstName());
        String jwtToken = jwtTokenUtil.generateFifteenMinuteExpiryToken(claims);
        claims.put("token", jwtToken);
        userOTP = (int) claims.get("otp");
        return claims;
    }

    @Override
    public HashMap<String, Object> OTPVerification(VerifyOTPDTO verifyOTPDTO) throws Exception {
        if(userOTP == verifyOTPDTO.getOtp()) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "OTP verification successful");
            return response;
        }
        else {
            throw new OTPVerificationFailedException("OTP does not match", new Exception("OTP does not match", null));
        }
    }

    @Override
    public HashMap<String, Object> setPassword(NewPasswordDTO newPasswordDTO) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userRepository.setPassword(newPasswordDTO.getEmailID(), passwordEncoder.encode(newPasswordDTO.getNewPassword()));
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Password changed successfully");
        return response;
    }

    @Override
    public Object verifyAccount(String token, VerifyAccountDTO verifyAccountDTO) throws Exception {
        Claims tokenClaims = jwtTokenUtil.getAllClaimsFromToken(token.substring(7));
        String otp = tokenClaims.get("otp").toString();
        if(otp.equalsIgnoreCase(verifyAccountDTO.getOtp())) {
            userRepository.setIsVerified((String) tokenClaims.get("username"));
            HashMap<String, Object> claims = new HashMap<>();
            String jwtToken = jwtTokenUtil.generateToken(tokenClaims);
            claims.put("message", "Account Successfully Verified");
            claims.put("token", jwtToken);
            return claims;
        }
        else {
            throw new OTPVerificationFailed("OTP does not match", new Exception("OTP does not match", null));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao userDao = userRepository.findByEmailID(username);
        if (userDao == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(userDao.getEmailID())
                .password(userDao.getPassword())
                .build();
    }

    @Override
    public UserDao loadUserByEmailID(String emailID) throws UsernameNotFoundException {
        Optional<UserDao> userDao = Optional.ofNullable(userRepository.findByEmailID(emailID));
        if (userDao.isEmpty()) {
            return null;
        }
        return userDao.get();
    }

    public UserDao updateUser(EditUserRequestDTO updatedUser){
        String emailID = updatedUser.getEmailID();
        UserDao existingUser = userRepository.findByEmailID(emailID);
        if (existingUser == null) {
            throw new UsernameNotFoundException("User not found with emailID: " + emailID);
        }
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(updatedUser.getPassword());
        existingUser.setPassword(hashedPassword);

        UserDao updatedUserFromDB = userRepository.save(existingUser);

        return updatedUserFromDB;
    }


    @Override
    public List<UserDao> getAllUsers() {
        return (List<UserDao>)userRepository.findAll();
    }

    @Override
    public UserDao getUserByUserId(int userid) {
        Optional<UserDao> userDao = userRepository.findById(String.valueOf(userid));

        if (userDao.isPresent()) {
            return userDao.get();
        } else {
            return null;
        }
    }
}

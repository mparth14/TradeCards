package dal.asdc.tradecards.Service.impl;

import dal.asdc.tradecards.Exception.DuplicateEntryException;
import dal.asdc.tradecards.Exception.OTPVerificationFailed;
import dal.asdc.tradecards.Exception.OTPVerificationFailedException;
import dal.asdc.tradecards.Model.DAO.UserDao;
import dal.asdc.tradecards.Model.DTO.*;
import dal.asdc.tradecards.Repository.UserRepository;
import dal.asdc.tradecards.Service.UserService;
import dal.asdc.tradecards.Utility.JWTTokenUtil;
import dal.asdc.tradecards.Utility.UtilityFunctions;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import dal.asdc.tradecards.Utility.JWTTokenUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Mock
    private JWTTokenUtil jwtTokenUtil;

    @Mock
    private UtilityFunctions utilityFunctions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtTokenUtil = mock(JWTTokenUtil.class);
        userService = new UserServiceImpl(userRepository, jwtTokenUtil, utilityFunctions);
    }


    @Test
    @DisplayName("Testing create user method")
    public void testCreateUser() throws Exception {

        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setFirstName("Harhpreet");
        userSignUpDTO.setLastName("Singh");
        userSignUpDTO.setEmailID("abc@gmail.com");
        userSignUpDTO.setPassword("password123");

        when(userRepository.save(any(UserDao.class))).thenReturn(new UserDao());
        when(userRepository.findByEmailID("abc@gmail.com")).thenReturn(null);

        when(jwtTokenUtil.generateToken(any())).thenReturn("mocked_token");
        when(utilityFunctions.generateOTP()).thenReturn(123456);

        assertDoesNotThrow(() -> userService.create(userSignUpDTO));
    }

    @Test
    @DisplayName("Testing get user by username method")
    public void testGetUserByUsername() {
        String emailID = "test@abcd.com";

        UserDao mockUser = new UserDao();
        mockUser.setEmailID(emailID);
        mockUser.setFirstName("Harsh");
        mockUser.setLastName("Singh");

        when(userRepository.findByEmailID(emailID)).thenReturn(mockUser);
        when(userRepository.findById(emailID)).thenReturn(Optional.of(mockUser));

        Object result = userService.getUserByUsername(emailID);

        // Assertion
        assertNotNull(result);
        assertTrue(result instanceof Optional);

        Optional<UserDao> optionalUser = (Optional<UserDao>) result;
        assertTrue(optionalUser.isPresent());

        UserDao retrievedUser = optionalUser.get();
        assertEquals(emailID, retrievedUser.getEmailID());
        assertEquals("Harsh", retrievedUser.getFirstName());
        assertEquals("Singh", retrievedUser.getLastName());
    }

    @Test
    @DisplayName("Testing login method")
    public void testLogin() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmailId("harsh@gmail.com");

        UserDao mockUser = new UserDao();
        mockUser.setEmailID("harsh@gmail.com");
        mockUser.setFirstName("Harsh");
        mockUser.setUserid(123);

        when(userRepository.findByEmailID(userLoginDTO.getEmailId())).thenReturn(mockUser);
        when(jwtTokenUtil.generateToken(any())).thenReturn("mocked_token");

        Object result = userService.login(userLoginDTO);

        // Assertion
        assertNotNull(result);
        assertTrue(result instanceof HashMap);

        HashMap<String, Object> loginResult = (HashMap<String, Object>) result;

        assertEquals("harsh@gmail.com", loginResult.get("email"));
        assertEquals("Harsh", loginResult.get("firstName"));
        assertNotNull(loginResult.get("token"));
        assertEquals(123, loginResult.get("userId"));
    }

    @Test
    @DisplayName("Testing verify account method - successful verification")
    public void testVerifyAccountSuccess() throws Exception {
        String token = "mocked_token";
        VerifyAccountDTO verifyAccountDTO = new VerifyAccountDTO();
        verifyAccountDTO.setOtp("123456");

        Claims tokenClaims = mock(Claims.class);
        doReturn(tokenClaims).when(jwtTokenUtil).getAllClaimsFromToken(token.substring(7));
        when(tokenClaims.get("otp")).thenReturn("123456");
        when(tokenClaims.get("username")).thenReturn("harsh@gmail.com");
        when(jwtTokenUtil.generateToken(tokenClaims)).thenReturn("mocked_new_token");
        when(userRepository.setIsVerified("harsh@gmail.com")).thenReturn(1);

        Object result = userService.verifyAccount(token, verifyAccountDTO);

        // Assertion
        assertNotNull(result);
        assertTrue(result instanceof HashMap);

        HashMap<String, Object> verifyResult = (HashMap<String, Object>) result;

        assertEquals("Account Successfully Verified", verifyResult.get("message"));
        assertNotNull(verifyResult.get("token"));
        assertEquals("mocked_new_token", verifyResult.get("token"));
    }


    @Test
    @DisplayName("Testing verify account method - unsuccessful verification")
    public void testVerifyAccountFailure() {
        String token = "mocked_token";
        VerifyAccountDTO verifyAccountDTO = new VerifyAccountDTO();
        verifyAccountDTO.setOtp("654321");

        Claims tokenClaims = mock(Claims.class);
        when(jwtTokenUtil.getAllClaimsFromToken(token.substring(7))).thenReturn(tokenClaims);
        when(tokenClaims.get("otp")).thenReturn("123456");

        // Act and assert
        assertThrows(OTPVerificationFailed.class, () -> {
            userService.verifyAccount(token, verifyAccountDTO);
        });
    }

    @Test
    @DisplayName("Testing load user by username method")
    public void testLoadUserByUsername() {
        String username = "harsh@gmail.com";

        UserDao mockUser = new UserDao();
        mockUser.setEmailID("harsh@gmail.com");
        mockUser.setPassword("hashed_password");
        when(userRepository.findByEmailID(username)).thenReturn(mockUser);

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("hashed_password", userDetails.getPassword());
    }

    @Test
    @DisplayName("Testing load user by username method - user not found")
    public void testLoadUserByUsernameNotFound() {
        String username = "nonexistent@gmail.com";

        when(userRepository.findByEmailID(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });
    }

    @Test
    @DisplayName("Testing get all users method")
    public void testGetAllUsers() {
        UserDao user1 = new UserDao("Harshpreet", "Singh", "harsh@gmail.com", "password");
        UserDao user2 = new UserDao("Adam", "Smith", "adam@smith.com", "password");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDao> userList = userService.getAllUsers();

        assertNotNull(userList);
        assertEquals(2, userList.size());
        assertEquals("Harshpreet", userList.get(0).getFirstName());
        assertEquals("Singh", userList.get(0).getLastName());
        assertEquals("harsh@gmail.com", userList.get(0).getEmailID());
        assertEquals("Adam", userList.get(1).getFirstName());
        assertEquals("Smith", userList.get(1).getLastName());
        assertEquals("adam@smith.com", userList.get(1).getEmailID());
    }

    @Test
    @DisplayName("Testing update user method")
    public void testUpdateUser() {
        EditUserRequestDTO updatedUser = new EditUserRequestDTO();
        updatedUser.setEmailID("test@example.com");
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Doe");
        updatedUser.setPassword("newPassword");

        UserDao existingUser = new UserDao();
        existingUser.setEmailID("test@example.com");
        existingUser.setFirstName("OldFirstName");
        existingUser.setLastName("OldLastName");

        when(userRepository.findByEmailID("test@example.com")).thenReturn(existingUser);
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserDao updatedUserFromDB = userService.updateUser(updatedUser);

        assertEquals("test@example.com", updatedUserFromDB.getEmailID());
        assertEquals("John", updatedUserFromDB.getFirstName());
        assertEquals("Doe", updatedUserFromDB.getLastName());
    }

    @Test
    @DisplayName("Testing update user method for non existent user")
    public void testUpdateUserNotFound() {
        EditUserRequestDTO updatedUser = new EditUserRequestDTO();
        updatedUser.setEmailID("nonexistent@example.com");

        when(userRepository.findByEmailID("nonexistent@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.updateUser(updatedUser);
        });
    }

    @Test
    @DisplayName("Testing load user by their email id method")
    public void testLoadUserByEmailIDUserFound() {
        UserDao mockUser = new UserDao();
        mockUser.setEmailID("test@example.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");

        when(userRepository.findByEmailID("test@example.com")).thenReturn(mockUser);

        UserDao loadedUser = userService.loadUserByEmailID("test@example.com");

        assertNotNull(loadedUser);
        assertEquals("test@example.com", loadedUser.getEmailID());
        assertEquals("John", loadedUser.getFirstName());
    }

    @Test
    @DisplayName("Testing load user by their email id method by providing non existent user")
    public void testLoadUserByEmailIDUserNotFound() {
        String emailID = "nonexistent@example.com";

        when(userRepository.findByEmailID(emailID)).thenReturn(null);

        assertNull(userService.loadUserByEmailID(emailID));
    }

    @Test
    @DisplayName("Testing get user by userid method for valid user")
    public void testGetUserByUserIdUserFound() {
        int userid = 1;
        UserDao mockUser = new UserDao();
        mockUser.setUserid(userid);
        mockUser.setEmailID("harsh@harsh.com");
        mockUser.setFirstName("harshpreet");
        mockUser.setLastName("singh");

        when(userRepository.findById(String.valueOf(userid))).thenReturn(Optional.of(mockUser));

        UserDao loadedUser = userService.getUserByUserId(userid);

        assertNotNull(loadedUser);
        assertEquals(userid, loadedUser.getUserid());
        assertEquals("harsh@harsh.com", loadedUser.getEmailID());
        assertEquals("harshpreet", loadedUser.getFirstName());
    }


    @Test
    @DisplayName("Testing get user by userid method for non-existent user")
    public void testGetUserByUserIdUserNotFound() {
        int userid = 888;

        when(userRepository.findById(String.valueOf(userid))).thenReturn(Optional.empty());

        UserDao loadedUser = userService.getUserByUserId(userid);

        assertNull(loadedUser);
    }

    @Test
    @DisplayName("testing forgot password api response")
    void testForgetPasswordRequest() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);
        JWTTokenUtil jwtTokenUtil = mock(JWTTokenUtil.class);
        UtilityFunctions utilityFunctions = mock(UtilityFunctions.class);

        UserDao userDao = new UserDao();
        userDao.setEmailID("test@example.com");
        userDao.setFirstName("John");

        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setEmailID("test@example.com");

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("emailID", userDao.getEmailID());
        claims.put("otp", 123456); // Mocked OTP value
        claims.put("first name", userDao.getFirstName());
        String jwtToken = "mockedJwtToken";
        claims.put("token", jwtToken);

        // Creating a mock UserService
        UserServiceImpl userService = new UserServiceImpl(userRepository, jwtTokenUtil, utilityFunctions);

        when(userRepository.findByEmailID("test@example.com")).thenReturn(userDao);
        when(utilityFunctions.generateOTP()).thenReturn(123456);
        when(jwtTokenUtil.generateFifteenMinuteExpiryToken(ArgumentMatchers.anyMap())).thenReturn(jwtToken);

        HashMap<String, Object> result = userService.forgetPasswordRequest(forgetPasswordDTO);

        verify(userRepository, times(1)).findByEmailID("test@example.com");
        verify(utilityFunctions, times(1)).generateOTP();
        verify(jwtTokenUtil, times(1)).generateFifteenMinuteExpiryToken(ArgumentMatchers.anyMap());

        assertEquals(claims, result);
    }

    @Test
    void testOTPVerificationWithCorrectOTP() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserOTP(123456);

        // Creating a DTO with correct OTP
        VerifyOTPDTO correctOtpDTO = new VerifyOTPDTO();
        correctOtpDTO.setOtp(123456);

        try {
            HashMap<String, Object> result = userService.OTPVerification(correctOtpDTO);
            assertTrue(result.containsKey("message"));
            assertEquals("OTP verification successful", result.get("message"));
        } catch (Exception e) {
            fail("Exception should not be thrown for correct OTP");
        }
    }

    @Test
    void testOTPVerificationWithIncorrectOTP() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserOTP(123456);

        // Creating a DTO with incorrect OTP
        VerifyOTPDTO incorrectOtpDTO = new VerifyOTPDTO();
        incorrectOtpDTO.setOtp(654321); // Set an incorrect OTP for testing

        assertThrows(OTPVerificationFailedException.class, () -> {
            userService.OTPVerification(incorrectOtpDTO);
        });
    }

    @Test
    void testSetPassword() {
        UserRepository userRepository = mock(UserRepository.class);
        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

        // Creating a NewPasswordDTO
        NewPasswordDTO newPasswordDTO = new NewPasswordDTO();
        newPasswordDTO.setEmailID("test@example.com");
        newPasswordDTO.setNewPassword("newPassword");

        // Creating a mock UserService
        UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder);

        // Mocking the BCryptPasswordEncoder behavior
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        // Calling the method under test
        try {
            HashMap<String, Object> result = userService.setPassword(newPasswordDTO);
            assertTrue(result.containsKey("message"));
            assertEquals("Password changed successfully", result.get("message"));
        } catch (Exception e) {
            // If an exception is thrown, fail the test
            fail("Exception should not be thrown for setting password");
        }
    }
}

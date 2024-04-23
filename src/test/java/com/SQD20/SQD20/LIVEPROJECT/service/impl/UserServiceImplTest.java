package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.AppUser;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.config.JwtService;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.UsernameNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.AuthenticationRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.RegisterRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.UpdateUserRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.AuthenticationResponse;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.RegisterResponse;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.UserResponse;
import com.SQD20.SQD20.LIVEPROJECT.repository.UserRepository;
import com.SQD20.SQD20.LIVEPROJECT.service.EmailService;
import com.SQD20.SQD20.LIVEPROJECT.service.impl.UserServiceImpl;
import com.SQD20.SQD20.LIVEPROJECT.utils.UserUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmailService emailService;

    @Mock
    UpdateUserRequest updateUserRequest;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password");
        registerRequest.setPhoneNumber("123456789");

        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenAnswer(invocation -> {
            return invocation.<AppUser>getArgument(0);
        });

        RegisterResponse response = userService.register(registerRequest);

        assertEquals(UserUtils.ACCOUNT_CREATION_SUCCESS_CODE, response.getResponseCode());
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("john.doe@example.com");
        authenticationRequest.setPassword("password");

        AppUser user = new AppUser();
        user.setEmail("john.doe@example.com");

        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(null);

        AuthenticationResponse response = userService.authenticate(authenticationRequest);

        assertEquals(UserUtils.LOGIN_SUCCESS_CODE, response.getResponseCode());
        assertEquals("john.doe@example.com", response.getEmail());
    }

    @Test
    void testVerifyEmail_UserVerified() {

        String token = "mockToken";
        String username = "mockUser";


        when(jwtService.getUserName(token)).thenReturn(username);

        AppUser user = new AppUser();
        user.setEmail(username);
        user.setIsEnabled(true);
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        String result = userService.verifyEmail(token);


        assertEquals("User has been verified!", result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testVerifyEmail_UserNotVerified() {

        String token = "mockToken";
        String username = "mockUser";


        when(jwtService.getUserName(token)).thenReturn(username);


        AppUser user = new AppUser();
        user.setEmail(username);
        user.setIsEnabled(false);
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));


        String result = userService.verifyEmail(token);


        assertEquals("Email Verified", result);
        verify(userRepository, times(1)).save(user);
        assertTrue(user.isEnabled());
    }

    @Test
    void testVerifyEmail_UserNotFound() {

        String token = "mockToken";
        String username = "mockUser";


        when(jwtService.getUserName(token)).thenReturn(username);


        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());


        String result = userService.verifyEmail(token);


        assertEquals("User does not exist", result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testVerifyEmail_InvalidToken() {

        String token = "invalidToken";


        when(jwtService.getUserName(token)).thenReturn(null);


        String result = userService.verifyEmail(token);


        assertEquals("Invalid token or broken link", result);
        verify(userRepository, never()).findByEmail(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testResendEmailVerification_Success() {
        String email = "test@example.com";
        String token = "your_generated_token";

        AppUser user = new AppUser();
        user.setEmail(email);
        user.setIsEnabled(false); // Assuming the user is not enabled initially

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(token);

        ResponseEntity<?> responseEntity = userService.resendEmailVerification(email);

        assert(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
        assert(responseEntity.getBody().equals("Verification email resent successfully."));
    }

    @Test
    public void testResendEmailVerification_UserNotExist() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = userService.resendEmailVerification(email);

        assert(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        assert(responseEntity.getBody().equals("User with the provided email does not exist."));
    }

    @Test
    public void testResendEmailVerification_EmailAlreadyVerified() {
        String email = "verified@example.com";

        AppUser user = new AppUser();
        user.setEmail(email);
        user.setIsEnabled(true); // Assuming the user's email is already verified

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        ResponseEntity<?> responseEntity = userService.resendEmailVerification(email);

        assert(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        assert(responseEntity.getBody().equals("User's email is already verified."));
    }
    @Test
    void testEditUser() {
        // Mocking data
        Long userId = 1L;
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Grace");
        registerRequest.setPhoneNumber("09051527654");

        AppUser existingUser = new AppUser();


        // Mocking repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Testing editUser method
        UserResponse editedUser = userService.editUser(userId, updateUserRequest);

        // Assertions
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
        verifyNoMoreInteractions(userRepository);
        // Add more assertions as needed
    }

    @Test
    void testViewUser() {
        // Mocking data
        Long userId = 1L;

        AppUser existingUser = new AppUser();
        existingUser.setFirstName("Praise");
        existingUser.setLastName("JohnPual");
        existingUser.setPhoneNumber("08140996323");

        // Mocking repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Testing getUserById method
        UserResponse userResponse = userService.viewUser(userId);

        // Assertions
        verify(userRepository).findById(userId);
        // Add assertions to check userResponse fields against existingUser fields
        // Add more assertions as needed
    }

    @Test
    void testViewUser_UserNotFound() {
        // Mocking data
        Long userId = 1L;

        // Mocking repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Testing getUserById method for user not found scenario
        try {
            userService.viewUser(userId);
            fail("Expected UsernameNotFoundException was not thrown");
        } catch (UsernameNotFoundException e) {
            // UsernameNotFoundException thrown as expected
        }

        // Assertions
        verify(userRepository).findById(userId);
        // Add more assertions as needed
    }


    @Test
    public void testForgotPasswordEmail_UserNotFound() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.forgotPasswordEmail(email);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User with the provided email does not exist.", response.getBody());
    }

    @Test
    public void testForgotPasswordEmail_EmailSentSuccessfully() throws MessagingException, JsonProcessingException {
        String email = "existing@example.com";
        AppUser user = new AppUser();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("token");

        ResponseEntity<?> response = userService.forgotPasswordEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password reset email sent successfully.", response.getBody());
        // Verify that emailService.sendHtmlMessageForResetPassword was called with correct arguments
        verify(emailService).sendHtmlMessageForResetPassword(any(), eq(user.getFirstName()), any());
    }

    // Write similar tests for other scenarios of forgotPasswordEmail

    @Test
    public void testVerifyForgotPasswordEmail_UserNotFound() throws IOException {
        String token = "invalidToken";
        when(jwtService.getUserName(token)).thenReturn("nonexistent@example.com");
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        String result = userService.verifyForgotPasswordEmail(token);

        assertEquals("User does not exist", result);
    }

    // Write similar tests for other scenarios of verifyForgotPasswordEmail

    @Test
    public void testForgotPassword_PasswordResetSuccessfully() {
        String email = "existing@example.com";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";
        AppUser user = new AppUser();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("encryptedPassword");

        String result = userService.forgotPassword(email, newPassword, confirmPassword);

        assertEquals("Password reset successfully. You can now login with your new password.", result);
        // Verify that userRepository.updateUserPassword was called with correct arguments
        verify(userRepository).updateUserPassword(eq(email), eq("encryptedPassword"));
    }

    @Test
    public void testForgotPassword_PasswordsDoNotMatch() {
        String email = "existing@example.com";
        String newPassword = "newPassword";
        String confirmPassword = "differentPassword";
        AppUser user = new AppUser();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        String result = userService.forgotPassword(email, newPassword, confirmPassword);

        assertEquals("New password and confirm password do not match.", result);
        // Verify that userRepository.updateUserPassword was not called
        verify(userRepository, never()).updateUserPassword(anyString(), anyString());
    }


}

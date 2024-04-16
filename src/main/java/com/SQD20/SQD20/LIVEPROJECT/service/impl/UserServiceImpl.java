package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.AppUser;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.config.JwtService;
import com.SQD20.SQD20.LIVEPROJECT.infrastructure.exception.UsernameNotFoundException;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.AuthenticationRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.EmailDetails;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.RegisterRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.RegisterResponse;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.AuthenticationResponse;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.UserResponse;
import com.SQD20.SQD20.LIVEPROJECT.repository.UserRepository;
import com.SQD20.SQD20.LIVEPROJECT.service.EmailService;
import com.SQD20.SQD20.LIVEPROJECT.service.UserService;
import com.SQD20.SQD20.LIVEPROJECT.utils.EmailTemplate;
import com.SQD20.SQD20.LIVEPROJECT.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
//@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${baseUrl}")
    private String baseUrl;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Override
    public RegisterResponse register(@Valid RegisterRequest registerRequest) {

        // Validate email format
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(registerRequest.getEmail());
        if (!matcher.matches()) {
            return RegisterResponse.builder()
                    .responseCode(UserUtils.INVALID_EMAIL_FORMAT_CODE)
                    .responseMessage(UserUtils.INVALID_EMAIL_FORMAT_MESSAGE)
                    .build();
        }

        // Validate email domain
        String[] emailParts = registerRequest.getEmail().split("\\.");
        if (emailParts.length < 2 || emailParts[emailParts.length - 1].length() < 2) {
            System.out.println("Invalid email domain. Email parts: " + Arrays.toString(emailParts));

            return RegisterResponse.builder()
                    .responseCode(UserUtils.INVALID_EMAIL_DOMAIN_CODE)
                    .responseMessage(UserUtils.INVALID_EMAIL_DOMAIN_MESSAGE)
                    .build();
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())){
            return RegisterResponse.builder()
                    .responseCode(UserUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(UserUtils.ACCOUNT_EXISTS_MESSAGE)
                    .build();
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        AppUser newUser = AppUser.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(encodedPassword) // Encode the password
                .phoneNumber(registerRequest.getPhoneNumber())
                .isEnabled(false)
                .build();
        AppUser savedUser = userRepository.save(newUser);


        String jwtToken = jwtService.generateToken(newUser);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT VERIFICATION")
                .messageBody(EmailTemplate.getEmailMessage(savedUser.getFirstName(), baseUrl, jwtToken))
                .build();

        emailService.sendEmailAlert(emailDetails);
        return RegisterResponse.builder()
                .responseCode(UserUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(UserUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .build();
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        AppUser user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .responseCode(UserUtils.LOGIN_SUCCESS_CODE)
                .responseMessage(UserUtils.LOGIN_SUCCESS_MESSAGE)
                .email(user.getEmail())
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public String verifyEmail(String token) {
        String username = jwtService.getUserName(token);
//        log.info(username);
        if(username != null){
            Optional<AppUser> user = userRepository.findByEmail(username);
//            log.info(username);
            if(user.isPresent()){
                if(user.get().isEnabled()){
                    return "User has been verified!";
                } else {
                    user.get().setIsEnabled(true);
                    userRepository.save(user.get());
//                    log.info("Got to verification line");
                    return "Email Verified";
                }

            } else{
                return "User does not exist";
            }
        }
        return "Invalid token or broken link";
    }

    @Override
    public ResponseEntity<?> resendEmailVerification(String email) {
        // Check if the user exists
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User with the provided email does not exist.");
        }
        AppUser user = optionalUser.get();

        // Check if the user's email is already verified
        if (user.isEnabled()) {
            return ResponseEntity.badRequest().body("User's email is already verified.");
        }

        // Generate a new verification token
        String jwtToken = jwtService.generateToken(user);

        // Send the verification email
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("ACCOUNT VERIFICATION")
                .messageBody(EmailTemplate.getEmailMessage(user.getFirstName(), baseUrl, jwtToken))
                .build();

        try {
            emailService.sendEmailAlert(emailDetails);
            return ResponseEntity.ok().body("Verification email resent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to resend verification email. Please try again later.");
        }
    }

    @Override
    public UserResponse editUser(Long id, RegisterRequest registerRequest) {
        AppUser appUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("No User associated with " + id));
        appUser.setFirstName(registerRequest.getFirstName());
        appUser.setLastName(registerRequest.getLastName());
        appUser.setPhoneNumber(registerRequest.getPhoneNumber());
        userRepository.save(appUser);
        return UserResponse.builder()
                .responseMessage(UserUtils.USER_UPDATE_MESSAGE)
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .phoneNumber(appUser.getPhoneNumber())
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        AppUser viewUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserResponse.builder()
                .responseMessage(UserUtils.USER_DETAILS_MESSAGE)
                .firstName(viewUser.getFirstName())
                .lastName(viewUser.getLastName())
                .phoneNumber(viewUser.getPhoneNumber())
                .email(viewUser.getEmail())
                .taskList(viewUser.getTaskList())
                .build();
    }


}

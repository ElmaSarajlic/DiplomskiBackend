package ba.edu.ibu.DigitalMarketplace.core.service;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.DigitalMarketplace.core.model.User;
import ba.edu.ibu.DigitalMarketplace.core.repository.UserRepository;
import ba.edu.ibu.DigitalMarketplace.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.regex.Pattern;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private boolean isValidBiHPhoneNumber(String phoneNumber) {
        return Pattern.matches("^06[0-9]{1}[ -]?[0-9]{3}[ -]?[0-9]{3,4}$", phoneNumber);
    }


    public UserDTO signUp(UserRequestDTO userRequestDTO) throws NoSuchAlgorithmException {
        // check if user already exists with the given email
        userRepository.findByEmail(userRequestDTO.getEmail())
                .ifPresent(u -> {
                    throw new IllegalStateException("Email already in use");
                });

        if (isPasswordPwned(userRequestDTO.getPassword())) {
            throw new IllegalStateException("The provided password has been compromised, please choose a different one");
        }


        if (!isValidBiHPhoneNumber(userRequestDTO.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number must be in BiH format");
        }

        // create new user if not

        userRequestDTO.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword()));
        User user = userRepository.save(userRequestDTO.toEntity());

        return new UserDTO(user);
    }

    public LoginDTO signIn(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist."));

        String jwt = jwtService.generateToken(user, user.getId(), user.getUserType()); // passing the user ID and usertype


        return new LoginDTO(jwt);
    }

    public boolean updateUserPassword(String id, PasswordRequestDTO request) throws NoSuchAlgorithmException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalStateException("Old password does not match");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalStateException("New password cannot be the same as the old password");
        }
        if (isPasswordPwned(request.getNewPassword()) ){
            throw new IllegalStateException("The provided new password has been compromised, please choose a different one.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return true;
    }

    public boolean isPasswordPwned(String password) throws NoSuchAlgorithmException {
        String sha1 = sha1Hex(password);
        String prefix = sha1.substring(0, 5);
        String suffix = sha1.substring(5).toUpperCase();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.pwnedpasswords.com/range/" + prefix;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().contains(suffix);
        }

        return false;
    }
    private String sha1Hex(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        md.reset();

        byte[] buffer = input.getBytes(StandardCharsets.UTF_8);

        md.update(buffer);

        byte[] digest = md.digest();

        String hex = "";

        try (Formatter formatter = new Formatter()) {
            for (byte b : digest) {
                formatter.format("%02x", b);
            }

            hex = formatter.toString();
        }

        return hex;
    }
}


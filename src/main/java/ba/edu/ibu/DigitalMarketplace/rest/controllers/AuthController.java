package ba.edu.ibu.DigitalMarketplace.rest.controllers;

import ba.edu.ibu.DigitalMarketplace.core.service.AuthService;
import ba.edu.ibu.DigitalMarketplace.core.service.NotificationService;
import ba.edu.ibu.DigitalMarketplace.rest.dto.LoginDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.LoginRequestDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.UserDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/auth")
@SecurityRequirement(name = "JWT Security")
public class AuthController {
    @Autowired
    private NotificationService notificationService;
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequestDTO user) throws NoSuchAlgorithmException {
        UserDTO registeredUser = authService.signUp(user);

        String email = user.getEmail();
        String message = "A " + email + " has created an account.";

        try {
            notificationService.sendMessageToAdmins(message);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.signIn(loginRequest));
    }
}
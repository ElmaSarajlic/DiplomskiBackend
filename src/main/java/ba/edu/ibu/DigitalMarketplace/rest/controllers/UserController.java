package ba.edu.ibu.DigitalMarketplace.rest.controllers;

import ba.edu.ibu.DigitalMarketplace.core.service.AuthService;
import ba.edu.ibu.DigitalMarketplace.core.service.UserService;
import ba.edu.ibu.DigitalMarketplace.rest.dto.PasswordRequestDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.UserDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "JWT Security")
public class UserController {

    private final UserService userService;
    private final AuthService authService;


    public UserController(UserService userService,  AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/")
    @PreAuthorize("hasAnyAuthority( 'REGISTERED', 'ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }



    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @PreAuthorize("hasAnyAuthority('REGISTERED', 'ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    @PreAuthorize("hasAnyAuthority( 'REGISTERED', 'ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserRequestDTO user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/filter")
    @PreAuthorize("hasAnyAuthority('REGISTERED', 'ADMIN')")
    public ResponseEntity<UserDTO> filterUser(@RequestParam String email) {
        return ResponseEntity.ok(userService.filterByEmail(email));
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/password/{id}")
    public ResponseEntity<String> updateUserPassword(@PathVariable String id, @RequestBody PasswordRequestDTO passwordUpdateRequest) {
        try {
            authService.updateUserPassword(id, passwordUpdateRequest);
            return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
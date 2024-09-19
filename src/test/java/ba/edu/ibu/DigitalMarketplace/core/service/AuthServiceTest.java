package ba.edu.ibu.DigitalMarketplace.core.service;

import static ba.edu.ibu.DigitalMarketplace.core.model.enums.UserType.REGISTERED;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.junit.jupiter.api.extension.ExtendWith;

import ba.edu.ibu.DigitalMarketplace.core.model.User;
import ba.edu.ibu.DigitalMarketplace.core.repository.UserRepository;
import ba.edu.ibu.DigitalMarketplace.rest.dto.LoginRequestDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.LoginDTO;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;


    @InjectMocks
    private AuthService authService;
    @BeforeEach
    void setUp() {
        // Manually set the mock if @InjectMocks is not working as expected
        ReflectionTestUtils.setField(authService, "authenticationManager", authenticationManager);
    }


    @Test
    public void testSignInSuccess() {
        // Arrange
        String email = "test@example.com";
        String password = "validPassword";
        String fakeToken = "fakeJwtToken123";
        User user = new User();
        user.setId("1");
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(REGISTERED); // Assuming REGISTERED is an enum of UserType


        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail(email);
        loginRequestDTO.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        when(jwtService.generateToken(user, user.getId(), user.getUserType())).thenReturn(fakeToken);

        // Act
        LoginDTO loginDTO = authService.signIn(loginRequestDTO);

        // Assert
        assertEquals(fakeToken, loginDTO.getJwt(), "The returned token should match the expected token.");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(user, user.getId(), user.getUserType());
    }
}



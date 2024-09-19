package ba.edu.ibu.DigitalMarketplace.core.service;


import ba.edu.ibu.DigitalMarketplace.core.api.mailsender.MailSender;
import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.DigitalMarketplace.core.model.User;
import ba.edu.ibu.DigitalMarketplace.core.repository.UserRepository;
import ba.edu.ibu.DigitalMarketplace.rest.dto.UserDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private MailSender mailgunSender;

    @Autowired
    private MailSender sendgridSender;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        // List<User> users = userRepository.findAllCustom();

        return users
                .stream()
                .map(UserDTO::new)
                .collect(toList());
    }

    public UserDTO getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return new UserDTO(user.get());
    }

    public UserDTO addUser(UserRequestDTO payload) {
        User user = userRepository.save(payload.toEntity());
        return new UserDTO(user);
    }

    public UserDTO updateUser(String id, UserRequestDTO payload) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            throw new ResourceNotFoundException("User with the given ID does not exist.");
        }

        User existingUser = existingUserOpt.get();

        // update only fields that are present in the payload
        if (payload.getUsername() != null) {
            existingUser.setUsername(payload.getUsername());
        }
        if (payload.getImgUrl() != null) {
            existingUser.setImgUrl(payload.getImgUrl());
        }
        if (payload.getEmail() != null) {
            existingUser.setEmail(payload.getEmail());
        }
        if (payload.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(payload.getPhoneNumber());
        }
        if (payload.getAddress() != null) {
            existingUser.setAddress(payload.getAddress());
        }

        // save the updated user
        User updatedUser = userRepository.save(existingUser);
        return new UserDTO(updatedUser);
    }


    public void deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }

    public UserDTO filterByEmail(String email) {
        Optional<User> user = userRepository.findFirstByEmailLike(email);
        // Optional<User> user = userRepository.findByEmailCustom(email);
        return user.map(UserDTO::new).orElse(null);
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByUsernameOrEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
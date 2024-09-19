package ba.edu.ibu.DigitalMarketplace.core.service;

import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.model.Message;
import ba.edu.ibu.DigitalMarketplace.core.model.User;
import ba.edu.ibu.DigitalMarketplace.core.repository.MessageRepository;
import ba.edu.ibu.DigitalMarketplace.core.repository.UserRepository;
import ba.edu.ibu.DigitalMarketplace.rest.websockets.MainSocketHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static ba.edu.ibu.DigitalMarketplace.core.model.enums.UserType.ADMIN;

@Service
public class NotificationService {
    private final MainSocketHandler mainSocketHandler;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;


    public NotificationService(MainSocketHandler mainSocketHandler, MessageRepository messageRepository, UserRepository userRepository) {
        this.mainSocketHandler = mainSocketHandler;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public void broadcastMessage(String message) throws IOException {
        mainSocketHandler.broadcastMessage(message);
    }

    public void sendMessage(String userId, String message) {
        mainSocketHandler.sendMessage(userId, message);
    }


    public void sendMessageToAdmins(String message) throws IOException {
        List<User> admins = userRepository.findByUserType(ADMIN); // Assuming "ADMIN" is the correct type
        for (User admin : admins) {
            mainSocketHandler.sendMessage(admin.getId(), message);
            saveMessage(admin.getId(), message);
        }
    }

    private void saveMessage(String userId, String content) {
        Message message = new Message(userId, content, new Date());
        messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}

package ba.edu.ibu.DigitalMarketplace.rest.websockets;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.GeneralException;
import ba.edu.ibu.DigitalMarketplace.core.model.User;
import ba.edu.ibu.DigitalMarketplace.core.service.JwtService;
import ba.edu.ibu.DigitalMarketplace.core.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class MainSocketHandler implements WebSocketHandler {
    private final JwtService jwtService;
    private final UserService userService;
    public Map<String, WebSocketSession> sessions = new HashMap<>();

    public MainSocketHandler(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        User user = getUser(session);
        if (user == null) {
            return;
        }

        sessions.put(user.getId(), session);
        System.out.println("Session created for the user " + user.getId() + " where the session id is " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Error happened for session " + session.getId() + ": " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Connection closed for session " + session.getId() + ": " + closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageReceived = (String) message.getPayload();
        System.out.println("Message received: " + messageReceived);
    }

    public void broadcastMessage(String message) throws IOException {
        sessions.forEach((key, session) -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendMessage(String userId, String message) {
        WebSocketSession session = sessions.get(userId);
        if (session == null) {
            return;
        }

        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new GeneralException(e);
        }
    }

    private User getUser(WebSocketSession session) throws IOException {
        List<String> headers = session.getHandshakeHeaders().getOrEmpty("authorization");

        if (headers.isEmpty()) {
            session.close();
            return null;
        }

        String jwt = headers.get(0).substring(7);
        String userEmail = jwtService.extractUserName(jwt);

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
        return (User) userDetails;
    }
}
package ba.edu.ibu.DigitalMarketplace.core.api.mailsender;

import ba.edu.ibu.DigitalMarketplace.core.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MailSender {

    public String send(List<User> users, String message);
}

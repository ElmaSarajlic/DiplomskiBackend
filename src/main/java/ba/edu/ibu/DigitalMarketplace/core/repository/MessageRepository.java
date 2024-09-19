package ba.edu.ibu.DigitalMarketplace.core.repository;

import  ba.edu.ibu.DigitalMarketplace.core.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository <Message,String> {
}

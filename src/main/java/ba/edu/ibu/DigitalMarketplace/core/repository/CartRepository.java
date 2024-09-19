package ba.edu.ibu.DigitalMarketplace.core.repository;

import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CartRepository extends MongoRepository<Cart, String> {
    List<Cart> findAllByUserId(String userId);


}

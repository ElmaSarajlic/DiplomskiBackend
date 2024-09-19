package ba.edu.ibu.DigitalMarketplace.core.repository;
import ba.edu.ibu.DigitalMarketplace.core.model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface WishlistRepository extends MongoRepository<Wishlist, String> {
    List<Wishlist> findAllByUserId(String userId);

}

package ba.edu.ibu.DigitalMarketplace.core.repository;

import ba.edu.ibu.DigitalMarketplace.core.model.Subcategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends MongoRepository<Subcategory, String> {
    List<Subcategory> findByCategoryId(String categoryId);
}
package ba.edu.ibu.DigitalMarketplace.core.repository;

import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ActiveProfiles("test") // Uses application-test.properties for configuration
public class ArtworkRepositoryTests {

    @Autowired
    private ArtworkRepository artworkRepository;

    @Test
    void testFindByCategory() {
        // Arrange
        Artwork artwork = new Artwork();
        artwork.setTitle("Mona Lisa");
        artwork.setCategory("Classic");
        artworkRepository.save(artwork);

        // Act
        List<Artwork> artworks = artworkRepository.findByCategory("Classic");

        // Assert
        assertEquals(1, artworks.size());
        assertEquals("Mona Lisa", artworks.get(0).getTitle());
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
        // Arrange
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Starry Night");
        artwork1.setCategory("Impressionism");
        artworkRepository.save(artwork1);

        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Night Cafe");
        artwork2.setCategory("Post-Impressionism");
        artworkRepository.save(artwork2);

        // Act
        List<Artwork> artworks = artworkRepository.findByTitleContainingIgnoreCase("night");

        // Assert
        assertEquals(2, artworks.size());
    }
}

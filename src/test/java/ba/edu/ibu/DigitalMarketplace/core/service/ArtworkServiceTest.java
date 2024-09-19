package ba.edu.ibu.DigitalMarketplace.core.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.repository.ArtworkRepository;

import ba.edu.ibu.DigitalMarketplace.rest.dto.ArtworkDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.ArtworkRequestDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ArtworkServiceTest {
    @Mock
    private ArtworkRepository artworkRepository;

    @InjectMocks
    private ArtworkService artworkService;

    @Test
    public void testCreateArtwork() throws Exception {
        // Arrange
        ArtworkRequestDTO requestDTO = new ArtworkRequestDTO(); // Assume setters are called as per test requirement
        Artwork artwork = new Artwork(); // Assume setters are used as per requestDTO
        when(artworkRepository.save(any(Artwork.class))).thenReturn(artwork);

        // Act
        ArtworkDTO result = artworkService.createArtwork(requestDTO);

        // Assert
        assertNotNull(result);
        verify(artworkRepository).save(any(Artwork.class));
    }

    @Test
    public void testGetArtworkById() {
        // Arrange
        String artworkId = "1";
        Optional<Artwork> artwork = Optional.of(new Artwork());
        when(artworkRepository.findById(artworkId)).thenReturn(artwork);

        // Act
        ArtworkDTO result = artworkService.getArtworkById(artworkId);

        // Assert
        assertNotNull(result);
        verify(artworkRepository).findById(artworkId);
    }

    @Test
    public void testUpdateArtwork() {
        String artworkId = "1";
        ArtworkRequestDTO requestDTO = new ArtworkRequestDTO(); // Assume setters are called
        Artwork existingArtwork = new Artwork(); // Set properties as needed
        existingArtwork.setId(artworkId);

        Artwork updatedArtwork = new Artwork(); // Set updated properties as needed
        when(artworkRepository.findById(artworkId)).thenReturn(Optional.of(existingArtwork));
        when(artworkRepository.save(any(Artwork.class))).thenReturn(updatedArtwork);

        ArtworkDTO result = artworkService.updateArtwork(artworkId, requestDTO);

        assertNotNull(result);
        verify(artworkRepository).save(any(Artwork.class));
    }



    @Test
    public void testGetArtworksByCategory() {
        String categoryName = "Digital";
        List<Artwork> artworks = Arrays.asList(new Artwork(), new Artwork()); // Initialize with sample data

        when(artworkRepository.findByCategory(categoryName)).thenReturn(artworks);

        List<ArtworkDTO> result = artworkService.getArtworksByCategory(categoryName);

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        verify(artworkRepository).findByCategory(categoryName);
    }




}
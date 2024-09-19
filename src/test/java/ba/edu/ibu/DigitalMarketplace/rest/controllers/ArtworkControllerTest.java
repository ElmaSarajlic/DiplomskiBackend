package ba.edu.ibu.DigitalMarketplace.rest.controllers;

import ba.edu.ibu.DigitalMarketplace.core.service.ArtworkService;
import ba.edu.ibu.DigitalMarketplace.core.service.NotificationService;
import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.rest.dto.ArtworkDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.ArtworkRequestDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Date;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ArtworkControllerTest {

    @Mock
    private ArtworkService artworkService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ArtworkController artworkController;


    @Test
    public void testGetArtworkById_Found() {
        String artworkId = "1";
        Artwork mockArtwork = new Artwork();
        mockArtwork.setId(artworkId);
        mockArtwork.setTitle("Sample Artwork");
        mockArtwork.setDescription("This is a sample artwork.");
        mockArtwork.setCreationDate(new Date());
        mockArtwork.setCategory("Digital");
        mockArtwork.setUserId("user123");
        mockArtwork.setPrice(150.00);
        mockArtwork.setImgUrl("http://example.com/image.jpg");
        mockArtwork.setAuthor("Artist Name");

        ArtworkDTO returnedArtwork = new ArtworkDTO(mockArtwork);
        when(artworkService.getArtworkById(artworkId)).thenReturn(returnedArtwork);

        ResponseEntity<ArtworkDTO> response = artworkController.getArtworkById(artworkId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnedArtwork, response.getBody());
    }

    @Test
    public void testGetArtworkById_NotFound() {
        String artworkId = "1";
        when(artworkService.getArtworkById(artworkId)).thenReturn(null);

        ResponseEntity<ArtworkDTO> response = artworkController.getArtworkById(artworkId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

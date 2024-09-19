package ba.edu.ibu.DigitalMarketplace.rest.controllers;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.model.Cart;
import ba.edu.ibu.DigitalMarketplace.core.model.Comment;
import ba.edu.ibu.DigitalMarketplace.core.service.ArtworkService;
import ba.edu.ibu.DigitalMarketplace.core.service.NotificationService;
import ba.edu.ibu.DigitalMarketplace.rest.dto.ArtworkDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.ArtworkRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("api/artworks")
@SecurityRequirement(name = "JWT Security")

public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;
    private NotificationService notificationService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/")
    @PreAuthorize("hasAnyAuthority('REGISTERED', 'ADMIN')")
    public ResponseEntity<ArtworkDTO> createArtwork(@RequestBody ArtworkRequestDTO artworkRequestDTO) throws Exception {
        ArtworkDTO createdArtwork = artworkService.createArtwork(artworkRequestDTO);
        return new ResponseEntity<>(createdArtwork, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<ArtworkDTO>> getAllArtworks() {
        List<ArtworkDTO> artworks = artworkService.getAllArtworks();
        return new ResponseEntity<>(artworks, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{artworkId}")
    public ResponseEntity<ArtworkDTO> getArtworkById(@PathVariable String artworkId) {
        ArtworkDTO artwork = artworkService.getArtworkById(artworkId);
        if (artwork != null) {
            return new ResponseEntity<>(artwork, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{artworkId}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('REGISTERED', 'ADMIN')")
    public ResponseEntity<ArtworkDTO> updateArtwork(@PathVariable String artworkId, @RequestBody ArtworkRequestDTO artworkRequestDTO) {
        ArtworkDTO updatedArtwork = artworkService.updateArtwork(artworkId, artworkRequestDTO);
        if (updatedArtwork != null) {
            return new ResponseEntity<>(updatedArtwork, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{artworkId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('REGISTERED', 'ADMIN')")
    public ResponseEntity<Void> deleteArtwork(@PathVariable String artworkId) {
        artworkService.deleteArtwork(artworkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/category/{categoryName}", method = RequestMethod.GET)
    public ResponseEntity<List<ArtworkDTO>> getArtworksByCategory(@PathVariable String categoryName) {
        List<ArtworkDTO> artworks = artworkService.getArtworksByCategory(categoryName);
        if (!artworks.isEmpty()) {
            return new ResponseEntity<>(artworks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{userId}/{artworkId}/comments")
    public ResponseEntity<ArtworkDTO> addComment(@PathVariable String artworkId, @RequestBody Comment comment) {
        try {
            ArtworkDTO updatedArtwork = artworkService.addCommentToArtwork(artworkId, comment);
            return ResponseEntity.ok(updatedArtwork);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{artworkId}/comments")
    public ResponseEntity<List<Comment>> getCommentsForArtwork(@PathVariable String artworkId) {
        try {
            List<Comment> comments = artworkService.getCommentsForArtwork(artworkId);
            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String commentId) {
        try {
            Comment comment = artworkService.getCommentById(commentId);
            return ResponseEntity.ok(comment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

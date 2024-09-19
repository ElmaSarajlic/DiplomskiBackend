package ba.edu.ibu.DigitalMarketplace.core.service;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.model.Cart;
import ba.edu.ibu.DigitalMarketplace.core.model.Category;
import ba.edu.ibu.DigitalMarketplace.core.model.Comment;
import ba.edu.ibu.DigitalMarketplace.core.repository.ArtworkRepository;
import ba.edu.ibu.DigitalMarketplace.rest.dto.ArtworkDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.ArtworkRequestDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtworkService {
    private final ArtworkRepository artworkRepository;

    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public List<ArtworkDTO> getAllArtworks() {
        List<Artwork> artworks = artworkRepository.findAll();
        return artworks.stream()
                .map(ArtworkDTO::new)
                .collect(Collectors.toList());
    }

        public ArtworkDTO getArtworkById(String artworkId) {
        Optional<Artwork> artwork = artworkRepository.findById(artworkId);
        if (artwork.isEmpty()) {
            throw new ResourceNotFoundException("The Artwork with the given ID does not exist.");
        }
        return new ArtworkDTO(artwork.get());
    }

    public ArtworkDTO createArtwork(ArtworkRequestDTO artworkRequestDTO) throws Exception {
        Artwork artwork = artworkRepository.save(artworkRequestDTO.toEntity());
        return new ArtworkDTO(artwork);
    }

    public ArtworkDTO updateArtwork(String artworkId, ArtworkRequestDTO artworkRequestDTO) {
        Optional<Artwork> existingArtwork = artworkRepository.findById(artworkId);
        if (existingArtwork.isEmpty()) {
            throw new ResourceNotFoundException("The Artwork with the given ID does not exist.");
        }
        Artwork updatedArtwork = artworkRequestDTO.toEntity();
        updatedArtwork.setId(existingArtwork.get().getId());
        updatedArtwork = artworkRepository.save(updatedArtwork);
        return new ArtworkDTO(updatedArtwork);
    }

    public void deleteArtwork(String artworkId) {
        Optional<Artwork> artwork = artworkRepository.findById(artworkId);
        artwork.ifPresent(artworkRepository::delete);
    }

    public List<ArtworkDTO> getArtworksByCategory(String categoryName) {
        List<Artwork> artworks = artworkRepository.findByCategory(categoryName);

        if (artworks.isEmpty()) {
            throw new ResourceNotFoundException("Artworks with the given category do not exist.");
        }

        return artworks.stream()
                .map(ArtworkDTO::new)
                .collect(Collectors.toList());
    }

    public ArtworkDTO addCommentToArtwork(String artworkId, Comment comment) throws ResourceNotFoundException {
        Optional<Artwork> artworkOpt = artworkRepository.findById(artworkId);
        if (artworkOpt.isEmpty()) {
            throw new ResourceNotFoundException("The Artwork with the given ID does not exist.");
        }

        Artwork artwork = artworkOpt.get();
        List<Comment> comments = artwork.getComments();
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        artwork.setComments(comments);
        artwork = artworkRepository.save(artwork);
        return new ArtworkDTO(artwork);
    }

    public Comment getCommentById(String commentId) throws ResourceNotFoundException {
        List<Artwork> artworks = artworkRepository.findAll();

        for (Artwork artwork : artworks) {
            List<Comment> comments = artwork.getComments();
            if (comments != null) {
                for (Comment comment : comments) {
                    if (commentId.equals(comment.getId())) {
                        return comment;
                    }
                }
            }
        }

        throw new ResourceNotFoundException("No comment found with the given ID.");
    }

    public List<Comment> getCommentsForArtwork(String artworkId) throws ResourceNotFoundException {
        Optional<Artwork> artworkOpt = artworkRepository.findById(artworkId);
        if (artworkOpt.isEmpty()) {
            throw new ResourceNotFoundException("The Artwork with the given ID does not exist.");
        }

        Artwork artwork = artworkOpt.get();
        List<Comment> comments = artwork.getComments();
        if (comments == null) {
            throw new ResourceNotFoundException("No comments found for the given artwork.");
        }
        return comments;
    }
}

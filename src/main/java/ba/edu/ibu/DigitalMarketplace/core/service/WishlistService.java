package ba.edu.ibu.DigitalMarketplace.core.service;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.model.Wishlist;
import ba.edu.ibu.DigitalMarketplace.core.repository.ArtworkRepository;
import ba.edu.ibu.DigitalMarketplace.core.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ArtworkRepository artworkRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, ArtworkRepository artworkRepository) {
        this.wishlistRepository = wishlistRepository;
        this.artworkRepository = artworkRepository;
    }

    public String createOrGetWishlistIdByUserId(String userId) {
        List<Wishlist> existingWishlists = wishlistRepository.findAllByUserId(userId);
        if (existingWishlists.isEmpty()) {
            Wishlist newWishlist = new Wishlist();
            newWishlist.setUserId(userId);
            newWishlist.setCreationDate(new Date());
            wishlistRepository.save(newWishlist);
            return newWishlist.getId();
        }
        return existingWishlists.get(0).getId();
    }

    public Wishlist addArtworkToWishlist(String wishlistId, String artworkId) throws ResourceNotFoundException {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist with the given ID does not exist."));
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork with the given ID does not exist."));

        if (wishlist.getArtworks().stream().anyMatch(a -> a.getId().equals(artworkId))) {
            throw new IllegalStateException("Artwork already exists in the wishlist.");
        }

        wishlist.getArtworks().add(artwork);
        return wishlistRepository.save(wishlist);
    }

    public void removeArtworkFromWishlist(String wishlistId, String artworkId) throws ResourceNotFoundException {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist with the given ID does not exist."));
        Optional<Artwork> artworkToRemove = wishlist.getArtworks().stream()
                .filter(artwork -> artwork.getId().equals(artworkId))
                .findFirst();

        if (artworkToRemove.isPresent()) {
            wishlist.getArtworks().remove(artworkToRemove.get());
            wishlistRepository.save(wishlist);
        } else {
            throw new ResourceNotFoundException("Artwork with the given ID does not exist in the wishlist.");
        }
    }

    public Wishlist getWishlistById(String wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist with the given ID does not exist."));
    }

    public List<Wishlist> getWishlistsByUserId(String userId) {
        return wishlistRepository.findAllByUserId(userId);
    }

    public void clearWishlistByUserId(String userId) {
        List<Wishlist> userWishlists = wishlistRepository.findAllByUserId(userId);
        if (!userWishlists.isEmpty()) {
            wishlistRepository.deleteAll(userWishlists);
        }
    }
}

package ba.edu.ibu.DigitalMarketplace.core.service;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.model.Cart;
import ba.edu.ibu.DigitalMarketplace.core.repository.ArtworkRepository;
import ba.edu.ibu.DigitalMarketplace.core.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ArtworkRepository artworkRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ArtworkRepository artworkRepository) {
        this.cartRepository = cartRepository;
        this.artworkRepository = artworkRepository;

    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public String getOrCreateCartIdByUserId(String userId) {
        List<Cart> existingCarts = cartRepository.findAllByUserId(userId);
        if (existingCarts.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setCreationDate(new Date());
            cartRepository.save(newCart);
            return newCart.getId();
        }
        return existingCarts.get(0).getId(); // Assumes only one active cart per user; modify as needed.
    }

    public Cart addArtworkToCart(String cartId, String artworkId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with the given ID does not exist."));
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork with the given ID does not exist."));

        // Check if the artwork already exists in the cart
        boolean artworkExists = cart.getArtworks().stream()
                .anyMatch(existingArtwork -> existingArtwork.getId().equals(artworkId));

        if (artworkExists) {
            throw new IllegalStateException("Artwork already exists in the cart.");
        }

        cart.getArtworks().add(artwork);
        cart.setQuantity(cart.getQuantity() + 1);
        return cartRepository.save(cart);
    }


    public void removeFromCart(String cartId, String artworkId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with the given ID does not exist."));
        Optional<Artwork> artworkToRemove = cart.getArtworks().stream()
                .filter(artwork -> artwork.getId().equals(artworkId))
                .findFirst();

        if (artworkToRemove.isPresent()) {
            cart.getArtworks().remove(artworkToRemove.get());
            cart.setQuantity(cart.getQuantity() - 1);
            cartRepository.save(cart);
        } else {
            throw new ResourceNotFoundException("Artwork with the given ID does not exist in the cart.");
        }
    }

    public Cart getCartById(String cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with the given ID does not exist."));
    }

    public List<Cart> getCartsByUserId(String userId) {
        return cartRepository.findAllByUserId(userId);
    }

    public void clearCartByUserId(String userId) {
        List<Cart> userCarts = cartRepository.findAllByUserId(userId);
        if (!userCarts.isEmpty()) {
            cartRepository.deleteAll(userCarts);
        }
    }
}

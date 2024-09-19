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
import ba.edu.ibu.DigitalMarketplace.core.model.Cart;
import ba.edu.ibu.DigitalMarketplace.core.repository.ArtworkRepository;
import ba.edu.ibu.DigitalMarketplace.core.repository.CartRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ArtworkRepository artworkRepository;

    @InjectMocks
    private CartService cartService;


    @Test
    public void testGetOrCreateCartIdByUserId_ExistingCart() {
        // Arrange
        String userId = "user123";
        Cart existingCart = new Cart();
        existingCart.setId("cart1");
        when(cartRepository.findAllByUserId(userId)).thenReturn(Arrays.asList(existingCart));

        // Act
        String cartId = cartService.getOrCreateCartIdByUserId(userId);

        // Assert
        assertEquals("cart1", cartId);
        verify(cartRepository, never()).save(any(Cart.class));
    }


    @Test
    public void testRemoveFromCart() throws ResourceNotFoundException {
        // Arrange
        String cartId = "cart1";
        String artworkId = "art1";
        Cart cart = new Cart();
        Artwork artwork = new Artwork();
        artwork.setId(artworkId);
        cart.getArtworks().add(artwork);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // Act
        cartService.removeFromCart(cartId, artworkId);

        // Assert
        assertFalse(cart.getArtworks().contains(artwork));
        verify(cartRepository).save(cart);
    }

    @Test
    public void testClearCartByUserId() {
        // Arrange
        String userId = "user123";
        List<Cart> carts = Arrays.asList(new Cart(), new Cart());
        when(cartRepository.findAllByUserId(userId)).thenReturn(carts);

        // Act
        cartService.clearCartByUserId(userId);

        // Assert
        verify(cartRepository).deleteAll(carts);
    }
}
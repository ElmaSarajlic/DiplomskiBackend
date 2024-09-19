package ba.edu.ibu.DigitalMarketplace.rest.controllers;

import ba.edu.ibu.DigitalMarketplace.core.model.Cart;
import ba.edu.ibu.DigitalMarketplace.core.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> allCarts = cartService.getAllCarts();
        return ResponseEntity.ok(allCarts);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Cart> addArtworkToCart(@PathVariable String userId,
                                                 @RequestParam String artworkId) {
        String cartId = cartService.getOrCreateCartIdByUserId(userId);
        Cart cart = cartService.addArtworkToCart(cartId, artworkId);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartId}/artwork/{artworkId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable String cartId,
                                               @PathVariable String artworkId) {
        cartService.removeFromCart(cartId, artworkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable String cartId) {
        Cart cart = cartService.getCartById(cartId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUserId(@PathVariable String userId) {
        List<Cart> carts = cartService.getCartsByUserId(userId);
        return ResponseEntity.ok(carts);
    }

    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<Void> clearCartByUserId(@PathVariable String userId) {
        cartService.clearCartByUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

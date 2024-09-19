package ba.edu.ibu.DigitalMarketplace.rest.controllers;

import ba.edu.ibu.DigitalMarketplace.core.model.Wishlist;
import ba.edu.ibu.DigitalMarketplace.core.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Wishlist> addArtworkToWishlist(@PathVariable String userId,
                                                         @RequestParam String artworkId) {
        String wishlistId = wishlistService.createOrGetWishlistIdByUserId(userId);
        Wishlist wishlist = wishlistService.addArtworkToWishlist(wishlistId, artworkId);
        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @DeleteMapping("/{wishlistId}/artwork/{artworkId}")
    public ResponseEntity<Void> removeArtworkFromWishlist(@PathVariable String wishlistId,
                                                          @PathVariable String artworkId) {
        wishlistService.removeArtworkFromWishlist(wishlistId, artworkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable String wishlistId) {
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        return ResponseEntity.ok(wishlist);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlistsByUserId(@PathVariable String userId) {
        List<Wishlist> wishlists = wishlistService.getWishlistsByUserId(userId);
        return ResponseEntity.ok(wishlists);
    }

    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<Void> clearWishlistByUserId(@PathVariable String userId) {
        wishlistService.clearWishlistByUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

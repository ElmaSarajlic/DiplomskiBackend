package ba.edu.ibu.DigitalMarketplace.rest.dto;

import ba.edu.ibu.DigitalMarketplace.core.model.User;
import ba.edu.ibu.DigitalMarketplace.core.model.enums.UserType;

import java.util.Date;


public class UserRequestDTO {
    private UserType userType;
    private String email;
    private String username;
    private String password;
    private String imgUrl;
    private String cartId;
    private String wishlistId;
    private String address;
    private String phoneNumber;



    public UserRequestDTO() { }



    public UserRequestDTO(User user) {
        this.userType = user.getUserType();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.imgUrl = user.getImgUrl();
        this.cartId = user.getCartId();
        this.wishlistId = user.getWishlistId();

        this.phoneNumber = user.getPhoneNumber();
    }

    public UserRequestDTO(String updatedName, String s, String newImgUrl, String newPhone, String newAddress) {
    }


    public String getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
    }

    public User toEntity() {
        User user = new User();
        user.setUserType(userType);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setCreationDate(new Date());
        user.setImgUrl(imgUrl);
        user.setCartId(cartId);
        user.setWishlistId(wishlistId);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}


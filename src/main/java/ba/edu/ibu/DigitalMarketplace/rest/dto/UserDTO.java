package ba.edu.ibu.DigitalMarketplace.rest.dto;


import ba.edu.ibu.DigitalMarketplace.core.model.User;
import ba.edu.ibu.DigitalMarketplace.core.model.enums.UserType;

import java.util.Date;

public class UserDTO {
    private String id;
    private String username;
    private UserType userType;
    private String email;
    private Date creationDate;
    private String imgUrl;
    private String cartId;

    public String getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
    }

    private String wishlistId;
    private String address;
    private String phoneNumber;


    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.userType = user.getUserType();
        this.email = user.getEmail();
        this.creationDate = user.getCreationDate();
        this.imgUrl = user.getImgUrl();
        this.cartId = user.getCartId();
        this.wishlistId = user.getWishlistId();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
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

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setEmail(String email) { this.email = email;}

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }


}

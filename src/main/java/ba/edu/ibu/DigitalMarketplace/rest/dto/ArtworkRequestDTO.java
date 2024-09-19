package ba.edu.ibu.DigitalMarketplace.rest.dto;

import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.model.Comment;

import java.util.Date;
import java.util.List;

public class ArtworkRequestDTO {
    private String title;
    private String description;
    private String category;
    private String userId;
    private double price;
    private String imgUrl;
    private String author;
    private List<Comment> comments;  // Embedded comments



    public ArtworkRequestDTO() { }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public ArtworkRequestDTO(Artwork artwork) {
        this.title = artwork.getTitle();
        this.description = artwork.getDescription();
        this.category = artwork.getCategory();
        this.userId = artwork.getUserId();
        this.price = artwork.getPrice();
        this.imgUrl = artwork.getImgUrl();
        this.author = artwork.getAuthor();
        this.comments = artwork.getComments();
    }

    public Artwork toEntity() {
        Artwork artwork = new Artwork();
        artwork.setTitle(title);
        artwork.setDescription(description);
        artwork.setCreationDate(new Date());
        artwork.setCategory(category);
        artwork.setUserId(userId);
        artwork.setPrice(price);
        artwork.setImgUrl(imgUrl);
        artwork.setAuthor(author);
        artwork.setComments(comments);
        return artwork;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

}

package ba.edu.ibu.DigitalMarketplace.rest.dto;

import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.model.Comment;

import java.util.Date;
import java.util.List;

public class ArtworkDTO {
    private String id;
    private String title;
    private String description;
    private Date creationDate;
    private String category;
    private String userId;
    private double price;
    private String imgUrl;
    private String author;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    private List<Comment> comments;  // Embedded comments




    public ArtworkDTO(Artwork artwork) {
        this.id = artwork.getId();
        this.title = artwork.getTitle();
        this.description = artwork.getDescription();
        this.creationDate = artwork.getCreationDate();
        this.category = artwork.getCategory();
        this.userId = artwork.getUserId();
        this.price = artwork.getPrice();
        this.imgUrl = artwork.getImgUrl();
        this.author = artwork.getAuthor();
        this.comments = artwork.getComments();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description;}

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
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

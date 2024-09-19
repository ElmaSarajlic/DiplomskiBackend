package ba.edu.ibu.DigitalMarketplace.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class Artwork {
    @Id
    private String id;
    private String title;
    private String description;
    private Date creationDate;
    private String category;
    private double price;
    private String userId;
    private String imgUrl;
    private String author;
    private List<Comment> comments;  // Embedded comments


    public Artwork(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }



    public  Artwork(){}

    public Artwork(String id, String title, String userId, String category, double price,  String description, Date creationDate, String imgUrl, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.creationDate = creationDate;
        this.userId = userId;
        this.price = price;
        this.imgUrl = imgUrl;
        this.author = author;


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

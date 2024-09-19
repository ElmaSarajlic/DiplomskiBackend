package ba.edu.ibu.DigitalMarketplace.core.model;

import org.springframework.data.annotation.Id;
import java.util.Date;

public class Comment {
    @Id
    private String id;
    private String userId;

    public Comment(String id) {
        this.id = id;
    }

    public String getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(String artworkId) {
        this.artworkId = artworkId;
    }

    private String artworkId;
    private String text;
    private Date creationDate;

    public Comment() {}

    public Comment(String id, String userId, String text, Date creationDate) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.creationDate = creationDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

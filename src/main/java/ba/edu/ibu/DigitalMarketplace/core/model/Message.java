package ba.edu.ibu.DigitalMarketplace.core.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {
    @Id
    private String id;
    private String userId;
    private String content;
    private Date creationDate;


    public Message(String id, String userId, String content, Date creationDate) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.creationDate = creationDate;
    }

    public Message(String userId, String content, Date creationDate) {
        this.userId = userId;
        this.content = content;
        this.creationDate = creationDate;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

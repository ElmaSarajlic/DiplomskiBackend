package ba.edu.ibu.DigitalMarketplace.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Wishlist {
    @Id
    private String id;
    private List<Artwork> artworks = new ArrayList<>();
    private Date creationDate;
    private String userId;


    public  Wishlist(){}

    public Wishlist(String id,  String userId, List<Artwork> artworks,   Date creationDate) {
        this.id = id;
        this.artworks = artworks;
        this.creationDate = creationDate;
        this.userId = userId;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

}

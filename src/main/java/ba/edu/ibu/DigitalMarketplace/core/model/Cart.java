package ba.edu.ibu.DigitalMarketplace.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Cart {

        @Id
        private String id;
        private List<Artwork> artworks = new ArrayList<>();
        private int quantity;
        private Date creationDate;
        private String userId;
        private float totalPrice;
        public  Cart(){}

        public Cart(String id,  String userId, List<Artwork> artworks,  int quantity, Date creationDate, float totalPrice) {
            this.id = id;
            this.quantity = quantity;
            this.artworks = artworks;
            this.creationDate = creationDate;
            this.userId = userId;
            this.totalPrice = totalPrice;

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;

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
        public float getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(float totalPrice) {
            this.totalPrice = totalPrice;
        }

    }


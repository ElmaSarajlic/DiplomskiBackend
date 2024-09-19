package ba.edu.ibu.DigitalMarketplace.core.model;

import org.springframework.data.annotation.Id;

import java.util.Date;


public class Subcategory {
    @Id
    private String id;
    private String name;
    private String categoryId;
    private Date creationDate;



    public Subcategory(){}

    public Subcategory(String id, String name, String categoryId, Date creationDate) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCategoryId(){
        return categoryId;
    }

    public void setCategoryId(String categoryId){
        this.categoryId = categoryId;
    }


}
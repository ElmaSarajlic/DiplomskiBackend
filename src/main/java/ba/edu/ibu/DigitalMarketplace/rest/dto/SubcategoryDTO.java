package ba.edu.ibu.DigitalMarketplace.rest.dto;

import ba.edu.ibu.DigitalMarketplace.core.model.Subcategory;

public class SubcategoryDTO {
    private String id;
    private String name;
    private String categoryId;


    public SubcategoryDTO(Subcategory subcategory) {
        this.id = subcategory.getId();
        this.name = subcategory.getName();
        this.categoryId = subcategory.getCategoryId();

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

    public String getCategoryId(){
        return categoryId;
    }

    public void setCategoryId(String categoryId){
        this.categoryId = categoryId;
    }



}



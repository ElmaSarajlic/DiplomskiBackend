package ba.edu.ibu.DigitalMarketplace.rest.dto;


import ba.edu.ibu.DigitalMarketplace.core.model.Subcategory;

public class SubcategoryRequestDTO {
    private String id;
    private String name;
    private String categoryId;


    public SubcategoryRequestDTO() { }

    public SubcategoryRequestDTO(Subcategory subcategory) {
        this.name = subcategory.getName();
        this.categoryId = subcategory.getCategoryId();

    }

    public Subcategory toEntity() {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(id);
        subcategory.setName(name);
        subcategory.setCategoryId(categoryId);
        return subcategory;
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

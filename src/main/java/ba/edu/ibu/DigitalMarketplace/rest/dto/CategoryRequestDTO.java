package ba.edu.ibu.DigitalMarketplace.rest.dto;

import ba.edu.ibu.DigitalMarketplace.core.model.Category;

import java.util.ArrayList;

public class CategoryRequestDTO {
    private String id;
    private String name;


    public CategoryRequestDTO() { }

    public CategoryRequestDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();

    }

    public Category toEntity() {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setSubcategories(new ArrayList<>()); // Initialize an empty list of subcategories

        return category;
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


}

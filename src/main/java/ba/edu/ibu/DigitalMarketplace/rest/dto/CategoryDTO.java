package ba.edu.ibu.DigitalMarketplace.rest.dto;


import ba.edu.ibu.DigitalMarketplace.core.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
    private String id;
    private String name;
    private List<SubcategoryDTO> subcategories;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.subcategories = new ArrayList<>();
    }

    public CategoryDTO(String id, String name, List<SubcategoryDTO> subcategories) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
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

    public List<SubcategoryDTO> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryDTO> subcategories) { // Changed from List<Subcategory> to List<SubcategoryDTO>
        this.subcategories = subcategories;
    }


}

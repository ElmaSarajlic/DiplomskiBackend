package ba.edu.ibu.DigitalMarketplace.rest.controllers;

import ba.edu.ibu.DigitalMarketplace.core.model.Category;
import ba.edu.ibu.DigitalMarketplace.core.model.Subcategory;
import ba.edu.ibu.DigitalMarketplace.core.service.CategoryService;
import ba.edu.ibu.DigitalMarketplace.rest.dto.CategoryDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.CategoryRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/categories")
@SecurityRequirement(name = "JWT Security")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryRequestDTO);

        // Initialize subcategories as an empty ArrayList if it's null
        if (createdCategory.getSubcategories() == null) {
            createdCategory.setSubcategories(new ArrayList<>());
        }

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN', 'REGISTERED')")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String categoryId) {
        CategoryDTO category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String categoryId, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryRequestDTO);
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/{categoryId}/subcategories", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> addSubcategory(
            @PathVariable String categoryId,
            @RequestBody Subcategory subcategory) {
        Category updatedCategory = categoryService.addSubcategoryToCategory(categoryId, subcategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCategory);
    }

    @RequestMapping(value ="/{categoryId}/subcategories/{subcategoryId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")

    public ResponseEntity<String> deleteSubcategoryFromCategory(@PathVariable String categoryId, @PathVariable String subcategoryId) {
        try {
            categoryService.deleteSubcategoryFromCategory(categoryId, subcategoryId);
            return ResponseEntity.ok("Subcategory deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}


package ba.edu.ibu.DigitalMarketplace.core.service;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.DigitalMarketplace.core.model.Category;
import ba.edu.ibu.DigitalMarketplace.core.model.Subcategory;
import ba.edu.ibu.DigitalMarketplace.core.repository.CategoryRepository;
import ba.edu.ibu.DigitalMarketplace.core.repository.SubcategoryRepository;
import ba.edu.ibu.DigitalMarketplace.rest.dto.CategoryDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.CategoryRequestDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.SubcategoryDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.SubcategoryRequestDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryService subcategoryService;

    public CategoryService(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, SubcategoryService subcategoryService) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.subcategoryService = subcategoryService;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToCategoryDTO(Category category) {
        List<SubcategoryDTO> subcategoryDTOs = category.getSubcategories().stream()
                .map(SubcategoryDTO::new) // Assuming you have a suitable constructor in SubcategoryDTO
                .collect(Collectors.toList());

        return new CategoryDTO(category.getId(), category.getName(), subcategoryDTOs);
    }



    public CategoryDTO getCategoryById(String categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("The category with the given ID does not exist.");
        }
        return new CategoryDTO(category.get());
    }

    public CategoryDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        // This will create a new Category entity with an empty list of subcategories
        Category category = categoryRequestDTO.toEntity();
        category = categoryRepository.save(category); // Save the new Category entity to the database
        return new CategoryDTO(category); // Convert the saved entity back to DTO
    }


    public CategoryDTO updateCategory(String categoryId, CategoryRequestDTO categoryRequestDTO) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isEmpty()) {
            throw new ResourceNotFoundException("The category with the given ID does not exist.");
        }
        Category updatedCategory = categoryRequestDTO.toEntity();
        updatedCategory.setId(existingCategory.get().getId());
        updatedCategory = categoryRepository.save(updatedCategory);
        return new CategoryDTO(updatedCategory);
    }


    public void deleteCategory(String categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("The category with the given ID does not exist.");
        }
        categoryRepository.delete(category.get());
    }

    public Category addSubcategoryToCategory(String categoryId, Subcategory subcategory) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        String subcategoryId = generateUniqueSubcategoryId();


        if (category.getSubcategories() == null) {
            category.setSubcategories(new ArrayList<>());
        }
        subcategory.setId(subcategoryId);

        category.getSubcategories().add(subcategory);
        String name = subcategory.getName();
        String id = subcategory.getId();
        SubcategoryRequestDTO subcategoryRequestDTO = new SubcategoryRequestDTO();
        subcategoryRequestDTO.setId(id);
        subcategoryRequestDTO.setName(name);
        subcategoryRequestDTO.setCategoryId(categoryId);
        subcategoryService.createSubcategory(subcategoryRequestDTO);


        return categoryRepository.save(category);
    }
    private String generateUniqueSubcategoryId() {
        // You can implement your logic here to generate a unique ID, e.g., UUID.randomUUID().toString()
        // Ensure that the generated ID is unique within your application.
        return UUID.randomUUID().toString();
    }



    public void deleteSubcategoryFromCategory(String categoryId, String subcategoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category with ID: " + categoryId + " not found"));

        // Check if category has subcategories and remove the one with the given ID
        Optional<Subcategory> subcategoryOptional = category.getSubcategories().stream()
                .filter(subcategory -> subcategory.getId() != null && subcategory.getId().equals(subcategoryId))
                .findFirst();

        if (subcategoryOptional.isPresent()) {
            // Remove subcategory from category's subcategory list
            category.getSubcategories().remove(subcategoryOptional.get());
            categoryRepository.save(category);

            // Now delete the subcategory from the subcategory table
            subcategoryService.deleteSubcategory(subcategoryId);
        } else {
            throw new RuntimeException("Subcategory not found in category");
        }
    }

}



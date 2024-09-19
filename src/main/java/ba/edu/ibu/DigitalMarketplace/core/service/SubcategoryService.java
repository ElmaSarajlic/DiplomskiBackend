package ba.edu.ibu.DigitalMarketplace.core.service;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.DigitalMarketplace.core.model.Subcategory;
import ba.edu.ibu.DigitalMarketplace.core.repository.CategoryRepository;
import ba.edu.ibu.DigitalMarketplace.core.repository.SubcategoryRepository;
import ba.edu.ibu.DigitalMarketplace.rest.dto.SubcategoryDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.SubcategoryRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubcategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository ) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<SubcategoryDTO> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        return subcategories.stream()
                .map(SubcategoryDTO::new)
                .collect(Collectors.toList());
    }

    public SubcategoryDTO getSubcategoryById(String subcategoryId) { // Changed
        Optional<Subcategory> subcategory = subcategoryRepository.findById(subcategoryId); // Changed
        if (subcategory.isEmpty()) {
            throw new ResourceNotFoundException("The subcategory with the given ID does not exist."); // Changed
        }
        return new SubcategoryDTO(subcategory.get()); // Changed
    }

    public SubcategoryDTO createSubcategory(SubcategoryRequestDTO subcategoryRequestDTO) {
        Subcategory subcategory = subcategoryRepository.save(subcategoryRequestDTO.toEntity());
        return new SubcategoryDTO(subcategory); // Changed
    }

    public SubcategoryDTO updateSubcategory(String subcategoryId, SubcategoryRequestDTO subcategoryRequestDTO) { // Changed
        Optional<Subcategory> existingSubcategory = subcategoryRepository.findById(subcategoryId); // Changed
        if (existingSubcategory.isEmpty()) {
            throw new ResourceNotFoundException("The subcategory with the given ID does not exist."); // Changed
        }
        Subcategory updatedSubcategory = subcategoryRequestDTO.toEntity();
        updatedSubcategory.setId(existingSubcategory.get().getId());
        updatedSubcategory = subcategoryRepository.save(updatedSubcategory);
        return new SubcategoryDTO(updatedSubcategory);
    }

    public void deleteSubcategory(String subcategoryId) { // Changed
        Optional<Subcategory> subcategory = subcategoryRepository.findById(subcategoryId); // Changed
        if (subcategory.isEmpty()) {
            throw new ResourceNotFoundException("The subcategory with the given ID does not exist."); // Changed
        }
        subcategoryRepository.delete(subcategory.get()); // Changed
    }


    public List<SubcategoryDTO> getAllSubcategoriesByCategoryId(String categoryId) {
        List<Subcategory> subcategories = subcategoryRepository.findByCategoryId(categoryId);
        return subcategories.stream()
                .map(this::convertToSubcategoryDTO)
                .collect(Collectors.toList());
    }
    private SubcategoryDTO convertToSubcategoryDTO(Subcategory subcategory) {
        return new SubcategoryDTO(subcategory);
    }
}

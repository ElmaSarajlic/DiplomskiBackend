package ba.edu.ibu.DigitalMarketplace.rest.controllers;

import ba.edu.ibu.DigitalMarketplace.core.service.SubcategoryService;
import ba.edu.ibu.DigitalMarketplace.rest.dto.SubcategoryDTO;
import ba.edu.ibu.DigitalMarketplace.rest.dto.SubcategoryRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/subcategories") // Changed
@SecurityRequirement(name = "JWT Security")
public class SubcategoryController { // Changed

    @Autowired
    private SubcategoryService subcategoryService; // Changed

    public SubcategoryController(SubcategoryService subcategoryService) { // Changed
        this.subcategoryService = subcategoryService;
    }




    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SubcategoryDTO>> getAllSubcategories() { // Changed
        List<SubcategoryDTO> subcategories = subcategoryService.getAllSubcategories(); // Changed
        return new ResponseEntity<>(subcategories, HttpStatus.OK);
    }

    @RequestMapping(value = "/{subcategoryId}", method = RequestMethod.GET) // Changed
    @PreAuthorize("hasAuthority('ADMIN', 'REGISTERED')")
    public ResponseEntity<SubcategoryDTO> getSubcategoryById(@PathVariable String subcategoryId) { // Changed
        SubcategoryDTO subcategory = subcategoryService.getSubcategoryById(subcategoryId); // Changed
        if (subcategory != null) {
            return new ResponseEntity<>(subcategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubcategoryDTO> createSubcategory(@RequestBody SubcategoryRequestDTO subcategoryRequestDTO) { // Changed
        SubcategoryDTO createdSubcategory = subcategoryService.createSubcategory(subcategoryRequestDTO); // Changed
        return new ResponseEntity<>(createdSubcategory, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{subcategoryId}", method = RequestMethod.PUT) // Changed
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubcategoryDTO> updateSubcategory(@PathVariable String subcategoryId, @RequestBody SubcategoryRequestDTO subcategoryRequestDTO) { // Changed
        SubcategoryDTO updatedSubcategory = subcategoryService.updateSubcategory(subcategoryId, subcategoryRequestDTO); // Changed
        if (updatedSubcategory != null) {
            return new ResponseEntity<>(updatedSubcategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{subcategoryId}", method = RequestMethod.DELETE) // Changed
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable String subcategoryId) { // Changed
        subcategoryService.deleteSubcategory(subcategoryId); // Changed
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<SubcategoryDTO>> getAllSubcategoriesByCategoryId(@PathVariable String categoryId) {
        List<SubcategoryDTO> subcategories = subcategoryService.getAllSubcategoriesByCategoryId(categoryId);
        if (subcategories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subcategories);
    }
}

package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Category;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.CategoryRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        try {
            if (category == null) {
                throw new IllegalArgumentException("Category cannot be null");
            }
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating category: " + e.getMessage());
        }
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(int po_CategoryCode, Category updatedCategory) {
        try {
            Optional<Category> existingCategoryOptional = categoryRepository.findById(po_CategoryCode);

            if (existingCategoryOptional.isPresent()) {
                Category existingCategory = existingCategoryOptional.get();
                existingCategory.setName(updatedCategory.getName());
                return categoryRepository.save(existingCategory);
            } else {
                throw new RuntimeException("Could not find Category with ID: " + po_CategoryCode);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid Category Code: " + po_CategoryCode);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the Category: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int po_CategoryCode) {
        Optional<Category> optionalCategory = categoryRepository.findById(po_CategoryCode);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(po_CategoryCode);
            return ("Category was successfully deleted");
        } else {
            throw new RuntimeException("Could not find Category with ID: " + po_CategoryCode);
        }
    }

    @Override
    public Category findById(int po_CategoryCode) {
        Optional<Category> optionalCategory = categoryRepository.findById(po_CategoryCode);
        return optionalCategory.orElseThrow(() -> new RuntimeException("Category with ID " + po_CategoryCode + " not found"));
    }

    @Override
    public Category findByname(String name) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findByname(name);
            return optionalCategory.orElseThrow(() -> new RuntimeException("Category with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Category");
        }
    }


    @Override
    public List<Category> searchByKeyword(String name) {
        return categoryRepository.searchByKeyword(name);
    }
}


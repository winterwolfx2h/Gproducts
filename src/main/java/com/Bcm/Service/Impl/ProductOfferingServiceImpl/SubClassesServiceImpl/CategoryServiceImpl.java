package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Category;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.CategoryRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(int po_CategoryCode, Category updatedCategory) {
        Optional<Category> existingCategoryOptional = categoryRepository.findById(po_CategoryCode);

        if (existingCategoryOptional.isPresent()) {
            Category existingCategory = existingCategoryOptional.get();
            existingCategory.setName(updatedCategory.getName());
            return categoryRepository.save(existingCategory);
        } else {
            throw new RuntimeException("Could not find Category with ID: " + po_CategoryCode);
        }
    }

    @Override
    public String delete(int po_CategoryCode) {
        categoryRepository.deleteById(po_CategoryCode);
        return ("Category was successfully deleted");
    }

    @Override
    public Category findById(int po_CategoryCode) {
        Optional<Category> optionalCategory = categoryRepository.findById(po_CategoryCode);
        return optionalCategory.orElseThrow(() -> new RuntimeException("Category with ID " + po_CategoryCode + " not found"));
    }

    @Override
    public List<Category> searchByKeyword(String name) {
        return categoryRepository.searchByKeyword(name);
    }
}

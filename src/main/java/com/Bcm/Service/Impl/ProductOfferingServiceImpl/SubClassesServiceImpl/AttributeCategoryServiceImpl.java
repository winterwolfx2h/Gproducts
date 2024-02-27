package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.AttributeCategory;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.AttributeCategoryRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.AttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttributeCategoryServiceImpl implements AttributeCategoryService {

    @Autowired
    AttributeCategoryRepository attributeCategoryRepository;

    @Override
    public AttributeCategory create(AttributeCategory AttributeCategory) {
        try {
            if (AttributeCategory == null) {
                throw new IllegalArgumentException("AttributeCategory cannot be null");
            }
            return attributeCategoryRepository.save(AttributeCategory);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating AttributeCategory: " + e.getMessage());
        }
    }

    @Override
    public List<AttributeCategory> read() {
        return attributeCategoryRepository.findAll();
    }

    @Override
    public AttributeCategory update(int po_AttributeCategoryCode, AttributeCategory updatedAttributeCategory) {
        try {
            Optional<AttributeCategory> existingAttributeCategoryOptional = attributeCategoryRepository.findById(po_AttributeCategoryCode);

            if (existingAttributeCategoryOptional.isPresent()) {
                AttributeCategory existingAttributeCategory = existingAttributeCategoryOptional.get();
                existingAttributeCategory.setName(updatedAttributeCategory.getName());
                return attributeCategoryRepository.save(existingAttributeCategory);
            } else {
                throw new RuntimeException("Could not find AttributeCategory with ID: " + po_AttributeCategoryCode);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid AttributeCategory Code: " + po_AttributeCategoryCode);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the AttributeCategory: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int po_AttributeCategoryCode) {
        Optional<AttributeCategory> optionalAttributeCategory = attributeCategoryRepository.findById(po_AttributeCategoryCode);
        if (optionalAttributeCategory.isPresent()) {
            attributeCategoryRepository.deleteById(po_AttributeCategoryCode);
            return ("AttributeCategory was successfully deleted");
        } else {
            throw new RuntimeException("Could not find AttributeCategory with ID: " + po_AttributeCategoryCode);
        }
    }

    @Override
    public AttributeCategory findById(int po_AttributeCategoryCode) {
        Optional<AttributeCategory> optionalAttributeCategory = attributeCategoryRepository.findById(po_AttributeCategoryCode);
        return optionalAttributeCategory.orElseThrow(() -> new RuntimeException("AttributeCategory with ID " + po_AttributeCategoryCode + " not found"));
    }

    @Override
    public AttributeCategory findByName(String name) {
        try {
            Optional<AttributeCategory> optionalAttributeCategory = attributeCategoryRepository.findByname(name);
            return optionalAttributeCategory.orElseThrow(() -> new RuntimeException("AttributeCategory with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding AttributeCategory");
        }
    }

    @Override
    public List<AttributeCategory> searchByKeyword(String name) {
        return attributeCategoryRepository.searchByKeyword(name);
    }
}


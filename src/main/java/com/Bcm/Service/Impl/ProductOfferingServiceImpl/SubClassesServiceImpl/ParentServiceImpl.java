package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ParentRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    ParentRepository parentRepository;

    @Override
    public Parent create(Parent parent) {
        try {
            return parentRepository.save(parent);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating Parent");
        }
    }

    @Override
    public List<Parent> read() {
        try {
            return parentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Parents");
        }
    }

    @Override
    public Parent update(int po_ParentCode, Parent updatedParent) {
        try {
            Optional<Parent> existingParentOptional = parentRepository.findById(po_ParentCode);
            if (existingParentOptional.isPresent()) {
                Parent existingParent = existingParentOptional.get();
                existingParent.setName(updatedParent.getName());
                return parentRepository.save(existingParent);
            } else {
                throw new RuntimeException("Parent with ID " + po_ParentCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating Parent");
        }
    }

    @Override
    public String delete(int po_ParentCode) {
        try {
            parentRepository.deleteById(po_ParentCode);
            return ("Parent with ID " + po_ParentCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Parent");
        }
    }

    @Override
    public Parent findById(int po_ParentCode) {
        try {
            Optional<Parent> optionalPlan = parentRepository.findById(po_ParentCode);
            return optionalPlan.orElseThrow(() -> new RuntimeException("Parent with ID " + po_ParentCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Parent");
        }
    }

    @Override
    public List<Parent> searchByKeyword(String name) {
        try {
            return parentRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Parent by keyword");
        }
    }

    @Override
    public Parent findByName(String name) {
        try {
            Optional<Parent> optionalParent = parentRepository.findByname(name);
            return optionalParent.orElseThrow(() -> new RuntimeException("Parent with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Parent");
        }
    }
}

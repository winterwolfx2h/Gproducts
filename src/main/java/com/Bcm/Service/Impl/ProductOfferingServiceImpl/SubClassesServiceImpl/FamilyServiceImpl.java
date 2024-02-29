package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubFamily;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.FamilyRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    FamilyRepository familyRepository;

    @Override
    public Family create(Family family) {
        try {
            return familyRepository.save(family);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating Family");
        }
    }

    @Override
    public List<Family> read() {
        try {
            return familyRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Families");
        }
    }

    @Override
    public Family update(int po_FamilyCode, Family updatedFamily) {
        try {
            Optional<Family> existingFamilyOptional = familyRepository.findById(po_FamilyCode);
            if (existingFamilyOptional.isPresent()) {
                Family existingFamily = existingFamilyOptional.get();
                existingFamily.setName(updatedFamily.getName());
                return familyRepository.save(existingFamily);
            } else {
                throw new RuntimeException("Family with ID " + po_FamilyCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating Family");
        }
    }

    @Override
    public String delete(int po_FamilyCode) {
        try {
            familyRepository.deleteById(po_FamilyCode);
            return ("Family with ID " + po_FamilyCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Family");
        }
    }

    @Override
    public Family findById(int po_FamilyCode) {
        try {
            Optional<Family> optionalFamily = familyRepository.findById(po_FamilyCode);
            return optionalFamily.orElseThrow(() -> new RuntimeException("Family with ID " + po_FamilyCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Family");
        }
    }

    @Override
    public List<Family> searchByKeyword(String name) {
        try {
            return familyRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Family by keyword");
        }
    }

    @Override
    public Family findByName(String name) {
        try {
            Optional<Family> optionalFamily = familyRepository.findByName(name);
            return optionalFamily.orElseThrow(() -> new RuntimeException("Family with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Family");
        }
    }

    /*@Override
    public List<Family> findBySubFamilyName(String SubFamilyName) {
        return familyRepository.findBySubFamilyName(SubFamilyName);
    }*/





}

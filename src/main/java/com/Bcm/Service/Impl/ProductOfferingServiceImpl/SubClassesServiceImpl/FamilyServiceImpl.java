package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
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

    @Autowired
    FamilyService familyService;

    @Override
    public Family create(Family family) {
        return familyRepository.save(family);
    }

    @Override
    public List<Family> read() {
        return familyRepository.findAll();
    }

    @Override
    public Family update(int po_FamilyCode, Family updatedFamily) {
        Optional<Family> existingFamilyOptional = familyRepository.findById(po_FamilyCode);

        if (existingFamilyOptional.isPresent()) {
            Family existingFamily = existingFamilyOptional.get();
            existingFamily.setName(updatedFamily.getName());
            return familyRepository.save(existingFamily);
        } else {
            throw new RuntimeException("Could not find Family with ID: " + po_FamilyCode);
        }
    }

    @Override
    public String delete(int po_FamilyCode) {
        familyRepository.deleteById(po_FamilyCode);
        return ("Family was successfully deleted");
    }

    @Override
    public Family findById(int po_FamilyCode) {
        Optional<Family> optionalFamily = familyRepository.findById(po_FamilyCode);
        return optionalFamily.orElseThrow(() -> new RuntimeException("Family with ID " + po_FamilyCode + " not found"));
    }


    @Override
    public List<Family> searchByKeyword(String name) {
        return familyRepository.searchByKeyword(name);
    }


}

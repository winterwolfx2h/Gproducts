package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubFamily;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.SubFamilyRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubFamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubFamilyServiceImpl implements SubFamilyService {

    final SubFamilyRepository subFamilyRepository;
    @Override
    public SubFamily create(SubFamily Subfamily) {
        try {
            return subFamilyRepository.save(Subfamily);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating Family");
        }
    }

    @Override
    public List<SubFamily> read() {
        try {
            return subFamilyRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Families");
        }
    }

    @Override
    public SubFamily findByName(String name) {
        try {
            Optional<SubFamily> optionalFamily = subFamilyRepository.findByName(name);
            return optionalFamily.orElseThrow(() -> new RuntimeException("SubFamily with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding SubFamily");
        }
    }
    /*@Override
    public List<SubFamily> findByParentFamily(Family family) {
        return subFamilyRepository.findByParentFamily(family);
    }*/


}

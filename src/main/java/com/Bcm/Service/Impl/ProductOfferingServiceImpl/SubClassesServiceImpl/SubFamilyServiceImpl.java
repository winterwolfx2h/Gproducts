package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubFamily;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.SubFamilyRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.SubFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubFamilyServiceImpl implements SubFamilyService {


    @Autowired
    SubFamilyRepository subFamilyRepository;



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


}

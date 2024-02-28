package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RelationType;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.RelationTypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.RelationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationTypeServiceImpl implements RelationTypeService {

    @Autowired
    RelationTypeRepository relationTypeRepository;

    @Override
    public RelationType create(RelationType Type) {
        try {
            return relationTypeRepository.save(Type);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating RelationType");
        }
    }

    @Override
    public List<RelationType> read() {
        try {
            return relationTypeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving RelationTypes");
        }
    }

    @Override
    public RelationType update(int poRelationType_code, RelationType updatedType) {
        try {
            Optional<RelationType> existingTypeOptional = relationTypeRepository.findById(poRelationType_code);
            if (existingTypeOptional.isPresent()) {
                RelationType existingType = existingTypeOptional.get();
                existingType.setName(updatedType.getName());
                return relationTypeRepository.save(existingType);
            } else {
                throw new RuntimeException("RelationType with ID " + poRelationType_code + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating RelationType");
        }
    }

    @Override
    public String delete(int poRelationType_code) {
        try {
            relationTypeRepository.deleteById(poRelationType_code);
            return ("RelationType with ID " + poRelationType_code + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting RelationType");
        }
    }

    @Override
    public RelationType findById(int poRelationType_code) {
        try {
            Optional<RelationType> optionalPlan = relationTypeRepository.findById(poRelationType_code);
            return optionalPlan.orElseThrow(() -> new RuntimeException("RelationType with ID " + poRelationType_code + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding RelationType");
        }
    }

    @Override
    public List<RelationType> searchByKeyword(String name) {
        try {
            return relationTypeRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching RelationType by keyword");
        }
    }

    @Override
    public RelationType findByName(String name) {
        try {
            Optional<RelationType> optionalType = relationTypeRepository.findByName(name);
            return optionalType.orElseThrow(() -> new RuntimeException("RelationType with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding RelationType");
        }
    }
}

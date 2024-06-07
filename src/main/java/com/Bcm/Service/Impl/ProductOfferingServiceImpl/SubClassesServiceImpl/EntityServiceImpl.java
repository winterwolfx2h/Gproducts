package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.EntityAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.EntityRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class EntityServiceImpl implements EntityService {

    final EntityRepository entityRepository;
    final ProductOfferingService productOfferingService;

    @Override
    public EligibilityEntity create(EligibilityEntity Entity) {
        try {
            if (findByNameexist(Entity.getName())) {
                throw new EntityAlreadyExistsException("Entity with the same name already exists");
            }
            return entityRepository.save(Entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating Entity", e);
        }
    }


    @Override
    public List<EligibilityEntity> read() {
        try {
            return entityRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Families");
        }
    }

    @Override
    public EligibilityEntity update(int entityCode, EligibilityEntity updatedEntity) {
        Optional<EligibilityEntity> existingEntityOptional = entityRepository.findById(entityCode);
        if (existingEntityOptional.isPresent()) {
            EligibilityEntity existingEntity = existingEntityOptional.get();

            String newName = updatedEntity.getName();
            // Check if there's another Entity with the same name
            if (!existingEntity.getName().equals(newName) && entityRepository.existsByName(newName)) {
                throw new EntityAlreadyExistsException("Entity with name '" + newName + "' already exists.");
            }

            existingEntity.setName(newName);
            existingEntity.setDescription(updatedEntity.getDescription());
            return entityRepository.save(existingEntity);
        } else {
            throw new ResourceNotFoundException("Entity with ID " + entityCode + " not found.");
        }
    }


    @Override
    public String delete(int entityCode) {
        try {
            EligibilityEntity Entity = findById(entityCode);
            entityRepository.deleteById(entityCode);
            //updateProductOfferingsWithDeletedEntity(Entity.getName());
            return ("Entity with ID " + entityCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Entity");
        }
    }

    /*private void updateProductOfferingsWithDeletedEntity(String Entitys) {
        List<ProductOffering> productOfferings = productOfferingService.findByEntitys(Entitys);
        for (ProductOffering offering : productOfferings) {
            offering.setEntitys(null);
            productOfferingService.update(offering.getProduct_id(), offering);
        }
    }*/


    @Override
    public EligibilityEntity findById(int entityCode) {
        try {
            Optional<EligibilityEntity> optionalEntity = entityRepository.findById(entityCode);
            return optionalEntity.orElseThrow(() -> new RuntimeException("Entity with ID " + entityCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Entity");
        }
    }

    @Override
    public List<EligibilityEntity> searchByKeyword(String name) {
        try {
            return entityRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Entity by keyword");
        }
    }

    @Override
    public EligibilityEntity findByName(String name) {
        try {
            Optional<EligibilityEntity> optionalEntity = entityRepository.findByName(name);
            return optionalEntity.orElseThrow(() -> new RuntimeException("Entity with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Entity");
        }
    }

    @Override
    public boolean findByNameexist(String name) {
        try {
            Optional<EligibilityEntity> optionalEntity = entityRepository.findByName(name);
            return optionalEntity.isPresent(); // Return true if Entity exists, false otherwise
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Entity");
        }
    }

    @Override
    public boolean existsById(int entityCode) {
        return entityRepository.existsById(entityCode);
    }
}

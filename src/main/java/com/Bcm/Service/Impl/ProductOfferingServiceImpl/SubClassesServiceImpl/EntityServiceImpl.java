package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.EntityAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.EntityRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.EntityService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EntityServiceImpl implements EntityService {

  private static final String EID = "Entity with ID ";
  private static final String invArg = "Invalid argument provided for finding Entity";
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
      if (!existingEntity.getName().equals(newName) && entityRepository.existsByName(newName)) {
        throw new EntityAlreadyExistsException("Entity with name '" + newName + "' already exists.");
      }

      existingEntity.setName(newName);
      existingEntity.setDescription(updatedEntity.getDescription());
      existingEntity.setChannelCode(updatedEntity.getChannelCode());
      return entityRepository.save(existingEntity);
    } else {
      throw new ResourceNotFoundException(EID + entityCode + " not found.");
    }
  }

  @Override
  public String delete(int entityCode) {
    try {
      entityRepository.deleteById(entityCode);
      return (EID + entityCode + " was successfully deleted");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for deleting Entity");
    }
  }

  @Override
  public EligibilityEntity findById(int entityCode) {
    try {
      Optional<EligibilityEntity> optionalEntity = entityRepository.findById(entityCode);
      return optionalEntity.orElseThrow(() -> new RuntimeException(EID + entityCode + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
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
      return optionalEntity.orElseThrow(() -> new RuntimeException(EID + name + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
    }
  }

  @Override
  public boolean findByNameexist(String name) {
    try {
      Optional<EligibilityEntity> optionalEntity = entityRepository.findByName(name);
      return optionalEntity.isPresent();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
    }
  }

  @Override
  public boolean existsById(int entityCode) {
    return entityRepository.existsById(entityCode);
  }
}

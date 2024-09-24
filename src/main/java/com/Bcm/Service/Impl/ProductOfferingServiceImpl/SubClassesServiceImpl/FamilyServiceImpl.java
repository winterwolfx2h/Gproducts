package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Family.*;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.FamilyRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.SubFamilyRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FamilyServiceImpl implements FamilyService {

  final FamilyRepository familyRepository;
  final ProductOfferingService productOfferingService;
  private final SubFamilyRepository subFamilyRepository;
  private static final String SID = "SubFamily with ID ";
  private static final String FID = "Family with ID ";
  private static final String invArg = "Invalid argument provided for finding Family";
  private static final String NF = " not found.";

  @Transactional
  @Override
  public FamilyResponseDTO createOrUpdateFamily(FamilyRequestDTO familyRequestDTO) {
    Optional<Family> existingFamilyOptional = familyRepository.findByName(familyRequestDTO.getName());
    Family family;
    if (existingFamilyOptional.isPresent()) {
      family = existingFamilyOptional.get();
    } else {
      family = new Family();
      family.setName(familyRequestDTO.getName());
      family.setDescription(familyRequestDTO.getDescription());
    }

    for (SubFamilyRequestDTO subFamilyDTO : familyRequestDTO.getSubFamilies()) {
      SubFamily subFamily =
          subFamilyRepository
              .findBySubFamilyName(subFamilyDTO.getSubFamilyName())
              .orElseGet(
                  () -> {
                    SubFamily newSubFamily = new SubFamily();
                    newSubFamily.setSubFamilyName(subFamilyDTO.getSubFamilyName());
                    newSubFamily.setSubFamilyDescription(subFamilyDTO.getSubFamilyDescription());
                    return newSubFamily;
                  });

      if (!family.getSubFamilies().contains(subFamily)) {
        family.addSubFamily(subFamily);
      }
    }

    family = familyRepository.save(family);

    List<SubFamilyResponseDTO> subFamilyResponseDTOs = new ArrayList<>();
    for (SubFamily subFml : family.getSubFamilies()) {
      SubFamilyResponseDTO subFamilyResponseDTO =
          new SubFamilyResponseDTO(
              subFml.getPo_SubFamilyCode(), subFml.getSubFamilyName(), subFml.getSubFamilyDescription());
      subFamilyResponseDTOs.add(subFamilyResponseDTO);
    }

    return new FamilyResponseDTO(
        family.getPo_FamilyCode(), family.getName(), family.getDescription(), subFamilyResponseDTOs);
  }

  @Override
  public List<Family> read() {
    try {
      return familyRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while retrieving Families");
    }
  }

  @Transactional
  @Override
  public FamilyResponseDTO update(int po_FamilyCode, FamilyRequestDTOUpdate familyRequestDTO) {
    Optional<Family> existingFamilyOptional = familyRepository.findById(po_FamilyCode);
    if (existingFamilyOptional.isPresent()) {
      Family existingFamily = existingFamilyOptional.get();

      String newName = familyRequestDTO.getName();
      if (!existingFamily.getName().equals(newName) && familyRepository.existsByName(newName)) {
        throw new FamilyAlreadyExistsException("Family with name '" + newName + "' already exists.");
      }

      existingFamily.setName(newName);
      existingFamily.setDescription(familyRequestDTO.getDescription());

      Map<Integer, SubFamily> existingSubFamiliesMap =
          existingFamily.getSubFamilies().stream()
              .collect(Collectors.toMap(SubFamily::getPo_SubFamilyCode, subFamily -> subFamily));

      List<SubFamily> updatedSubFamilies = new ArrayList<>();
      for (SubFamilyRequestDTO subFamilyRequestDTO : familyRequestDTO.getSubFamilies()) {
        if (subFamilyRequestDTO.getPo_SubFamilyCode() != null) {
          SubFamily subFamily = existingSubFamiliesMap.get(subFamilyRequestDTO.getPo_SubFamilyCode());
          if (subFamily == null) {
            throw new ResourceNotFoundException(SID + subFamilyRequestDTO.getPo_SubFamilyCode() + NF);
          }
          subFamily.setSubFamilyName(subFamilyRequestDTO.getSubFamilyName());
          subFamily.setSubFamilyDescription(subFamilyRequestDTO.getSubFamilyDescription());
          updatedSubFamilies.add(subFamily);
        } else {
          boolean existsInUpdatedList =
              updatedSubFamilies.stream()
                  .anyMatch(sf -> sf.getSubFamilyName().equals(subFamilyRequestDTO.getSubFamilyName()));
          if (!existsInUpdatedList) {
            SubFamily newSubFamily = new SubFamily();
            newSubFamily.setSubFamilyName(subFamilyRequestDTO.getSubFamilyName());
            newSubFamily.setSubFamilyDescription(subFamilyRequestDTO.getSubFamilyDescription());
            newSubFamily.setFamily(existingFamily);
            updatedSubFamilies.add(newSubFamily);
          }
        }
      }

      existingFamily.getSubFamilies().stream()
          .filter(subFamily -> !updatedSubFamilies.contains(subFamily))
          .forEach(updatedSubFamilies::add);

      existingFamily.getSubFamilies().clear();
      existingFamily.getSubFamilies().addAll(updatedSubFamilies);

      existingFamily = familyRepository.save(existingFamily);

      List<SubFamilyResponseDTO> subFamilyResponseDTOs = new ArrayList<>();
      for (SubFamily subFam : existingFamily.getSubFamilies()) {
        SubFamilyResponseDTO subFamilyResponseDTO =
            new SubFamilyResponseDTO(
                subFam.getPo_SubFamilyCode(), subFam.getSubFamilyName(), subFam.getSubFamilyDescription());
        subFamilyResponseDTOs.add(subFamilyResponseDTO);
      }

      return new FamilyResponseDTO(
          existingFamily.getPo_FamilyCode(),
          existingFamily.getName(),
          existingFamily.getDescription(),
          subFamilyResponseDTOs);
    } else {
      throw new ResourceNotFoundException(FID + po_FamilyCode + NF);
    }
  }

  @Override
  public String delete(int po_FamilyCode) {
    try {
      Family family = findById(po_FamilyCode);
      familyRepository.deleteById(po_FamilyCode);
      updateProductOfferingsWithDeletedFamily(family.getName());
      return (FID + po_FamilyCode + " was successfully deleted");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for deleting Family");
    }
  }

  private void updateProductOfferingsWithDeletedFamily(String familyName) {
    List<ProductOffering> productOfferings = productOfferingService.findByFamilyName(familyName);
    for (ProductOffering offering : productOfferings) {
      offering.setFamilyName(null);
      productOfferingService.update(offering.getProduct_id(), offering);
    }
  }

  @Override
  public Family findById(int po_FamilyCode) {
    try {
      Optional<Family> optionalFamily = familyRepository.findById(po_FamilyCode);
      return optionalFamily.orElseThrow(() -> new RuntimeException(FID + po_FamilyCode + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
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
      return optionalFamily.orElseThrow(() -> new RuntimeException(FID + name + " not found"));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
    }
  }

  @Override
  public boolean findByNameexist(String name) {
    try {
      Optional<Family> optionalFamily = familyRepository.findByName(name);
      return optionalFamily.isPresent();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(invArg);
    }
  }

  @Override
  public boolean existsById(int po_FamilyCode) {
    return familyRepository.existsById(po_FamilyCode);
  }

  @Override
  public List<FamilyResponseDTO> getAllFamilies() {
    List<Family> families = familyRepository.findAll();
    List<FamilyResponseDTO> list = new ArrayList<>();
    for (Family family1 : families) {
      List<SubFamilyResponseDTO> subFamilyResponseDTOs = new ArrayList<>();
      for (SubFamily subFamily : family1.getSubFamilies()) {
        SubFamilyResponseDTO subFamilyResponseDTO =
            new SubFamilyResponseDTO(
                subFamily.getPo_SubFamilyCode(), subFamily.getSubFamilyName(), subFamily.getSubFamilyDescription());
        subFamilyResponseDTOs.add(subFamilyResponseDTO);
      }
      FamilyResponseDTO apply =
          new FamilyResponseDTO(
              family1.getPo_FamilyCode(), family1.getName(), family1.getDescription(), subFamilyResponseDTOs);
      list.add(apply);
    }
    return list;
  }

  @Transactional
  @Override
  public void unlinkSubFamilyFromFamily(int familyId, int subFamilyId) {
    Optional<Family> familyOptional = familyRepository.findById(familyId);
    if (familyOptional.isEmpty()) {
      throw new ResourceNotFoundException(FID + familyId + NF);
    }

    Family family = familyOptional.get();
    Optional<SubFamily> subFamilyOptional = subFamilyRepository.findById(subFamilyId);
    if (subFamilyOptional.isEmpty()) {
      throw new ResourceNotFoundException(SID + subFamilyId + NF);
    }

    SubFamily subFamily = subFamilyOptional.get();
    if (!family.getSubFamilies().contains(subFamily)) {
      throw new IllegalArgumentException(SID + subFamilyId + " is not linked to Family with ID " + familyId);
    }

    family.removeSubFamily(subFamily);
    subFamily.setFamily(null);

    familyRepository.save(family);
    subFamilyRepository.save(subFamily);
  }

  @Override
  public List<SubFamily> readSubFamilies() {
    try {
      return subFamilyRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while retrieving SubFamilies", e);
    }
  }

  @Override
  public boolean findBySubFamilyNameExist(String subFamilyName) {
    try {
      Optional<SubFamily> optionalSubFamily = subFamilyRepository.findBySubFamilyName(subFamilyName);
      return optionalSubFamily.isPresent();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid argument provided for finding SubFamily", e);
    }
  }

  @Override
  @Transactional
  public void deleteSubFamily(int po_SubFamilyCode) {
    Optional<SubFamily> subFamilyOptional = subFamilyRepository.findById(po_SubFamilyCode);
    if (subFamilyOptional.isPresent()) {
      subFamilyRepository.delete(subFamilyOptional.get());
    } else {
      throw new ResourceNotFoundException(SID + po_SubFamilyCode + NF);
    }
  }
}

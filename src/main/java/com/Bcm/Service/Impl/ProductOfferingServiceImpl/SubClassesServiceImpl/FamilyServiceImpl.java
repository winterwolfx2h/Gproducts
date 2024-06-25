package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.FamilyAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.SubClasses.*;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.FamilyRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.SubFamilyRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FamilyServiceImpl implements FamilyService {

    final FamilyRepository familyRepository;
    final ProductOfferingService productOfferingService;
    private final SubFamilyRepository subFamilyRepository;

    @Override
    public Family create(Family family) {
        try {
            if (findByNameexist(family.getName())) {
                throw new FamilyAlreadyExistsException("Family with the same name already exists");
            }
            return familyRepository.save(family);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating Family", e);
        }
    }

    @Transactional
    @Override
    public FamilyResponseDTO createOrUpdateFamily(FamilyRequestDTO familyRequestDTO) {
        // Check if a Family with the given name already exists
        Optional<Family> existingFamilyOptional = familyRepository.findByName(familyRequestDTO.getName());
        Family family;
        if (existingFamilyOptional.isPresent()) {
            // If Family exists, use it
            family = existingFamilyOptional.get();
        } else {
            // If Family does not exist, create a new one
            family = new Family();
            family.setName(familyRequestDTO.getName());
            family.setDescription(familyRequestDTO.getDescription());
        }

        // Check if a SubFamily with the given name already exists
        SubFamily subFamily = subFamilyRepository.findBySubFamilyName(familyRequestDTO.getSubFamilyName())
                .orElseGet(() -> {
                    // Create a new SubFamily entity if it does not exist
                    SubFamily newSubFamily = new SubFamily();
                    newSubFamily.setSubFamilyName(familyRequestDTO.getSubFamilyName());
                    return newSubFamily;
                });

        // Add the SubFamily to the Family (if not already associated)
        if (!family.getSubFamilies().contains(subFamily)) {
            family.addSubFamily(subFamily);
        }

        // Save the Family (will cascade to SubFamily if new)
        family = familyRepository.save(family);

        // Prepare response DTO
        List<SubFamilyResponseDTO> subFamilyResponseDTOs = family.getSubFamilies().stream()
                .map(subFam -> new SubFamilyResponseDTO(subFam.getPo_SubFamilyCode(), subFam.getSubFamilyName()))
                .collect(Collectors.toList());

        return new FamilyResponseDTO(family.getPo_FamilyCode(), family.getName(), family.getDescription(), subFamilyResponseDTOs);
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
        Optional<Family> existingFamilyOptional = familyRepository.findById(po_FamilyCode);
        if (existingFamilyOptional.isPresent()) {
            Family existingFamily = existingFamilyOptional.get();

            String newName = updatedFamily.getName();
            // Check if there's another family with the same name
            if (!existingFamily.getName().equals(newName) && familyRepository.existsByName(newName)) {
                throw new FamilyAlreadyExistsException("Family with name '" + newName + "' already exists.");
            }

            existingFamily.setName(newName);
            existingFamily.setDescription(updatedFamily.getDescription());
            return familyRepository.save(existingFamily);
        } else {
            throw new ResourceNotFoundException("Family with ID " + po_FamilyCode + " not found.");
        }
    }

    @Override
    public String delete(int po_FamilyCode) {
        try {
            Family family = findById(po_FamilyCode);
            familyRepository.deleteById(po_FamilyCode);
            updateProductOfferingsWithDeletedFamily(family.getName());
            return ("Family with ID " + po_FamilyCode + " was successfully deleted");
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

    @Override
    public boolean findByNameexist(String name) {
        try {
            Optional<Family> optionalFamily = familyRepository.findByName(name);
            return optionalFamily.isPresent(); // Return true if family exists, false otherwise
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Family");
        }
    }

    @Override
    public boolean existsById(int po_FamilyCode) {
        return familyRepository.existsById(po_FamilyCode);
    }
}

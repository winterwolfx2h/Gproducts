package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface FamilyService {

    @Transactional
    FamilyResponseDTO createOrUpdateFamily(FamilyRequestDTO familyRequestDTO);

    List<Family> read();

    FamilyResponseDTO update(int po_FamilyCode, FamilyRequestDTOUpdate familyRequestDTO);

    String delete(int po_FamilyCode);

    Family findById(int po_FamilyCode);

    List<Family> searchByKeyword(String name);

    Family findByName(String name);

    boolean findByNameexist(String name);

    boolean existsById(int po_FamilyCode);

    List<FamilyResponseDTO> getAllFamilies();

    void unlinkSubFamilyFromFamily(int familyId, int subFamilyId);

    List<SubFamily> readSubFamilies();

    boolean findBySubFamilyNameExist(String subFamilyName);

    void deleteSubFamily(int po_SubFamilyCode);
}

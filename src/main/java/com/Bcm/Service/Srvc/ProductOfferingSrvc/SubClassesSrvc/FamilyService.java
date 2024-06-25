package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductOfferingABE.SubClasses.FamilyRequestDTO;
import com.Bcm.Model.ProductOfferingABE.SubClasses.FamilyResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface FamilyService {

    Family create(Family family);

    @Transactional
    FamilyResponseDTO createOrUpdateFamily(FamilyRequestDTO familyRequestDTO);

    List<Family> read();

    Family update(int po_FamilyCode, Family family);

    String delete(int po_FamilyCode);

    Family findById(int po_FamilyCode);

    List<Family> searchByKeyword(String name);

    Family findByName(String name);

    boolean findByNameexist(String name);

    boolean existsById(int po_FamilyCode);
}

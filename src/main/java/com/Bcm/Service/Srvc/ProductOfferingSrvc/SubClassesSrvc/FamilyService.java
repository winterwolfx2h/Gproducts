package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FamilyService {

    Family create(Family family);

    List<Family> read();

    Family update(int po_FamilyCode, Family family);

    String delete(int po_FamilyCode);

    Family findById(int po_FamilyCode);

    List<Family> searchByKeyword(String name);

    Family findByName(String name);

    boolean findByNameexist(String name);

    /* List<Family> findBySubFamilyName(String SubFamilyName);*/

    boolean existsById(int po_FamilyCode);



}

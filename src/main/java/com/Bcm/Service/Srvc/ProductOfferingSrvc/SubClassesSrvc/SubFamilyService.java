package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Family;
import com.Bcm.Model.ProductOfferingABE.SubClasses.SubFamily;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SubFamilyService {
    SubFamily create(SubFamily Subfamily);

    List<SubFamily> read();

    SubFamily findByName(String name);

    /*List<SubFamily> findByParentFamily(Family family);*/

}

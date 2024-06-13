package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface EntityService {

  EligibilityEntity create(EligibilityEntity entity);

  List<EligibilityEntity> read();

  EligibilityEntity update(int entityCode, EligibilityEntity entity);

  String delete(int entityCode);

  EligibilityEntity findById(int entityCode);

  List<EligibilityEntity> searchByKeyword(String name);

  EligibilityEntity findByName(String name);

  boolean findByNameexist(String name);

  boolean existsById(int entityCode);
}

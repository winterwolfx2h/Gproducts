package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.POCharacteristicType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface POCharacteristicTypeService {

    POCharacteristicType create(POCharacteristicType POCharacteristicType);

    List<POCharacteristicType> read();

    POCharacteristicType update(int poCharType_code, POCharacteristicType POCharacteristicType);

    String delete(int poCharType_code);

    POCharacteristicType findById(int poCharType_code);

    List<POCharacteristicType> searchByKeyword(String name);

    POCharacteristicType findByName(String name);


}

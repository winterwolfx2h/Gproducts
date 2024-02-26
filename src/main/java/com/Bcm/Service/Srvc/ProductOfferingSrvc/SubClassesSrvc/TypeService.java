package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeService {

    Type create(Type Type);

    List<Type> read();

    Type update(int poType_code, Type Type);

    String delete(int po_TypeCode);

    Type findById(int po_TypeCode);

    Type findByName(String name);

    List<Type> searchByKeyword(String name);


}

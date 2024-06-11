package com.Bcm.Service.Srvc.ProductOfferingSrvc;


import com.Bcm.Model.ProductOfferingABE.Type;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TypeService {

    Type create(Type type);

    List<Type> read();

    Type update(int type_id, Type type);

    String delete(int type_id);

    Type findById(int type_id);

    List<Type> searchByKeyword(String typeName);

    Type findByTypeName(String typeName);

    boolean existsById(int type_id);
}

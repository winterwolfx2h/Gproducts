package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.ProductOfferingABE.Methods;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MethodsService {

  Methods create(Methods methods);

  List<Methods> read();

  Methods update(int method_Id, Methods methods);

  String delete(int method_Id);

  Methods findById(int method_Id);

  List<Methods> searchByKeyword(String name);

  Methods findByName(String name);
}

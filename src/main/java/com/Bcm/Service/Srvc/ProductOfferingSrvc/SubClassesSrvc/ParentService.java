package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Parent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParentService {

    Parent create(Parent parent);

    List<Parent> read();

    Parent update(int po_SpecCode, Parent parent);

    String delete(int po_ParentCode);

    Parent findById(int po_ParentCode);

    List<Parent> searchByKeyword(String name);
}

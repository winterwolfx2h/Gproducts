package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.RelationType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RelationTypeService {

    RelationType create(RelationType RelationType);

    List<RelationType> read();

    RelationType update(int poRelationType_code, RelationType RelationType);

    String delete(int po_RelationTypeCode);

    RelationType findById(int po_RelationTypeCode);

    RelationType findByName(String name);

    List<RelationType> searchByKeyword(String name);


}

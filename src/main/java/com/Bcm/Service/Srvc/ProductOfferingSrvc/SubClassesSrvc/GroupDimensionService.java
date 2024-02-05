package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.GroupDimension;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupDimensionService {

    GroupDimension create(GroupDimension groupDimension);

    List<GroupDimension> read();

    GroupDimension update(int po_GdCode, GroupDimension groupDimension);

    String delete(int po_GdCode);

    GroupDimension findById(int po_GdCode);

    List<GroupDimension> searchByKeyword(String name);
}

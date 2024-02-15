package com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.CableType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CableTypeService {

    CableType create(CableType cableType);

    List<CableType> read();

    CableType update(int CbTID, CableType cableType);

    String delete(int CbTID);

    CableType findById(int CbTID);

    List<CableType> searchByKeyword(String name);
}
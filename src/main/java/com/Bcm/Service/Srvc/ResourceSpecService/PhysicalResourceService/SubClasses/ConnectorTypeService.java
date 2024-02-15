package com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.ConnectorType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConnectorTypeService {

    ConnectorType create(ConnectorType connectorType);

    List<ConnectorType> read();

    ConnectorType update(int CnTID, ConnectorType connectorType);

    String delete(int CnTID);

    ConnectorType findById(int CnTID);

    List<ConnectorType> searchByKeyword(String name);
}
package com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalConnector;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhysicalConnectorService {
    PhysicalConnector create(PhysicalConnector physicalConnector);

    List<PhysicalConnector> read();

    PhysicalConnector update(int PCnID, PhysicalConnector physicalConnector);

    String delete(int PCnID);

    PhysicalConnector findById(int PCnID);
}
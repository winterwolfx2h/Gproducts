package com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalComponent;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PhysicalComponentService {
    PhysicalComponent create(PhysicalComponent physicalComponent);

    List<PhysicalComponent> read();

    PhysicalComponent update(int PCpID, PhysicalComponent physicalComponent);

    String delete(int PCpID);

    PhysicalComponent findById(int PCpID);
}
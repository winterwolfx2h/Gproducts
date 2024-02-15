package com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalResource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PhysicalResourceService {
    PhysicalResource create(PhysicalResource physicalResource);

    List<PhysicalResource> read();

    PhysicalResource update(int PRID, PhysicalResource physicalResource);

    String delete(int PRID);

    PhysicalResource findById(int PRID);
}
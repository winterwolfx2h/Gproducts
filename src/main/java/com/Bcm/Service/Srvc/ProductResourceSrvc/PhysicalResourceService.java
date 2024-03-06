package com.Bcm.Service.Srvc.ProductResourceSrvc;

import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhysicalResourceService {

    PhysicalResource create(PhysicalResource PhysicalResource);

    List<PhysicalResource> read();

    PhysicalResource update(int phyResourceId, PhysicalResource PhysicalResource);

    String delete(int phyResourceId);

    PhysicalResource findById(int phyResourceId);

    List<PhysicalResource> searchByKeyword(String physicalResourceType);

    PhysicalResource findByPhysicalResourceType(String physicalResourceType);


}

package com.Bcm.Service.Srvc.ProductResourceSrvc;

import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhysicalResourceService {

    PhysicalResource create(PhysicalResource PhysicalResource);

    List<PhysicalResource> read();

    PhysicalResource update(int PR_id, PhysicalResource PhysicalResource);

    String delete(int PR_id);

    PhysicalResource findById(int PR_id);

    List<PhysicalResource> searchByKeyword(String physicalResourceType);

    PhysicalResource findByPhysicalResourceType(String physicalResourceType);

    boolean existsById(int PR_id);


    PhysicalResource changeServiceStatus(int PR_id);
}

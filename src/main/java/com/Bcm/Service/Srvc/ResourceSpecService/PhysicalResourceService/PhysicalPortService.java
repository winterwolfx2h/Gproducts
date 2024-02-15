package com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.PhysicalPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhysicalPortService {
    PhysicalPort create(PhysicalPort physicalPort);

    List<PhysicalPort> read();

    PhysicalPort update(int PPID, PhysicalPort physicalPort);

    String delete(int PPID);

    PhysicalPort findById(int PPID);
}
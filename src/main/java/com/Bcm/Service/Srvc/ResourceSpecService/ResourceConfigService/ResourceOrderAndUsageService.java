package com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceOrderAndUsage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResourceOrderAndUsageService {
    ResourceOrderAndUsage create(ResourceOrderAndUsage resourceOrderAndUsage);

    List<ResourceOrderAndUsage> read();

    ResourceOrderAndUsage update(int ROUID, ResourceOrderAndUsage resourceOrderAndUsage);

    String delete(int ROUID);

    ResourceOrderAndUsage findById(int ROUID);
}
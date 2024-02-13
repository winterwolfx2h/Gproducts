package com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.ResourceConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ResourceConfigurationService {
    ResourceConfiguration create(ResourceConfiguration resourceConfiguration);

    List<ResourceConfiguration> read();

    ResourceConfiguration update(int LRID, ResourceConfiguration resourceConfiguration);

    String delete(int LRID);

    ResourceConfiguration findById(int LRID);
}
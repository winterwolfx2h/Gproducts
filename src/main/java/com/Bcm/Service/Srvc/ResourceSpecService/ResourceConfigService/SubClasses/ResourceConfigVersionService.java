package com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ResourceConfigVersion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResourceConfigVersionService {

    ResourceConfigVersion create(ResourceConfigVersion resourceConfigVersion);

    List<ResourceConfigVersion> read();

    ResourceConfigVersion update(int RCVID, ResourceConfigVersion resourceConfigVersion);

    String delete(int RCVID);

    ResourceConfigVersion findById(int RCVID);

    List<ResourceConfigVersion> searchByKeyword(String name);
}
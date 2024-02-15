package com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceService;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface LogicalResourceService {
    LogicalResource create(LogicalResource logicalResource);

    List<LogicalResource> read();

    LogicalResource update(int LRID, LogicalResource logicalResource);

    String delete(int LRID);

    LogicalResource findById(int LRID);
}
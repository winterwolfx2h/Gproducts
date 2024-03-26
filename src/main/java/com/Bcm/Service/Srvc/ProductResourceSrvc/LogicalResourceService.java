package com.Bcm.Service.Srvc.ProductResourceSrvc;

import com.Bcm.Model.ProductResourceABE.LogicalResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogicalResourceService {

    LogicalResource create(LogicalResource LogicalResource);

    List<LogicalResource> read();

    LogicalResource update(int logResourceId, LogicalResource LogicalResource);

    String delete(int logResourceId);

    LogicalResource findById(int logResourceId);

    List<LogicalResource> searchByKeyword(String logicalResourceType);

    LogicalResource findByLogicalResourceType(String logicalResourceType);

    boolean existsById(int logResourceId);


}

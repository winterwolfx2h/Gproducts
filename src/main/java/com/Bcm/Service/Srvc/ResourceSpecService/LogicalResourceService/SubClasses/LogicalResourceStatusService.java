package com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceService.SubClasses;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.LogicalResourceStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogicalResourceStatusService {

    LogicalResourceStatus create(LogicalResourceStatus logicalResourceStatus);

    List<LogicalResourceStatus> read();

    LogicalResourceStatus update(int LRSID, LogicalResourceStatus logicalResourceStatus);

    String delete(int LRSID);

    LogicalResourceStatus findById(int LRSID);

    List<LogicalResourceStatus> searchByKeyword(String name);

}
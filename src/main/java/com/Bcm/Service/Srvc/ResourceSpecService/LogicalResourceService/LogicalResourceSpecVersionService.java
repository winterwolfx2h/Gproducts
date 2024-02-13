package com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceSpecVersionService;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.LogicalResourceSpecVersion;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface LogicalResourceSpecVersionService {
    LogicalResourceSpecVersion create(LogicalResourceSpecVersion logicalResourceSpecVersion);

    List<LogicalResourceSpecVersion> read();

    LogicalResourceSpecVersion update(int LRSVID, LogicalResourceSpecVersion logicalResourceSpecVersion);

    String delete(int LRSVID);

    LogicalResourceSpecVersion findById(int LRSVID);
}
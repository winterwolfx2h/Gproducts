package com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUUsageStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ROUUsageStatusService {

    ROUUsageStatus create(ROUUsageStatus rouUsageStatus);

    List<ROUUsageStatus> read();

    ROUUsageStatus update(int RUSID, ROUUsageStatus rouUsageStatus);

    String delete(int RUSID);

    ROUUsageStatus findById(int RUSID);

    List<ROUUsageStatus> searchByKeyword(String name);
}
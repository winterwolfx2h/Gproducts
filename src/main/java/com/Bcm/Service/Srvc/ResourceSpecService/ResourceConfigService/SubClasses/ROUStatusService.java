package com.Bcm.Service.Srvc.ResourceSpecService.ResourceConfigService.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ROUStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ROUStatusService {

    ROUStatus create(ROUStatus rouStatus);

    List<ROUStatus> read();

    ROUStatus update(int RSID, ROUStatus rouStatus);

    String delete(int RSID);

    ROUStatus findById(int RSID);

    List<ROUStatus> searchByKeyword(String name);
}
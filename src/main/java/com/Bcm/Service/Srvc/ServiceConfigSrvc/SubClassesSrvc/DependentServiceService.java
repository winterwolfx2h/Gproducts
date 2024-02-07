package com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc;

import com.Bcm.Model.ServiceABE.SubClasses.DependentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DependentServiceService {

    DependentService create(DependentService dependentService);

    List<DependentService> read();

    DependentService update(int DS_code, DependentService dependentService);

    String delete(int DS_code);

    DependentService findById(int DS_code);

    List<DependentService> searchByKeyword(String name);
}

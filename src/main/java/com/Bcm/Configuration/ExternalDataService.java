package com.Bcm.Configuration;

import com.Bcm.Model.BSCSModels.RatePlanBSCS;
import com.Bcm.Repository.ExternalBSCSDBRepo.ExternalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalDataService {

    @Autowired
    private ExternalDataRepository externalDataRepository;

    public List<RatePlanBSCS> fetchDataFromExternalDatabase() {
        return externalDataRepository.findAll();
    }
}

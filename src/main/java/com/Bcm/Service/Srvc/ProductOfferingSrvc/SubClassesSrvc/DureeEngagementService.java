package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DureeEngagement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DureeEngagementService {

    DureeEngagement create(DureeEngagement dureeEngagement);

    List<DureeEngagement> read();

    DureeEngagement update(int po_DureeEngCode, DureeEngagement dureeEngagement);

    String delete(int po_DureeEngCode);

    DureeEngagement findById(int po_DureeEngCode);

    List<DureeEngagement> searchByKeyword(String name);
}

package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.DateFinEngagement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DateFinEngagementService {

    DateFinEngagement create(DateFinEngagement dateFinEngagement);

    List<DateFinEngagement> read();

    DateFinEngagement update(int po_DateFinEngCode, DateFinEngagement dateFinEngagement);

    String delete(int po_DateFinEngCode);

    DateFinEngagement findById(int po_DateFinEngCode);

    List<DateFinEngagement> searchByKeyword(String name);
}

package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.TimeZoneType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TimeZoneTypeService {

    TimeZoneType create(TimeZoneType timeZoneType);

    List<TimeZoneType> read();

    TimeZoneType update(int po_TimeZoneTypeCode, TimeZoneType timeZoneType);

    String delete(int po_TimeZoneTypeCode);

    TimeZoneType findById(int po_TimeZoneTypeCode);

    List<TimeZoneType> searchByKeyword(String name);
}

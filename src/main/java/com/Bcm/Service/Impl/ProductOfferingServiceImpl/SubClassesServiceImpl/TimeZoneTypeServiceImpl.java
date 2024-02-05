package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.TimeZoneType;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.TimeZoneTypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.TimeZoneTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeZoneTypeServiceImpl implements TimeZoneTypeService {


    @Autowired
    TimeZoneTypeRepository timeZoneTypeRepository;

    @Autowired
    TimeZoneTypeService timeZoneTypeService;

    @Override
    public TimeZoneType create(TimeZoneType TimeZoneType) {
        return timeZoneTypeRepository.save(TimeZoneType);
    }

    @Override
    public List<TimeZoneType> read() {
        return timeZoneTypeRepository.findAll();
    }

    @Override
    public TimeZoneType update(int po_TimeZoneTypeCode, TimeZoneType updatedTimeZoneType) {
        Optional<TimeZoneType> existingTimeZoneTypeOptional = timeZoneTypeRepository.findById(po_TimeZoneTypeCode);

        if (existingTimeZoneTypeOptional.isPresent()) {
            TimeZoneType existingTimeZoneType = existingTimeZoneTypeOptional.get();
            existingTimeZoneType.setName(updatedTimeZoneType.getName());
            return timeZoneTypeRepository.save(existingTimeZoneType);
        } else {
            throw new RuntimeException("Could not find Time Zone Type with ID: " + po_TimeZoneTypeCode);
        }
    }

    @Override
    public String delete(int po_TimeZoneTypeCode) {
        timeZoneTypeRepository.deleteById(po_TimeZoneTypeCode);
        return ("Time Zone Type was successfully deleted");
    }

    @Override
    public TimeZoneType findById(int po_TimeZoneTypeCode) {
        Optional<TimeZoneType> optionalPlan = timeZoneTypeRepository.findById(po_TimeZoneTypeCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Time Zone Type with ID " + po_TimeZoneTypeCode + " not found"));
    }


    @Override
    public List<TimeZoneType> searchByKeyword(String name) {
        return timeZoneTypeRepository.searchByKeyword(name);
    }


}

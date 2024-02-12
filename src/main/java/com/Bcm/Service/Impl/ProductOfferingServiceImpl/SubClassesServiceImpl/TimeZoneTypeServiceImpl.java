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
    public TimeZoneType create(TimeZoneType timeZoneType) {
        try {
            return timeZoneTypeRepository.save(timeZoneType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating TimeZoneType");
        }
    }

    @Override
    public List<TimeZoneType> read() {
        try {
            return timeZoneTypeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving TimeZoneTypes");
        }
    }

    @Override
    public TimeZoneType update(int po_TimeZoneTypeCode, TimeZoneType updatedTimeZoneType) {
        try {
            Optional<TimeZoneType> existingTimeZoneTypeOptional = timeZoneTypeRepository.findById(po_TimeZoneTypeCode);
            if (existingTimeZoneTypeOptional.isPresent()) {
                TimeZoneType existingTimeZoneType = existingTimeZoneTypeOptional.get();
                existingTimeZoneType.setName(updatedTimeZoneType.getName());
                return timeZoneTypeRepository.save(existingTimeZoneType);
            } else {
                throw new RuntimeException("Time Zone Type with ID " + po_TimeZoneTypeCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating TimeZoneType");
        }
    }

    @Override
    public String delete(int po_TimeZoneTypeCode) {
        try {
            timeZoneTypeRepository.deleteById(po_TimeZoneTypeCode);
            return "Time Zone Type with ID " + po_TimeZoneTypeCode + " was successfully deleted";
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting TimeZoneType");
        }
    }

    @Override
    public TimeZoneType findById(int po_TimeZoneTypeCode) {
        try {
            Optional<TimeZoneType> optionalTimeZoneType = timeZoneTypeRepository.findById(po_TimeZoneTypeCode);
            return optionalTimeZoneType.orElseThrow(() -> new RuntimeException("Time Zone Type with ID " + po_TimeZoneTypeCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding TimeZoneType");
        }
    }

    @Override
    public List<TimeZoneType> searchByKeyword(String name) {
        try {
            return timeZoneTypeRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching TimeZoneType by keyword");
        }
    }
}

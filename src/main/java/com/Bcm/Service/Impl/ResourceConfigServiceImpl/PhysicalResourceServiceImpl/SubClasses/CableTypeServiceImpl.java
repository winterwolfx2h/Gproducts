package com.Bcm.Service.Impl.ResourceConfigServiceImpl.PhysicalResourceServiceImpl.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.CableType;
import com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo.SubClasses.CableTypeRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.SubClasses.CableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CableTypeServiceImpl implements CableTypeService {

    @Autowired
    CableTypeRepository CableTypeRepository;

    @Override
    public CableType create(CableType  CableType) {
        try {
            if ( CableType == null) {
                throw new IllegalArgumentException(" CableType cannot be null");
            }
            return  CableTypeRepository.save( CableType);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating  CableType: " + e.getMessage());
        }
    }

    @Override
    public List< CableType> read() {
        return  CableTypeRepository.findAll();
    }
    @Override
    public  CableType update(int FID,  CableType updatedCableType) {
        try {
            Optional<CableType> existingCableTypeOptional = CableTypeRepository.findById(FID);

            if (existingCableTypeOptional.isPresent()) {
                CableType existingCableType = existingCableTypeOptional.get();
                existingCableType.setName(updatedCableType.getName());
                return  CableTypeRepository.save(existingCableType);
            } else {
                throw new RuntimeException("Could not find  CableType with ID: " + FID);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid  CableType Code: " + FID);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the  CableType: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int FID) {
        Optional<CableType> optionalCableType =  CableTypeRepository.findById(FID);
        if (optionalCableType.isPresent()) {
            CableTypeRepository.deleteById(FID);
            return (" CableType was successfully deleted");
        } else {
            throw new RuntimeException("Could not find  CableType with ID: " + FID);
        }
    }
    @Override
    public  CableType findById(int FID) {
        Optional<CableType> optionalCableType =  CableTypeRepository.findById(FID);
        return optionalCableType.orElseThrow(() -> new RuntimeException(" CableType with ID " + FID + " not found"));
    }
    @Override
    public List< CableType> searchByKeyword(String name) {
        return  CableTypeRepository.searchByKeyword(name);
    }
}
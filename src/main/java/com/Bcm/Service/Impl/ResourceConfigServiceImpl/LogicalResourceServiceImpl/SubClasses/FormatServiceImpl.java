package com.Bcm.Service.Impl.ResourceConfigServiceImpl.LogicalResourceServiceImpl.SubClasses;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.Format;
import com.Bcm.Repository.ResourceSpecRepo.LogicalResourceRepo.SubClasses.FormatRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceService.SubClasses.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormatServiceImpl implements FormatService {

    @Autowired
    FormatRepository FormatRepository;

    @Override
    public Format create(Format Format) {
        try {
            if (Format == null) {
                throw new IllegalArgumentException(" Format cannot be null");
            }
            return FormatRepository.save(Format);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating  Format: " + e.getMessage());
        }
    }

    @Override
    public List<Format> read() {
        return FormatRepository.findAll();
    }

    @Override
    public Format update(int FID, Format updatedFormat) {
        try {
            Optional<Format> existingFormatOptional = FormatRepository.findById(FID);

            if (existingFormatOptional.isPresent()) {
                Format existingFormat = existingFormatOptional.get();
                existingFormat.setName(updatedFormat.getName());
                return FormatRepository.save(existingFormat);
            } else {
                throw new RuntimeException("Could not find  Format with ID: " + FID);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid  Format Code: " + FID);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the  Format: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int FID) {
        Optional<Format> optionalFormat = FormatRepository.findById(FID);
        if (optionalFormat.isPresent()) {
            FormatRepository.deleteById(FID);
            return (" Format was successfully deleted");
        } else {
            throw new RuntimeException("Could not find  Format with ID: " + FID);
        }
    }

    @Override
    public Format findById(int FID) {
        Optional<Format> optionalFormat = FormatRepository.findById(FID);
        return optionalFormat.orElseThrow(() -> new RuntimeException(" Format with ID " + FID + " not found"));
    }

    @Override
    public List<Format> searchByKeyword(String name) {
        return FormatRepository.searchByKeyword(name);
    }
}

package com.Bcm.Service.Impl.ResourceConfigServiceImpl.PhysicalResourceServiceImpl.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.ConnectorType;
import com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo.SubClasses.ConnectorTypeRepository;
import com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.SubClasses.ConnectorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConnectorTypeServiceImpl implements ConnectorTypeService {

    @Autowired
    ConnectorTypeRepository ConnectorTypeRepository;

    @Override
    public ConnectorType create(ConnectorType ConnectorType) {
        try {
            if (ConnectorType == null) {
                throw new IllegalArgumentException(" ConnectorType cannot be null");
            }
            return ConnectorTypeRepository.save(ConnectorType);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("There was a Data integrity violation occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating  ConnectorType: " + e.getMessage());
        }
    }

    @Override
    public List<ConnectorType> read() {
        return ConnectorTypeRepository.findAll();
    }

    @Override
    public ConnectorType update(int FID, ConnectorType updatedConnectorType) {
        try {
            Optional<ConnectorType> existingConnectorTypeOptional = ConnectorTypeRepository.findById(FID);

            if (existingConnectorTypeOptional.isPresent()) {
                ConnectorType existingConnectorType = existingConnectorTypeOptional.get();
                existingConnectorType.setName(updatedConnectorType.getName());
                return ConnectorTypeRepository.save(existingConnectorType);
            } else {
                throw new RuntimeException("Could not find  ConnectorType with ID: " + FID);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid  ConnectorType Code: " + FID);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the  ConnectorType: " + ex.getMessage());
        }
    }

    @Override
    public String delete(int FID) {
        Optional<ConnectorType> optionalConnectorType = ConnectorTypeRepository.findById(FID);
        if (optionalConnectorType.isPresent()) {
            ConnectorTypeRepository.deleteById(FID);
            return (" ConnectorType was successfully deleted");
        } else {
            throw new RuntimeException("Could not find  ConnectorType with ID: " + FID);
        }
    }

    @Override
    public ConnectorType findById(int FID) {
        Optional<ConnectorType> optionalConnectorType = ConnectorTypeRepository.findById(FID);
        return optionalConnectorType.orElseThrow(() -> new RuntimeException(" ConnectorType with ID " + FID + " not found"));
    }

    @Override
    public List<ConnectorType> searchByKeyword(String name) {
        return ConnectorTypeRepository.searchByKeyword(name);
    }
}
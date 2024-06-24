package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.DatabaseOperationException;
import com.Bcm.Exception.MethodsAlreadyExistsException;
import com.Bcm.Exception.ResourceNotFoundException;
import com.Bcm.Model.ProductOfferingABE.Methods;
import com.Bcm.Repository.ProductOfferingRepo.MethodsRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.MethodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MethodsServiceImpl implements MethodsService {

    final MethodsRepository methodsRepository;

    @Override
    public Methods create(Methods methods) {
        Optional<Methods> existingMethod = methodsRepository.findByName(methods.getName());
        if (existingMethod.isPresent()) {
            throw new MethodsAlreadyExistsException("Method with name: " + methods.getName() + " already exists.");
        }
        try {
            return methodsRepository.save(methods);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating Methods", e);
        }
    }

    @Override
    public List<Methods> read() {
        try {
            return methodsRepository.findAllOrderedByMethodId();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Methods");
        }
    }

    @Override
    public Methods update(int method_Id, Methods updatedMethods) {
        Optional<Methods> existingMethodsOptional = methodsRepository.findById(method_Id);
        if (existingMethodsOptional.isPresent()) {
            Methods existingMethods = existingMethodsOptional.get();

            String newName = updatedMethods.getName();
            // Check if there's another Methods with the same name
            if (!existingMethods.getName().equals(newName) && methodsRepository.existsByName(newName)) {
                throw new MethodsAlreadyExistsException("Methods with name '" + newName + "' already exists.");
            }
            existingMethods.setName(updatedMethods.getName());
            existingMethods.setUrl(updatedMethods.getUrl());
            return methodsRepository.save(existingMethods);
        } else {
            throw new ResourceNotFoundException("Methods with ID " + method_Id + " not found.");
        }
    }

    @Override
    public String delete(int method_Id) {
        try {
            Methods methods = findById(method_Id);
            methodsRepository.deleteById(method_Id);
            return ("Methods with ID " + method_Id + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Methods");
        }
    }

    @Override
    public Methods findById(int method_Id) {
        try {
            Optional<Methods> optionalMethods = methodsRepository.findById(method_Id);
            return optionalMethods.orElseThrow(() -> new RuntimeException("Methods with ID " + method_Id + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Methods");
        }
    }

    @Override
    public List<Methods> searchByKeyword(String name) {
        try {
            return methodsRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Methods by keyword");
        }
    }

    @Override
    public Methods findByName(String name) {
        try {
            Optional<Methods> optionalMethods = methodsRepository.findByName(name);
            return optionalMethods.orElseThrow(() -> new RuntimeException("Methods with name " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Methods");
        }
    }
}

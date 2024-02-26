package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Type;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.TypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeRepository typeRepository;

    @Override
    public Type create(Type Type) {
        try {
            return typeRepository.save(Type);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating Type");
        }
    }

    @Override
    public List<Type> read() {
        try {
            return typeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving Types");
        }
    }

    @Override
    public Type update(int po_TypeCode, Type updatedType) {
        try {
            Optional<Type> existingTypeOptional = typeRepository.findById(po_TypeCode);
            if (existingTypeOptional.isPresent()) {
                Type existingType = existingTypeOptional.get();
                existingType.setName(updatedType.getName());
                return typeRepository.save(existingType);
            } else {
                throw new RuntimeException("Type with ID " + po_TypeCode + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating Type");
        }
    }

    @Override
    public String delete(int po_TypeCode) {
        try {
            typeRepository.deleteById(po_TypeCode);
            return ("Type with ID " + po_TypeCode + " was successfully deleted");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting Type");
        }
    }

    @Override
    public Type findById(int po_TypeCode) {
        try {
            Optional<Type> optionalPlan = typeRepository.findById(po_TypeCode);
            return optionalPlan.orElseThrow(() -> new RuntimeException("Type with ID " + po_TypeCode + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Type");
        }
    }

    @Override
    public List<Type> searchByKeyword(String name) {
        try {
            return typeRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching Type by keyword");
        }
    }

    @Override
    public Type findByName(String name) {
        try {
            Optional<Type> optionalType = typeRepository.findByName(name);
            return optionalType.orElseThrow(() -> new RuntimeException("Type with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Type");
        }
    }
}

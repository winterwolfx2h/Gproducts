package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.POCharacteristicType;
import com.Bcm.Repository.ProductOfferingRepo.POCharacteristicTypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.POCharacteristicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class POCharacteristicTypeServiceImpl implements POCharacteristicTypeService {

    @Autowired
    POCharacteristicTypeRepository POCharacteristicTypeRepository;

    @Override
    public POCharacteristicType create(POCharacteristicType POCharacteristicType) {
        return POCharacteristicTypeRepository.save(POCharacteristicType);
    }

    @Override
    public List<POCharacteristicType> read() {
        return POCharacteristicTypeRepository.findAll();
    }

    @Override
    public POCharacteristicType update(int poCharType_code, POCharacteristicType updatedPOCharacteristicType) {
        Optional<POCharacteristicType> existingProductOptional = POCharacteristicTypeRepository.findById(poCharType_code);

        if (existingProductOptional.isPresent()) {
            POCharacteristicType existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedPOCharacteristicType.getName());
            existingProduct.setIntCharID(updatedPOCharacteristicType.getIntCharID());
            existingProduct.setExtCharID(updatedPOCharacteristicType.getExtCharID());
            existingProduct.setProvider(updatedPOCharacteristicType.getProvider());

            return POCharacteristicTypeRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find POCharacteristicType with ID: " + poCharType_code);
        }
    }

    @Override
    public String delete(int poCharType_code) {
        POCharacteristicTypeRepository.deleteById(poCharType_code);
        return ("POCharacteristicType was successfully deleted");
    }

    @Override
    public POCharacteristicType findById(int poCharType_code) {
        Optional<POCharacteristicType> optionalPlan = POCharacteristicTypeRepository.findById(poCharType_code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("POCharacteristicType with ID " + poCharType_code + " not found"));
    }

    @Override
    public List<POCharacteristicType> searchByKeyword(String name) {
        return POCharacteristicTypeRepository.searchByKeyword(name);
    }

    @Override
    public POCharacteristicType findByName(String name) {
        try {
            Optional<POCharacteristicType> optionalPOCharacteristicType = POCharacteristicTypeRepository.findByName(name);
            return optionalPOCharacteristicType.orElseThrow(() -> new RuntimeException("POCharacteristicType with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding POCharacteristicType");
        }
    }


}


package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Repository.ProductOfferingRepo.POAttributesRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.POAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class POAttributesServiceImpl implements POAttributesService {

    @Autowired
    POAttributesRepository poAttributesRepository;

    @Override
    public POAttributes create(POAttributes POAttributes) {
        return poAttributesRepository.save(POAttributes);
    }

    @Override
    public List<POAttributes> read() {
        return poAttributesRepository.findAll();
    }

    @Override
    public POAttributes update(int poAttribute_code, POAttributes updatedPOAttributes) {
        Optional<POAttributes> existingProductOptional = poAttributesRepository.findById(poAttribute_code);

        if (existingProductOptional.isPresent()) {
            POAttributes existingProduct = existingProductOptional.get();
            existingProduct.setShortCode(updatedPOAttributes.getShortCode());
            existingProduct.setAttributeCategory(updatedPOAttributes.getAttributeCategory());
            existingProduct.setExternalId(updatedPOAttributes.getExternalId());
            existingProduct.setStartDate(updatedPOAttributes.getStartDate());
            existingProduct.setEndDate(updatedPOAttributes.getEndDate());
            existingProduct.setDescription(updatedPOAttributes.getDescription());
            existingProduct.setAttributeValue(updatedPOAttributes.getAttributeValue());
            existingProduct.setAttributeValDesc(updatedPOAttributes.getAttributeValDesc());
            existingProduct.setCharType(updatedPOAttributes.getCharType());
            existingProduct.setCharValue(updatedPOAttributes.getCharValue());

            return poAttributesRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find POAttributes with ID: " + poAttribute_code);
        }
    }

    @Override
    public String delete(int poAttribute_code) {
        poAttributesRepository.deleteById(poAttribute_code);
        return ("POAttributes was successfully deleted");
    }

    @Override
    public POAttributes findById(int poAttribute_code) {
        Optional<POAttributes> optionalPlan = poAttributesRepository.findById(poAttribute_code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("POAttributes with ID " + poAttribute_code + " not found"));
    }

    @Override
    public List<POAttributes> searchByKeyword(String description) {
        return poAttributesRepository.searchByKeyword(description);
    }

    @Override
    public POAttributes findByDescription(String description) {
        try {
            Optional<POAttributes> optionalPOAttributes = poAttributesRepository.findByDescription(description);
            return optionalPOAttributes.orElseThrow(() -> new RuntimeException("POAttributes with ID " + description + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding POAttributes");
        }
    }


}


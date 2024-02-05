package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.BundledProductOffer;
import com.Bcm.Repository.ProductOfferingRepo.BundledProductOfferRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BundledProductOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BundledProductOfferServiceImpl implements BundledProductOfferService {

    @Autowired
    BundledProductOfferRepository bundledProductOfferRepository;

    @Autowired
    BundledProductOfferService bundledProductOfferService;

    @Override
    public BundledProductOffer create(BundledProductOffer bundledProductOffer) {
        return bundledProductOfferRepository.save(bundledProductOffer);
    }

    @Override
    public List<BundledProductOffer> read() {
        return bundledProductOfferRepository.findAll();
    }

    @Override
    public BundledProductOffer update(int bdo_code, BundledProductOffer updatedBundledProductOffer) {
        Optional<BundledProductOffer> existingProductOptional = bundledProductOfferRepository.findById(bdo_code);

        if (existingProductOptional.isPresent()) {
            BundledProductOffer existingProduct = existingProductOptional.get();
            existingProduct.setStatus(updatedBundledProductOffer.getStatus());
            existingProduct.setValidFor(updatedBundledProductOffer.getValidFor());

            return bundledProductOfferRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find product offering with ID: " + bdo_code);
        }
    }
    @Override
    public String delete(int bdo_code) {
        bundledProductOfferRepository.deleteById(bdo_code);
        return ("plan was successfully deleted");
    }
    @Override
    public BundledProductOffer findById(int bdo_code) {
        Optional<BundledProductOffer> optionalPlan = bundledProductOfferRepository.findById(bdo_code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Plan with ID " + bdo_code + " not found"));
    }


}
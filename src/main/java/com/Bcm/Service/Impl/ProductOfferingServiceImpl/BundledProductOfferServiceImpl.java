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
            throw new RuntimeException("Could not find bundled product offer with ID: " + bdo_code);
        }
    }

    @Override
    public String delete(int bdo_code) {
        try {
            bundledProductOfferRepository.deleteById(bdo_code);
            return ("Bundled product offer was successfully deleted");
        } catch (RuntimeException e) {
            throw new RuntimeException("An unexpected error occurred while deleting bundled product offer with ID: " + bdo_code, e);
        }
    }

    @Override
    public BundledProductOffer findById(int bdo_code) {
        Optional<BundledProductOffer> optionalPlan = bundledProductOfferRepository.findById(bdo_code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Bundled product offer with ID " + bdo_code + " not found"));
    }
}

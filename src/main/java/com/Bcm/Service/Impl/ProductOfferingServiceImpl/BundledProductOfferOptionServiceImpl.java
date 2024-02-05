package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Model.ProductOfferingABE.BundledProductOfferOption;
import com.Bcm.Repository.ProductOfferingRepo.BundledProductOfferOptionRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.BundledProductOfferOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BundledProductOfferOptionServiceImpl implements BundledProductOfferOptionService {

    @Autowired
    BundledProductOfferOptionRepository bundledProductOfferOptionRepository;

    @Autowired
    BundledProductOfferOptionService bundledProductOfferOptionService;

    @Override
    public BundledProductOfferOption create(BundledProductOfferOption bundledProductOfferOption) {
        return bundledProductOfferOptionRepository.save(bundledProductOfferOption);
    }

    @Override
    public List<BundledProductOfferOption> read() {
        return bundledProductOfferOptionRepository.findAll();
    }

    @Override
    public BundledProductOfferOption update(int bdoo_code, BundledProductOfferOption updatedBundledProductOfferOption) {
        Optional<BundledProductOfferOption> existingProductOptional = bundledProductOfferOptionRepository.findById(bdoo_code);

        if (existingProductOptional.isPresent()) {
            BundledProductOfferOption existingProduct = existingProductOptional.get();
            existingProduct.setDefaultProductOfferNumber(updatedBundledProductOfferOption.getDefaultProductOfferNumber());
            existingProduct.setNumberRelationOfferLowerLimit(updatedBundledProductOfferOption.getNumberRelationOfferLowerLimit());
            existingProduct.setNumberRelationOfferUpperLimit(updatedBundledProductOfferOption.getNumberRelationOfferUpperLimit());


            return bundledProductOfferOptionRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Could not find product offering with ID: " + bdoo_code);
        }
    }
    @Override
    public String delete(int bdoo_code) {
        bundledProductOfferOptionRepository.deleteById(bdoo_code);
        return ("plan was successfully deleted");
    }
    @Override
    public BundledProductOfferOption findById(int bdoo_code) {
        Optional<BundledProductOfferOption> optionalPlan = bundledProductOfferOptionRepository.findById(bdoo_code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Plan with ID " + bdoo_code + " not found"));
    }


}
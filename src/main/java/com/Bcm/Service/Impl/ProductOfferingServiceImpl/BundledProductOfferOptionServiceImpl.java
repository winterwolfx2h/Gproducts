package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class BundledProductOfferOptionServiceImpl/* implements BundledProductOfferOptionService*/ {
/*
    @Autowired
    BundledProductOfferOptionRepository bundledProductOfferOptionRepository;

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
            throw new RuntimeException("Could not find bundled product offer option with ID: " + bdoo_code);
        }
    }

    @Override
    public String delete(int bdoo_code) {
        try {
            bundledProductOfferOptionRepository.deleteById(bdoo_code);
            return ("Bundled product offer option was successfully deleted");
        } catch (RuntimeException e) {
            throw new RuntimeException("An unexpected error occurred while deleting bundled product offer option with ID: " + bdoo_code, e);
        }
    }

    @Override
    public BundledProductOfferOption findById(int bdoo_code) {
        Optional<BundledProductOfferOption> optionalPlan = bundledProductOfferOptionRepository.findById(bdoo_code);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Bundled product offer option with ID " + bdoo_code + " not found"));
    }*/
}

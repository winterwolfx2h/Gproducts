package com.Bcm.Service.Impl.Product;

import com.Bcm.Model.Product.ProductType;
import com.Bcm.Model.ProductOfferingABE.Type;
import com.Bcm.Repository.Product.ProductTypeRepository;
import com.Bcm.Service.Srvc.ProductSrvc.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

    final ProductTypeRepository productTypeRepository;

    @Override
    public ProductType create(ProductType productType) {
        if (productTypeRepository.findByTypeName(productType.getTypeName()).isPresent()) {
            throw new RuntimeException("Type with name " + productType.getTypeName() + " already exists");
        }

        if (productType.getProductTypeCode() != 0 && !productTypeRepository.existsById(productType.getProductTypeCode())) {
            throw new RuntimeException("Type ID " + productType.getProductTypeCode() + " does not exist");
        }
        return productTypeRepository.save(productType);
    }

    @Override
    public List<ProductType> read() {
        return productTypeRepository.findAll();
    }

    @Override
    public ProductType update(int productTypeCode, ProductType updateproductType) {
        Optional<ProductType> existingTypeOptional = productTypeRepository.findById(productTypeCode);

        if (existingTypeOptional.isPresent()) {
            ProductType existingType = existingTypeOptional.get();
            existingType.setTypeName(updateproductType.getTypeName());
            existingType.setDescription(updateproductType.getDescription());

            return productTypeRepository.save(existingType);
        } else {
            throw new RuntimeException("Could not find Type with ID: " + productTypeCode);
        }
    }

    @Override
    public String delete(int productTypeCode) {
        productTypeRepository.deleteById(productTypeCode);
        return ("Type was successfully deleted");
    }

    @Override
    public ProductType findById(int productTypeCode) {
        Optional<ProductType> optionalPlan = productTypeRepository.findById(productTypeCode);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Type with ID " + productTypeCode + " not found"));
    }

    @Override
    public List<ProductType> searchByKeyword(String typeName) {
        return productTypeRepository.searchByKeyword(typeName);
    }

    @Override
    public ProductType findByTypeName(String typeName) {
        try {
            Optional<ProductType> optionalType = productTypeRepository.findByTypeName(typeName);
            return optionalType.orElseThrow(() -> new RuntimeException("Type with " + typeName + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Type");
        }
    }

    @Override
    public boolean existsById(int productTypeCode) {
        return productTypeRepository.existsById(productTypeCode);
    }


}

package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.*;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Repository.ProductOfferingRepo.ProductOfferingRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.ProductOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductOfferingServiceImpl implements ProductOfferingService {

    final ProductOfferingRepository productOfferingRepository;

    @Override
    public ProductOffering create(ProductOffering productOffering) {
        Optional<ProductOffering> existingProduct = productOfferingRepository.findByname(productOffering.getName());

        if (productOffering.getChannels() == null || productOffering.getChannels().isEmpty()) {
            throw new InvalidInputException("Channels list cannot be empty.");
        }

        if (productOffering.getPoParent_Child() == null || productOffering.getPoParent_Child().isEmpty()) {
            productOffering.setPoParent_Child("PO-Parent");
        }

        try {
            productOffering.setPoParent_Child(productOffering.getPoParent_Child());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid value for poParent_Child: " + e.getMessage());
        }

        if (existingProduct.isPresent()) {
            throw new ProductOfferingAlreadyExistsException("A product offering with the same name already exists.");
        }

        productOffering.setStatus("Working state");

        try {
            return productOfferingRepository.save(productOffering);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating product offering", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating product offering", e);
        }
    }

    @Override
    public List<ProductOffering> read() {
        try {
            return productOfferingRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading product offerings", e);
        }
    }

    @Override
    public ProductOffering update(int po_code, ProductOffering updatedProductOffering) {
        Optional<ProductOffering> existingProductOptional = productOfferingRepository.findById(po_code);

        if (existingProductOptional.isPresent()) {
            ProductOffering existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProductOffering.getName());
            existingProduct.setParent(updatedProductOffering.getParent());
            //existingProduct.setExternalLinkId(updatedProductOffering.getExternalLinkId());
            // existingProduct.setProductSpecification(updatedProductOffering.getProductSpecification());
            existingProduct.setPoAttributes(updatedProductOffering.getPoAttributes());
            existingProduct.setProductRelation(updatedProductOffering.getProductRelation());
            //existingProduct.setProductOfferRelation(updatedProductOffering.getProductOfferRelation());
            //existingProduct.setLogicalResource(updatedProductOffering.getLogicalResource());
            existingProduct.setPhysicalResource(updatedProductOffering.getPhysicalResource());
            existingProduct.setBusinessProcess(updatedProductOffering.getBusinessProcess());
            //existingProduct.setEligibilityChannels(updatedProductOffering.getEligibilityChannels());
            existingProduct.setPoParent_Child(updatedProductOffering.getPoParent_Child());
            existingProduct.setChannels(updatedProductOffering.getChannels());

            //existingProduct.getEligibilityChannels();

            try {
                if (updatedProductOffering.getName() == null || updatedProductOffering.getDescription() == null) {
                    throw new InputException("Name and description cannot be null");
                }

                return productOfferingRepository.save(existingProduct);
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseOperationException("Error updating product offering with ID: " + po_code, e);
            } catch (ObjectOptimisticLockingFailureException e) {
                throw new ConcurrentModificationException("Another user has modified the product offering with ID: " + po_code + ". Please try again.");
            } catch (InputException e) {
                throw e;
            } catch (InvalidInput e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("An unexpected error occurred while updating product offering with ID: " + po_code, e);
            }
        } else {
            throw new ResourceNotFoundException("Could not find product offering with ID: " + po_code);
        }
    }

    @Override
    public String delete(int po_code) {
        if (!productOfferingRepository.existsById(po_code)) {
            throw new ResourceNotFoundException("Product offering with ID " + po_code + " not found");
        }

        try {
            productOfferingRepository.deleteById(po_code);
            return "Product offering with ID " + po_code + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting product offering with ID: " + po_code, e);
        }
    }

    @Override
    public ProductOffering findById(int po_code) {
        try {
            return productOfferingRepository.findById(po_code)
                    .orElseThrow(() -> new ResourceNotFoundException(" An unexpected error occurred while finding product offering with ID:" + po_code));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product offering with ID \"" + po_code + "\" not found", e);
        }
    }

    @Override
    public List<ProductOffering> searchByKeyword(String name) {
        try {
            return productOfferingRepository.searchByKeyword(name);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while searching for product offerings by keyword: " + name, e);
        }
    }

    @Override
    public ProductOffering findByName(String name) {
        try {
            Optional<ProductOffering> optionalProductOffering = productOfferingRepository.findByname(name);
            return optionalProductOffering.orElseThrow(() -> new RuntimeException("Product Offering with ID " + name + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Product Offering");
        }
    }

    @Override
    public List<ProductOffering> findByParentName(String parentName) {
        try {
            return productOfferingRepository.findByParent(parentName);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while searching for product offerings by parent name: " + parentName, e);
        }
    }

    @Override
    public List<ProductOffering> findByPoType(String poType) {
        try {
            return productOfferingRepository.findByPoType(poType);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while searching for product offerings by poType: " + poType, e);
        }
    }

    @Override
    public boolean existsById(int po_code) {
        return productOfferingRepository.existsById(po_code);
    }

    @Override
    public List<ProductOffering> findByFamilyName(String familyName) {
        try {
            return productOfferingRepository.findByFamilyName(familyName);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while searching for product offerings by family name: " + familyName, e);
        }
    }


    @Override
    public ProductOffering changeProductOfferingStatus(int po_code) {
        try {
            ProductOffering existingProduct = findById(po_code);

            switch (existingProduct.getStatus()) {
                case "Working state":
                    existingProduct.setStatus("Validated");
                    break;

                case "Validated":
                    existingProduct.setStatus("Suspended");
                    break;

                case "Suspended":
                    throw new ProductOfferingLogicException("Product " + existingProduct.getName() + " isn't fit to be offered for sale anymore.");

                default:
                    throw new InvalidInputException("Invalid status transition.");
            }

            return productOfferingRepository.save(existingProduct);

        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Product Offering with ID \"" + po_code + "\" not found", e);
        } catch (ProductOfferingLogicException e) {
            throw new ProductOfferingLogicException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while changing Product status", e);
        }
    }

    @Override
    public List<ProductOffering> changeMultipleProductStatuses(List<Integer> poCodes) {
        List<ProductOffering> updatedProducts = new ArrayList<>();

        for (int po_code : poCodes) {
            ProductOffering existingProduct = findById(po_code);

            switch (existingProduct.getStatus()) {
                case "Working state":
                    existingProduct.setStatus("Validated");
                    break;
                case "Validated":
                    existingProduct.setStatus("Suspended");
                    break;
                case "Suspended":
                    throw new ProductOfferingLogicException("Product " + existingProduct.getName() + " isn't fit to be offered for sale anymore.");
                default:
                    throw new InvalidInputException("Invalid status transition.");
            }

            productOfferingRepository.save(existingProduct);
            updatedProducts.add(existingProduct);
        }

        return updatedProducts;
    }

    @Override
    public boolean existsByName(String name) {
        return productOfferingRepository.findByName(name).isPresent();
    }

}

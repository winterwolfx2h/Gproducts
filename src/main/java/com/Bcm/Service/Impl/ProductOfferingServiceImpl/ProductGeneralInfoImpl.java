package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductPrice;
import com.Bcm.Model.ProductOfferingABE.ProductPriceGroup;
import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Model.ProductOfferingABE.SubClasses.EligibilityEntity;
import com.Bcm.Model.ProductResourceABE.PhysicalResource;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Repository.ProductOfferingRepo.*;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ChannelRepository;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.EntityRepository;
import com.Bcm.Repository.ProductResourceRepository.PhysicalResourceRepository;
import com.Bcm.Repository.ServiceConfigRepo.CustomerFacingServiceSpecRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.GeneralInfoService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductGeneralInfoImpl implements GeneralInfoService {
  final GeneralInfoRepository generalInfoRepository;
  final ProductOfferingRepository productOfferingRepository;
  final PhysicalResourceRepository physicalResourceRepository;
  final CustomerFacingServiceSpecRepository customerFacingServiceSpecRepository;
  final EntityRepository entityRepository;
  final ChannelRepository channelRepository;
  final ProductPriceGroupRepository productPriceGroupRepository;
  private final BusinessProcessRepository businessProcessRepository;
  private final ProductPriceRepository productPriceRepository;

  @Override
  public ProductOffering createGeneralInfoDTO(GeneralInfoDTO generalInfoDTO) {
    // Check if an existing product offering with the same name already exists
    generalInfoRepository
        .findByName(generalInfoDTO.getName())
        .ifPresent(
            existingProduct -> {
              throw new ProductOfferingAlreadyExistsException(
                  "A product offering with the name '" + generalInfoDTO.getName() + "' already exists.");
            });

    generalInfoDTO.setStatus("Working state");

    // Create new ProductOffering entity from DTO
    ProductOffering productOffering = new ProductOffering();
    productOffering.setName(generalInfoDTO.getName());
    productOffering.setPoType(generalInfoDTO.getPoType());
    productOffering.setEffectiveFrom(generalInfoDTO.getEffectiveFrom());
    productOffering.setEffectiveTo(generalInfoDTO.getEffectiveTo());
    productOffering.setDescription(generalInfoDTO.getDescription());
    productOffering.setDetailedDescription(generalInfoDTO.getDetailedDescription());
    productOffering.setParent(generalInfoDTO.getParent());
    productOffering.setWorkingStep("GeneralInfo");
    productOffering.setCategory(generalInfoDTO.getCategory());
    productOffering.setBS_externalId(generalInfoDTO.getBS_externalId());
    productOffering.setCS_externalId(generalInfoDTO.getCS_externalId());
    productOffering.setPoParent_Child(generalInfoDTO.getPoParent_Child());
    productOffering.setSellIndicator(generalInfoDTO.getSellIndicator());
    productOffering.setQuantityIndicator(generalInfoDTO.getQuantityIndicator());
    productOffering.setStatus("Working state");

    // Save the new ProductOffering
    return productOfferingRepository.save(productOffering);
  }

  @Override
  public ProductOffering getProductOfferingById(int Product_id) throws ProductOfferingNotFoundException {
    return productOfferingRepository
        .findById(Product_id)
        .orElseThrow(() -> new ProductOfferingNotFoundException("Product Offering not found with id: " + Product_id));
  }

  private void convertToEntity(GeneralInfoDTO generalInfoDTO, ProductOffering productOffering) {
    productOffering.setName(generalInfoDTO.getName());
    productOffering.setPoType(generalInfoDTO.getPoType());
    productOffering.setEffectiveFrom(generalInfoDTO.getEffectiveFrom());
    productOffering.setEffectiveTo(generalInfoDTO.getEffectiveTo());
    productOffering.setDescription(generalInfoDTO.getDescription());
    productOffering.setDetailedDescription(generalInfoDTO.getDetailedDescription());
    productOffering.setParent(generalInfoDTO.getParent());
    productOffering.setWorkingStep(generalInfoDTO.getWorkingStep());
    productOffering.setCategory(generalInfoDTO.getCategory());
    productOffering.setBS_externalId(generalInfoDTO.getBS_externalId());
    productOffering.setCS_externalId(generalInfoDTO.getCS_externalId());
    productOffering.setPoParent_Child(generalInfoDTO.getPoParent_Child());
    productOffering.setSellIndicator(generalInfoDTO.getSellIndicator());
    productOffering.setQuantityIndicator(generalInfoDTO.getQuantityIndicator());
    productOffering.setStatus(generalInfoDTO.getStatus());
    productOffering.setEligibility_id(productOffering.getEligibility_id());
  }

  private GeneralInfoDTO convertToDTO(ProductOffering productOffering) {
    GeneralInfoDTO generalInfoDTO = new GeneralInfoDTO();
    generalInfoDTO.setProduct_id(productOffering.getProduct_id());
    generalInfoDTO.setName(productOffering.getName());
    generalInfoDTO.setPoType(productOffering.getPoType());
    generalInfoDTO.setEffectiveFrom(productOffering.getEffectiveFrom());
    generalInfoDTO.setEffectiveTo(productOffering.getEffectiveTo());
    generalInfoDTO.setDescription(productOffering.getDescription());
    generalInfoDTO.setDetailedDescription(productOffering.getDetailedDescription());
    generalInfoDTO.setParent(productOffering.getParent());
    generalInfoDTO.setWorkingStep(productOffering.getWorkingStep());
    generalInfoDTO.setCategory(productOffering.getCategory());
    generalInfoDTO.setBS_externalId(productOffering.getBS_externalId());
    generalInfoDTO.setCS_externalId(productOffering.getCS_externalId());
    generalInfoDTO.setPoParent_Child(productOffering.getPoParent_Child());
    generalInfoDTO.setSellIndicator(productOffering.getSellIndicator());
    generalInfoDTO.setQuantityIndicator(productOffering.getQuantityIndicator());
    generalInfoDTO.setStatus(productOffering.getStatus());
    productOffering.setEligibility_id(productOffering.getEligibility_id());
    return generalInfoDTO;
  }

  @Override
  public ProductOffering updateProductOffering(
      GeneralInfoDTO generalInfoDTO,
      int product_id,
      int businessProcess_id,
      int pr_id,
      int serviceId,
      int productPriceCode,
      int productPriceGroupCode)
      throws ProductOfferingNotFoundException {
    // Fetch the ProductOffering entity by ID
    ProductOffering productOffering = getProductOfferingById(product_id);
    if (productOffering == null) {
      throw new ProductOfferingNotFoundException("Product Offering not found");
    }

    // Fetch the ProductPriceGroup entity by code
    ProductPriceGroup productPriceGroup =
        productPriceGroupRepository
            .findById(productPriceGroupCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Product Price Group not found"));

    // Fetch the ProductPrice entity by code
    ProductPrice productPrice =
        productPriceRepository
            .findById(productPriceCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Product Price not found"));

    // Fetch the BusinessProcess entity by code
    BusinessProcess businessProcess =
        businessProcessRepository
            .findById(businessProcess_id)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Business Process not found"));

    // Fetch the CFS entity by code
    CustomerFacingServiceSpec cfs =
        customerFacingServiceSpecRepository
            .findById(serviceId)
            .orElseThrow(() -> new ProductOfferingNotFoundException("CFS not found"));

    // Fetch the Physical Resource entity by code
    PhysicalResource physicalResource =
        physicalResourceRepository
            .findById(pr_id)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Physical Resource not found"));

    // Update the ProductOffering fields
    convertToEntity(generalInfoDTO, productOffering);

    // Update the association with ProductPriceGroup
    productOffering.getProductPriceGroups().clear(); // Clear existing associations
    productOffering.getProductPriceGroups().add(productPriceGroup); // Add the new association

    // Update the association with ProductPriceGroup
    productOffering.getProductPriceCode().clear(); // Clear existing associations
    productOffering.getProductPriceCode().add(productPrice); // Add the new association

    // Update the association with BusinessProcess
    productOffering.getBusinessProcess_id().clear(); // Clear existing associations
    productOffering.getBusinessProcess_id().add(businessProcess); // Add the new association

    // Update the association with CFS
    productOffering.getServiceId().clear(); // Clear existing associations
    productOffering.getServiceId().add(cfs); // Add the new association

    // Update the association with PhysicalResources
    productOffering.getPR_id().clear(); // Clear existing associations
    productOffering.getPR_id().add(physicalResource); // Add the new association

    // Save the ProductOffering entity to persist changes
    return productOfferingRepository.save(productOffering);
  }

  @Override
  public ProductOffering updateProductOfferingEligibility(
      GeneralInfoDTO generalInfoDTO, int Product_id, int channelCode, int entityCode, int eligibility_id)
      throws ProductOfferingNotFoundException {
    // Fetch the ProductOffering entity by ID
    ProductOffering productOffering = getProductOfferingById(Product_id);
    if (productOffering == null) {
      throw new ProductOfferingNotFoundException("Product Offering not found");
    }

    // Fetch the Channel entity by code
    Channel channel =
        channelRepository
            .findById(channelCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Channel not found"));

    // Fetch the Channel entity by code
    EligibilityEntity eligibilityEntity =
        entityRepository
            .findById(entityCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Entity not found"));

    convertToEntity(generalInfoDTO, productOffering);
    productOffering.setEligibility_id(productOffering.getEligibility_id());

    // Update the association with ProductPriceGroup
    productOffering.getChannelCode().clear(); // Clear existing associations
    productOffering.getChannelCode().add(channel); // Add the new association

    // Update the association with ProductPriceGroup
    productOffering.getEntityCode().clear(); // Clear existing associations
    productOffering.getEntityCode().add(eligibilityEntity); // Add the new association

    return productOfferingRepository.save(productOffering);
  }

  @Override
  public List<GeneralInfoDTO> getAllGeneralInfoDTOs() {
    List<ProductOffering> productOfferings = generalInfoRepository.findByNameNotLike("PO-PLAN%");
    List<GeneralInfoDTO> dtos = new ArrayList<>();

    for (ProductOffering productOffering : productOfferings) {
      GeneralInfoDTO dto = convertToDTO(productOffering);
      dtos.add(dto);
    }
    return dtos;
  }
}

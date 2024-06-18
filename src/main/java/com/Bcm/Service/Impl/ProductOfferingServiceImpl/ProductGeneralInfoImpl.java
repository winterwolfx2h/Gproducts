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
    generalInfoDTO.setWorkingStep("General Info");

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
    productOffering.setWorkingStep("GeneralInfo");

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
    productOffering.setWorkingStep(generalInfoDTO.getWorkingStep());
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
    generalInfoDTO.setWorkingStep(productOffering.getWorkingStep());
    productOffering.setEligibility_id(productOffering.getEligibility_id());
    return generalInfoDTO;
  }

  @Override
  public ProductOffering updatePOPrice(GeneralInfoDTO generalInfoDTO, int product_id, int productPriceCode)
      throws ProductOfferingNotFoundException {

    ProductOffering productOffering = getProductOfferingById(product_id);
    if (productOffering == null) {
      throw new ProductOfferingNotFoundException("Product Offering not found");
    }

    ProductPrice productPrice =
        productPriceRepository
            .findById(productPriceCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Product Price not found"));

    convertToEntity(generalInfoDTO, productOffering);

    //    productOffering.getProductPriceCode().clear();
    //    productOffering.getProductPriceCode().add(productPrice);

    return productOfferingRepository.save(productOffering);
  }

  @Override
  public ProductOffering updatePOBusinessProc(GeneralInfoDTO generalInfoDTO, int product_id, int businessProcess_id)
      throws ProductOfferingNotFoundException {

    ProductOffering productOffering = getProductOfferingById(product_id);
    if (productOffering == null) {
      throw new ProductOfferingNotFoundException("Product Offering not found");
    }

    BusinessProcess businessProcess =
        businessProcessRepository
            .findById(businessProcess_id)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Business Process not found"));

    convertToEntity(generalInfoDTO, productOffering);

    productOffering.setBusinessProcess_id(businessProcess_id);

    // Update the product's working step
    productOffering.setWorkingStep("Business Process");
    return productOfferingRepository.save(productOffering);
  }

  @Override
  public ProductOffering updatePOSrvcPr(GeneralInfoDTO generalInfoDTO, int product_id, int pr_id, int serviceId)
      throws ProductOfferingNotFoundException {
    ProductOffering productOffering = getProductOfferingById(product_id);
    if (productOffering == null) {
      throw new ProductOfferingNotFoundException("Product Offering not found");
    }

    PhysicalResource physicalResource =
        physicalResourceRepository
            .findById(pr_id)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Physical Resource not found"));

    CustomerFacingServiceSpec cfs =
        customerFacingServiceSpecRepository
            .findById(serviceId)
            .orElseThrow(() -> new ProductOfferingNotFoundException("CFS not found"));

    convertToEntity(generalInfoDTO, productOffering);

    productOffering.setPr_id(pr_id);
    productOffering.setServiceId(serviceId);

    // Update the product's working step
    productOffering.setWorkingStep("Product Resource");
    return productOfferingRepository.save(productOffering);
  }

  @Override
  public ProductOffering updatePOEligibility(
      GeneralInfoDTO generalInfoDTO,
      int Product_id,
      int channelCode,
      int entityCode,
      int eligibility_id,
      int productPriceCode)
      throws ProductOfferingNotFoundException {
    ProductOffering productOffering = getProductOfferingById(Product_id);
    if (productOffering == null) {
      throw new ProductOfferingNotFoundException("Product Offering not found.");
    }

    Channel channel =
        channelRepository
            .findById(channelCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Channel not found"));

    EligibilityEntity eligibilityEntity =
        entityRepository
            .findById(entityCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Entity not found"));

    ProductPriceGroup productPriceGroup =
        productPriceGroupRepository
            .findById(productPriceCode)
            .orElseThrow(() -> new ProductOfferingNotFoundException("Product Price Group not found"));

    convertToEntity(generalInfoDTO, productOffering);
    // productOffering.setEligibility_id(productOffering.getEligibility_id());
    productOffering.setEligibility_id(eligibility_id);

    productOffering.getChannelCode().clear();
    productOffering.getChannelCode().add(channel);

    productOffering.getEntityCode().clear();
    productOffering.getEntityCode().add(eligibilityEntity);

    productOffering.getProductPriceGroups().clear();
    productOffering.getProductPriceGroups().add(productPriceGroup);

    // Update the product's working step
    productOffering.setWorkingStep("Eligibility");
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

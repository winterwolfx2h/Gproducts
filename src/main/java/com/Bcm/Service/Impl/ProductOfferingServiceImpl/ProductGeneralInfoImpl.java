package com.Bcm.Service.Impl.ProductOfferingServiceImpl;

import com.Bcm.Exception.ProductOfferingAlreadyExistsException;
import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.GeneralInfoDTO;
import com.Bcm.Model.Product.GeneralInfoMapper;
import com.Bcm.Model.ProductOfferingABE.BusinessProcess;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import com.Bcm.Model.ProductOfferingABE.ProductPrice;
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
    productOffering.setSellInd(generalInfoDTO.getSellInd());
    productOffering.setQuantityInd(generalInfoDTO.getQuantityInd());
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

  @Override
  public GeneralInfoDTO getDTOById(int Product_id) throws ProductOfferingNotFoundException {
    ProductOffering productOffering =
        generalInfoRepository
            .findById(Product_id)
            .orElseThrow(() -> new ProductOfferingNotFoundException("GeneralInfoDTO not found with id: " + Product_id));

    return GeneralInfoMapper.toDTO(productOffering);
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
    productOffering.setSellInd(generalInfoDTO.getSellInd());
    productOffering.setQuantityInd(generalInfoDTO.getQuantityInd());
    // productOffering.setStatus(generalInfoDTO.getStatus());
    productOffering.setWorkingStep(generalInfoDTO.getWorkingStep());
    productOffering.setStockInd(productOffering.getStockInd());
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
    generalInfoDTO.setSellInd(productOffering.getSellInd());
    generalInfoDTO.setQuantityInd(productOffering.getQuantityInd());
    generalInfoDTO.setStatus(productOffering.getStatus());
    generalInfoDTO.setWorkingStep(productOffering.getWorkingStep());
    productOffering.setStockInd(productOffering.getStockInd());
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

    // Ensure the status is not modified
    productOffering.setStatus(productOffering.getStatus());

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

    // Ensure the status is not modified
    productOffering.setStatus(productOffering.getStatus());

    // Update the product's working step
    productOffering.setWorkingStep("Business Process");
    return productOfferingRepository.save(productOffering);
  }

  @Override
  public ProductOffering updatePOSrvcPr(GeneralInfoDTO generalInfoDTO, int product_id, Integer pr_id, Integer serviceId)
      throws ProductOfferingNotFoundException {
    ProductOffering productOffering = getProductOfferingById(product_id);
    if (productOffering == null) {
      throw new ProductOfferingNotFoundException("Product Offering not found");
    }

    if (pr_id != null) {
      PhysicalResource physicalResource =
          physicalResourceRepository
              .findById(pr_id)
              .orElseThrow(() -> new ProductOfferingNotFoundException("Physical Resource not found"));
      productOffering.setPr_id(pr_id);
    }

    if (serviceId != null) {
      CustomerFacingServiceSpec cfs =
          customerFacingServiceSpecRepository
              .findById(serviceId)
              .orElseThrow(() -> new ProductOfferingNotFoundException("CFS not found"));
      productOffering.getServiceId().add(cfs);
    }
    //  if (serviceId != null) {
    //    CustomerFacingServiceSpec cfs =
    //          customerFacingServiceSpecRepository
    //                   .findById(serviceId)
    //                    .orElseThrow(() -> new ProductOfferingNotFoundException("CFS not found"));
    //     productOffering.setServiceId(serviceId);
    // }

    convertToEntity(generalInfoDTO, productOffering);

    // Ensure the status is not modified
    productOffering.setStatus(productOffering.getStatus());

    // Update the product's working step
    productOffering.setWorkingStep("Product Resource");
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

  @Override
  public ProductOffering updatePOStockInd(GeneralInfoDTO generalInfoDTO, int productId, boolean stockInd)
      throws ProductOfferingNotFoundException {
    ProductOffering productOffering = getProductOfferingById(productId);

    // Update the stockInd field of the GeneralInfoDTO
    generalInfoDTO.setStockInd(stockInd);

    // Update the product offering with the new general info
    productOffering.setStockInd(stockInd);

    // Save the updated product offering
    return productOfferingRepository.save(productOffering);
  }

  @Override
  public ProductOffering updateGeneralInfoDTO(GeneralInfoDTO generalInfoDTO, int productId)
      throws ProductOfferingNotFoundException {
    ProductOffering productOffering = getProductOfferingById(productId);
    if (productOffering == null) {
      throw new ProductOfferingNotFoundException("Product Offering not found");
    }

    productOffering.setName(generalInfoDTO.getName());
    productOffering.setPoType(generalInfoDTO.getPoType());
    productOffering.setEffectiveFrom(generalInfoDTO.getEffectiveFrom());
    productOffering.setEffectiveTo(generalInfoDTO.getEffectiveTo());
    productOffering.setDescription(generalInfoDTO.getDescription());
    productOffering.setDetailedDescription(generalInfoDTO.getDetailedDescription());
    productOffering.setParent(generalInfoDTO.getParent());
    productOffering.setCategory(generalInfoDTO.getCategory());
    productOffering.setBS_externalId(generalInfoDTO.getBS_externalId());
    productOffering.setCS_externalId(generalInfoDTO.getCS_externalId());
    productOffering.setSellInd(generalInfoDTO.getSellInd());
    productOffering.setQuantityInd(generalInfoDTO.getQuantityInd());
    productOffering.setPoParent_Child(generalInfoDTO.getPoParent_Child());
    productOffering.setPoParent_Child(generalInfoDTO.getPoParent_Child());
    productOffering.setPoParent_Child(generalInfoDTO.getPoParent_Child());

    // Save the updated ProductOffering
    return productOfferingRepository.save(productOffering);
  }
}

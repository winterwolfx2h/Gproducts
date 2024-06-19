package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Exception.ProductOfferingNotFoundException;
import com.Bcm.Model.Product.ProductOfferingDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductOfferingService {

  ProductOffering create(ProductOffering productOffering);

  ProductOffering createProductOfferingDTO(ProductOfferingDTO dto);

  List<ProductOffering> read();

  ProductOffering update(int po_code, ProductOffering productOffering);

  String delete(int po_code);

  ProductOffering findById(int po_code);

  List<ProductOffering> searchByKeyword(String name);

  ProductOffering findByName(String name);

  boolean existsById(int po_code);

  List<ProductOffering> findByPoType(String poType);

  List<ProductOffering> findByFamilyName(String familyName);

  ProductOffering changeProductOfferingStatus(int po_code);

  boolean existsByName(String name);

  List<ProductOffering> changeMultipleProductStatuses(List<Integer> poCodes);

  List<ProductOfferingDTO> getAllProductOfferingDTOs();

  ProductOfferingDTO updateProductOfferingDTO(int po_code, ProductOfferingDTO updatedDTO);

  ProductOffering updatePODTORelations(
      ProductOfferingDTO productOfferingDTO, int Product_id, int channelCode, int entityCode, int productPriceGroupCode)
      throws ProductOfferingNotFoundException;
}

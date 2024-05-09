package com.Bcm.Service.Srvc.ProductOfferingSrvc;

import com.Bcm.Model.Product.ProductOfferingDTO;
import com.Bcm.Model.ProductOfferingABE.ProductOffering;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProductOfferingService {

    ProductOffering create(ProductOffering productOffering);

    List<ProductOffering> read();

    ProductOffering update(int po_code, ProductOffering productOffering);

    String delete(int po_code);

    ProductOffering findById(int po_code);

    List<ProductOffering> searchByKeyword(String name);

    ProductOffering findByName(String name);

    List<ProductOffering> findByParentName(String parentName);

    boolean existsById(int po_code);

    List<ProductOffering> findByPoType(String poType);

    List<ProductOffering> findByFamilyName(String familyName);

    ProductOffering changeProductOfferingStatus(int po_code);

    boolean existsByName(String name);

    List<ProductOffering> changeMultipleProductStatuses(List<Integer> poCodes);

    ProductOffering createProductOfferingDTO(ProductOfferingDTO dto);

}





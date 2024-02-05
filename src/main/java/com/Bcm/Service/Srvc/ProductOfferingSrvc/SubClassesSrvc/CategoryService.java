package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    Category create(Category category);

    List<Category> read();

    Category update(int po_CategoryCode, Category category);

    String delete(int po_CategoryCode);

    Category findById(int po_CategoryCode);

    List<Category> searchByKeyword(String name);
}

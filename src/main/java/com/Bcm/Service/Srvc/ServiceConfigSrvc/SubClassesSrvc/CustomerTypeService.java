package com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc;

import com.Bcm.Model.ServiceABE.SubClasses.CustomerType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerTypeService {

    CustomerType create(CustomerType customerType);

    List<CustomerType> read();

    CustomerType update(int CT_code, CustomerType customerType);

    String delete(int CT_code);

    CustomerType findById(int CT_code);

    List<CustomerType> searchByKeyword(String name);
}

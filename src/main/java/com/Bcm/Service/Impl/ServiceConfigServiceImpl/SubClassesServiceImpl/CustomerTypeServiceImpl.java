package com.Bcm.Service.Impl.ServiceConfigServiceImpl.SubClassesServiceImpl;


import com.Bcm.Model.ServiceABE.SubClasses.CustomerType;
import com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo.CustomerTypeRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerTypeServiceImpl implements CustomerTypeService {


    @Autowired
    CustomerTypeRepository customerTypeRepository;

    @Autowired
    CustomerTypeService customerTypeService;

    @Override
    public CustomerType create(CustomerType CustomerType) {
        return customerTypeRepository.save(CustomerType);
    }

    @Override
    public List<CustomerType> read() {
        return customerTypeRepository.findAll();
    }


    @Override
    public CustomerType update(int CT_code, CustomerType updatedCustomerType) {
        Optional<CustomerType> existingCustomerTypeOptional = customerTypeRepository.findById(CT_code);

        if (existingCustomerTypeOptional.isPresent()) {
            CustomerType existingCustomerType = existingCustomerTypeOptional.get();
            existingCustomerType.setName(updatedCustomerType.getName());
            return customerTypeRepository.save(existingCustomerType);
        } else {
            throw new RuntimeException("Could not find Group Dimension with ID: " + CT_code);
        }
    }


    @Override
    public String delete(int CT_code) {
        customerTypeRepository.deleteById(CT_code);
        return ("Group Dimension was successfully deleted");
    }

    @Override
    public CustomerType findById(int CT_code) {
        Optional<CustomerType> optionalCustomerType = customerTypeRepository.findById(CT_code);
        return optionalCustomerType.orElseThrow(() -> new RuntimeException("CustomerType with ID " + CT_code + " not found"));
    }


    @Override
    public List<CustomerType> searchByKeyword(String name) {
        return customerTypeRepository.searchByKeyword(name);
    }


}

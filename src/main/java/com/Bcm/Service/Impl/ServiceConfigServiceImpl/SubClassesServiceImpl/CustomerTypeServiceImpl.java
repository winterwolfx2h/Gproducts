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
    public CustomerType create(CustomerType customerType) {
        try {
            return customerTypeRepository.save(customerType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for creating CustomerType");
        }
    }

    @Override
    public List<CustomerType> read() {
        try {
            return customerTypeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving CustomerTypes");
        }
    }

    @Override
    public CustomerType update(int CT_code, CustomerType updatedCustomerType) {
        try {
            Optional<CustomerType> existingCustomerTypeOptional = customerTypeRepository.findById(CT_code);
            if (existingCustomerTypeOptional.isPresent()) {
                CustomerType existingCustomerType = existingCustomerTypeOptional.get();
                existingCustomerType.setName(updatedCustomerType.getName());
                return customerTypeRepository.save(existingCustomerType);
            } else {
                throw new RuntimeException("CustomerType with ID " + CT_code + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for updating CustomerType");
        }
    }

    @Override
    public String delete(int CT_code) {
        try {
            customerTypeRepository.deleteById(CT_code);
            return "CustomerType with ID " + CT_code + " was successfully deleted";
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for deleting CustomerType");
        }
    }

    @Override
    public CustomerType findById(int CT_code) {
        try {
            Optional<CustomerType> optionalCustomerType = customerTypeRepository.findById(CT_code);
            return optionalCustomerType.orElseThrow(() -> new RuntimeException("CustomerType with ID " + CT_code + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding CustomerType");
        }
    }

    @Override
    public List<CustomerType> searchByKeyword(String name) {
        try {
            return customerTypeRepository.searchByKeyword(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for searching CustomerType by keyword");
        }
    }
}

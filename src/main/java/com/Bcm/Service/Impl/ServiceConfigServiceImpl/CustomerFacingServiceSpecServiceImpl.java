package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Exception.*;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Repository.ServiceConfigRepo.CustomerFacingServiceSpecRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerFacingServiceSpecServiceImpl implements CustomerFacingServiceSpecService {

    final CustomerFacingServiceSpecRepository customerFacingServiceSpecRepository;

    @Override
    public CustomerFacingServiceSpec create(CustomerFacingServiceSpec customerFacingServiceSpec) {
        validateNotNullFields(customerFacingServiceSpec);

        Optional<CustomerFacingServiceSpec> existingService = customerFacingServiceSpecRepository.findByServiceSpecType(customerFacingServiceSpec.getServiceSpecType());
        if (existingService.isPresent()) {
            throw new ServiceAlreadyExistsException("Service with the same name already exists");
        }

        try {
            customerFacingServiceSpec.setStatus("Working state");
            return customerFacingServiceSpecRepository.save(customerFacingServiceSpec);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseOperationException("Error creating CustomerFacingServiceSpec", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating CustomerFacingServiceSpec", e);
        }
    }

    @Override
    public List<CustomerFacingServiceSpec> read() {
        try {
            return customerFacingServiceSpecRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while reading CustomerFacingServiceSpecs", e);
        }
    }

    @Override
    public CustomerFacingServiceSpec update(int CFSS_code, CustomerFacingServiceSpec updatedCustomerFacingServiceSpec) {
        Optional<CustomerFacingServiceSpec> existingCustomerFacingServiceSpecOptional = customerFacingServiceSpecRepository.findById(CFSS_code);

        if (existingCustomerFacingServiceSpecOptional.isPresent()) {
            CustomerFacingServiceSpec existingCustomerFacingServiceSpec = existingCustomerFacingServiceSpecOptional.get();
            if (!existingCustomerFacingServiceSpec.getServiceSpecType().equals(updatedCustomerFacingServiceSpec.getServiceSpecType())) {
                if (customerFacingServiceSpecRepository.findByServiceSpecType(updatedCustomerFacingServiceSpec.getServiceSpecType()).isPresent()) {
                    throw new ServiceAlreadyExistsException("Service with the same name already exists");
                }
            }
            existingCustomerFacingServiceSpec.setExternalId(updatedCustomerFacingServiceSpec.getExternalId());
            existingCustomerFacingServiceSpec.setNumPlanCode(updatedCustomerFacingServiceSpec.getNumPlanCode());
            existingCustomerFacingServiceSpec.setServiceSpecType(updatedCustomerFacingServiceSpec.getServiceSpecType());
            existingCustomerFacingServiceSpec.setStatus(updatedCustomerFacingServiceSpec.getStatus());
            existingCustomerFacingServiceSpec.setDescription(updatedCustomerFacingServiceSpec.getDescription());
            return customerFacingServiceSpecRepository.save(existingCustomerFacingServiceSpec);
        } else {
            throw new ResourceNotFoundException("Could not find CustomerFacingServiceSpec with ID: " + CFSS_code);
        }
    }


    @Override
    public String delete(int CFSS_code) {
        if (!customerFacingServiceSpecRepository.existsById(CFSS_code)) {
            throw new ResourceNotFoundException("CustomerFacingServiceSpec with ID " + CFSS_code + " not found");
        }

        try {
            customerFacingServiceSpecRepository.deleteById(CFSS_code);
            return "CustomerFacingServiceSpec  with ID " + CFSS_code + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting CustomerFacingServiceSpec with ID: " + CFSS_code, e);
        }
    }

    @Override
    public CustomerFacingServiceSpec findById(int CFSS_code) {
        try {
            return customerFacingServiceSpecRepository.findById(CFSS_code)
                    .orElseThrow(() -> new ResourceNotFoundException("CustomerFacingServiceSpec  with ID " + CFSS_code + " not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("CustomerFacingServiceSpec  with ID \"" + CFSS_code + "\" not found", e);
        }
    }

    public CustomerFacingServiceSpec changeServiceStatus(int CFSS_code) {
        try {
            CustomerFacingServiceSpec existingService = findById(CFSS_code);

            switch (existingService.getStatus()) {
                case "Working state":
                    existingService.setStatus("Validated");
                    break;

                case "Validated":
                    existingService.setStatus("Suspended");
                    break;

                case "Suspended":
                    throw new ServiceLogicException("CustomerFacingServiceSpec " + existingService.getServiceSpecType() + " isn't fit to be offered for sale anymore.");

                default:
                    throw new InvalidInputException("Invalid status transition.");
            }

            return customerFacingServiceSpecRepository.save(existingService);

        } catch (ServiceLogicException e) {
            throw e;
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("CustomerFacingServiceSpec with ID \"" + CFSS_code + "\" not found", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while changing CustomerFacingServiceSpec status", e);
        }
    }


    @Override
    public boolean findByNameexist(String serviceSpecType) {
        try {
            Optional<CustomerFacingServiceSpec> optionalService = customerFacingServiceSpecRepository.findByServiceSpecType(serviceSpecType);
            return optionalService.isPresent();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding the service");
        }
    }

    private void validateNotNullFields(CustomerFacingServiceSpec customerFacingServiceSpec) {
        if (customerFacingServiceSpec.getServiceSpecType() == null) {
            throw new InvalidInputException("ServiceSpecType cannot be null");
        }
    }
}

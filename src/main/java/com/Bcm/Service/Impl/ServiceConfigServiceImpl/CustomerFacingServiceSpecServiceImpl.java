package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Exception.*;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpecDTO;
import com.Bcm.Repository.ServiceConfigRepo.CustomerFacingServiceSpecRepository;
import com.Bcm.Repository.ServiceConfigRepo.ResourceFacingServiceSpecRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CustomerFacingServiceSpecServiceImpl implements CustomerFacingServiceSpecService {

    final CustomerFacingServiceSpecRepository customerFacingServiceSpecRepository;
    final ResourceFacingServiceSpecRepository resourceFacingServiceSpecRepository;

    @Override
    public CustomerFacingServiceSpec create(CustomerFacingServiceSpec customerFacingServiceSpec) {
        try {
            validateNotNullFields(customerFacingServiceSpec);

            Optional<CustomerFacingServiceSpec> existingServiceSpec = customerFacingServiceSpecRepository.findByServiceSpecType(
                    customerFacingServiceSpec.getServiceSpecType()
            );

            if (existingServiceSpec.isPresent()) {
                throw new ServiceAlreadyExistsException(
                        "CustomerFacingServiceSpec with name '" + customerFacingServiceSpec.getName() + "' already exists."
                );
            }
            customerFacingServiceSpec.setStatus("Working state");
            return customerFacingServiceSpecRepository.save(customerFacingServiceSpec);

        } catch (CFSSAlreadyExistsException e) {
            throw e;
        } catch (InvalidInputException e) {
            throw new InvalidInputException("Invalid input: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Database error: " + e.getRootCause().getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage(), e);
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
    public CustomerFacingServiceSpec update(int serviceId, CustomerFacingServiceSpec updatedCustomerFacingServiceSpec) {
        Optional<CustomerFacingServiceSpec> existingCustomerFacingServiceSpecOptional = customerFacingServiceSpecRepository.findById(serviceId);

        if (existingCustomerFacingServiceSpecOptional.isPresent()) {
            CustomerFacingServiceSpec existingCustomerFacingServiceSpec = existingCustomerFacingServiceSpecOptional.get();
            if (!existingCustomerFacingServiceSpec.getServiceSpecType().equals(updatedCustomerFacingServiceSpec.getServiceSpecType())) {
                if (customerFacingServiceSpecRepository.findByServiceSpecType(updatedCustomerFacingServiceSpec.getServiceSpecType()).isPresent()) {
                    throw new ServiceAlreadyExistsException("Service with the same name already exists");
                }
            }
            existingCustomerFacingServiceSpec.setName(updatedCustomerFacingServiceSpec.getName());
            existingCustomerFacingServiceSpec.setDescription(updatedCustomerFacingServiceSpec.getDescription());
            existingCustomerFacingServiceSpec.setServiceSpecType(updatedCustomerFacingServiceSpec.getServiceSpecType());
            existingCustomerFacingServiceSpec.setExternalId(updatedCustomerFacingServiceSpec.getExternalId());
            existingCustomerFacingServiceSpec.setLogicalResource(updatedCustomerFacingServiceSpec.getLogicalResource());
            existingCustomerFacingServiceSpec.setStatus(updatedCustomerFacingServiceSpec.getStatus());
            return customerFacingServiceSpecRepository.save(existingCustomerFacingServiceSpec);
        } else {
            throw new ResourceNotFoundException("Could not find CustomerFacingServiceSpec with ID: " + serviceId);
        }
    }


    @Override
    public String delete(int serviceId) {
        if (!customerFacingServiceSpecRepository.existsById(serviceId)) {
            throw new ResourceNotFoundException("CustomerFacingServiceSpec with ID " + serviceId + " not found");
        }

        try {
            customerFacingServiceSpecRepository.deleteById(serviceId);
            return "CustomerFacingServiceSpec  with ID " + serviceId + " was successfully deleted";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting CustomerFacingServiceSpec with ID: " + serviceId, e);
        }
    }

    @Override
    public CustomerFacingServiceSpec findById(int serviceId) {
        try {
            return customerFacingServiceSpecRepository.findById(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("CustomerFacingServiceSpec  with ID " + serviceId + " not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("CustomerFacingServiceSpec  with ID \"" + serviceId + "\" not found", e);
        }
    }

    public CustomerFacingServiceSpec changeServiceStatus(int serviceId) {
        try {
            CustomerFacingServiceSpec existingService = findById(serviceId);

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
            throw new RuntimeException("CustomerFacingServiceSpec with ID \"" + serviceId + "\" not found", e);
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


    public CustomerFacingServiceSpecDTO getCustomerFacingServiceSpecDTO(int serviceId) {
        CustomerFacingServiceSpec customerFacingServiceSpec = customerFacingServiceSpecRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerFacingServiceSpec with ID " + serviceId + " not found"));

        return new CustomerFacingServiceSpecDTO(
                customerFacingServiceSpec.getServiceId(),
                customerFacingServiceSpec.getName(),
                customerFacingServiceSpec.getDescription(),
                customerFacingServiceSpec.getServiceSpecType(),
                customerFacingServiceSpec.getExternalId(),
                customerFacingServiceSpec.getLogicalResource(),
                customerFacingServiceSpec.getStatus()
        );
    }


    public List<CustomerFacingServiceSpecDTO> getAllCustomerFacingServiceSpecDTOs() {
        List<CustomerFacingServiceSpec> customerFacingServiceSpecs = customerFacingServiceSpecRepository.findAll();

        return customerFacingServiceSpecs.stream().map(customerFacingServiceSpec -> {


            return new CustomerFacingServiceSpecDTO(
                    customerFacingServiceSpec.getServiceId(),
                    customerFacingServiceSpec.getName(),
                    customerFacingServiceSpec.getDescription(),
                    customerFacingServiceSpec.getServiceSpecType(),
                    customerFacingServiceSpec.getExternalId(),
                    customerFacingServiceSpec.getLogicalResource(),
                    customerFacingServiceSpec.getStatus()
            );
        }).collect(Collectors.toList());
    }

    private void validateNotNullFields(CustomerFacingServiceSpec customerFacingServiceSpec) {
        if (customerFacingServiceSpec.getServiceSpecType() == null) {
            throw new InvalidInputException("ServiceSpecType cannot be null");
        }
    }

    @Override
    public boolean existsByName(String name) {
        return customerFacingServiceSpecRepository.findByName(name).isPresent();
    }
}

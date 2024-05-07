package com.Bcm.Service.Impl.ServiceConfigServiceImpl;

import com.Bcm.Exception.*;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpecDTO;
import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpec;
import com.Bcm.Model.ServiceABE.ResourceFacingServiceSpecDTO;
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
                        "CustomerFacingServiceSpec with serviceSpecType '" + customerFacingServiceSpec.getServiceSpecType() + "' already exists."
                );
            }

            if (customerFacingServiceSpec.getResourceFacingServiceSpec() != null) {
                for (String rfssName : customerFacingServiceSpec.getResourceFacingServiceSpec()) {
                    if (!resourceFacingServiceSpecRepository.existsByName(rfssName)) {
                        throw new InvalidInputException("ResourceFacingServiceSpec '" + rfssName + "' does not exist.");
                    }
                }
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


    public CustomerFacingServiceSpecDTO getCustomerFacingServiceSpecDTO(int CFSS_code) {
        CustomerFacingServiceSpec customerFacingServiceSpec = customerFacingServiceSpecRepository.findById(CFSS_code)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerFacingServiceSpec with ID " + CFSS_code + " not found"));

        List<ResourceFacingServiceSpecDTO> resourceSpecs = customerFacingServiceSpec.getResourceFacingServiceSpec().stream()
                .map(rfssName -> {
                    Optional<ResourceFacingServiceSpec> resourceSpec = resourceFacingServiceSpecRepository.findByName(rfssName);
                    if (resourceSpec.isPresent()) {
                        ResourceFacingServiceSpec spec = resourceSpec.get();
                        return new ResourceFacingServiceSpecDTO(
                                spec.getRfss_code(),
                                spec.getName(),
                                spec.getDescription(),
                                spec.getValidFor(),
                                spec.getStatus()
                        );
                    } else {
                        throw new ResourceNotFoundException("ResourceFacingServiceSpec with name '" + rfssName + "' not found");
                    }
                })
                .collect(Collectors.toList());

        return new CustomerFacingServiceSpecDTO(
                customerFacingServiceSpec.getCFSS_code(),
                customerFacingServiceSpec.getExternalId(),
                customerFacingServiceSpec.getNumPlanCode(),
                customerFacingServiceSpec.getServiceSpecType(),
                customerFacingServiceSpec.getStatus(),
                customerFacingServiceSpec.getDescription(),
                resourceSpecs
        );
    }


    public List<CustomerFacingServiceSpecDTO> getAllCustomerFacingServiceSpecDTOs() {
        List<CustomerFacingServiceSpec> customerFacingServiceSpecs = customerFacingServiceSpecRepository.findAll();

        return customerFacingServiceSpecs.stream().map(customerFacingServiceSpec -> {
            List<ResourceFacingServiceSpecDTO> resourceSpecs = customerFacingServiceSpec.getResourceFacingServiceSpec().stream()
                    .map(rfssName -> {
                        ResourceFacingServiceSpec spec = resourceFacingServiceSpecRepository.findByName(rfssName)
                                .orElseThrow(() -> new ResourceNotFoundException("ResourceFacingServiceSpec with name '" + rfssName + "' not found"));

                        return new ResourceFacingServiceSpecDTO(
                                spec.getRfss_code(),
                                spec.getName(),
                                spec.getDescription(),
                                spec.getValidFor(),
                                spec.getStatus()
                        );
                    })
                    .collect(Collectors.toList());

            return new CustomerFacingServiceSpecDTO(
                    customerFacingServiceSpec.getCFSS_code(),
                    customerFacingServiceSpec.getExternalId(),
                    customerFacingServiceSpec.getNumPlanCode(),
                    customerFacingServiceSpec.getServiceSpecType(),
                    customerFacingServiceSpec.getStatus(),
                    customerFacingServiceSpec.getDescription(),
                    resourceSpecs
            );
        }).collect(Collectors.toList());
    }

    private void validateNotNullFields(CustomerFacingServiceSpec customerFacingServiceSpec) {
        if (customerFacingServiceSpec.getServiceSpecType() == null) {
            throw new InvalidInputException("ServiceSpecType cannot be null");
        }
    }
}

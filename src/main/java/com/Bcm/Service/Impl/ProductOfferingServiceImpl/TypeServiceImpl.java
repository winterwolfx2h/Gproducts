package com.Bcm.Service.Impl.ProductOfferingServiceImpl;


import com.Bcm.Model.ProductOfferingABE.Type;
import com.Bcm.Repository.ProductOfferingRepo.TypeRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {


    final TypeRepository typeRepository;

    @Override
    public List<Type> create(List<Type> types) {
        for (Type type : types) {
            if (typeRepository.findByTypeName(type.getTypeName()).isPresent()) {
                throw new RuntimeException("Type with name " + type.getTypeName() + " already exists");
            }

            if (type.getType_id() != 0 && !typeRepository.existsById(type.getType_id())) {
                throw new RuntimeException("Type ID " + type.getType_id() + " does not exist");
            }
        }

        return typeRepository.saveAll(types);
    }

    @Override
    public List<Type> read() {
        return typeRepository.findAll();
    }

    @Override
    public Type update(int type_id, Type updatedType) {
        Optional<Type> existingTypeOptional = typeRepository.findById(type_id);

        if (existingTypeOptional.isPresent()) {
            Type existingType = existingTypeOptional.get();
            existingType.setTypeName(updatedType.getTypeName());

            return typeRepository.save(existingType);
        } else {
            throw new RuntimeException("Could not find Type with ID: " + type_id);
        }
    }

    @Override
    public String delete(int type_id) {
        typeRepository.deleteById(type_id);
        return ("Type was successfully deleted");
    }

    @Override
    public Type findById(int type_id) {
        Optional<Type> optionalPlan = typeRepository.findById(type_id);
        return optionalPlan.orElseThrow(() -> new RuntimeException("Type with ID " + type_id + " not found"));
    }

    @Override
    public List<Type> searchByKeyword(String typeName) {
        return typeRepository.searchByKeyword(typeName);
    }

    @Override
    public Type findByTypeName(String typeName) {
        try {
            Optional<Type> optionalType = typeRepository.findByTypeName(typeName);
            return optionalType.orElseThrow(() -> new RuntimeException("Type with " + typeName + " not found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid argument provided for finding Type");
        }
    }

    @Override
    public boolean existsById(int type_id) {
        return typeRepository.existsById(type_id);
    }
}


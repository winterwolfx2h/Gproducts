package com.Bcm.Repository.ProductOfferingRepo;


import com.Bcm.Model.ProductOfferingABE.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Integer> {

    Optional<Type> findById(int type_id);

    Optional<Type> findByTypeName(String typeName);

    @Query("SELECT p FROM Type p WHERE p.typeName LIKE :typeName ")
    List<Type> searchByKeyword(String typeName);
}

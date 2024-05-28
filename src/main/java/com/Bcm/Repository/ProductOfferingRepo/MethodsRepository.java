package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.Methods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MethodsRepository extends JpaRepository<Methods, Integer> {

    Optional<Methods> findById(int method_Id);

    Optional<Methods> findByName(String name);

    @Query("SELECT p FROM Methods p WHERE p.name = :name")
    List<Methods> searchByKeyword(String name);

    boolean existsByName(String name);
}
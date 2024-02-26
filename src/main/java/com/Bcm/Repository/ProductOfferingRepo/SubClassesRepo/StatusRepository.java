package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findById(int pos_code);

    Optional<Status> findByname(String name);

    Optional<Status> findByName(String name);

    @Query("SELECT p FROM Status p WHERE p.name = :name")
    List<Status> searchByKeyword(String name);
}

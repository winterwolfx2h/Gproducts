package com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {

    Optional<Gender> findById(int GID);

    @Query("SELECT p FROM Gender p WHERE p.name LIKE %:name% ")
    List<Gender> searchByKeyword(String name);
}
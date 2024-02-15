package com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.CableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CableTypeRepository extends JpaRepository<CableType, Integer> {

    Optional<CableType> findById(int CbTID);

    @Query("SELECT p FROM CableType p WHERE p.name LIKE %:name% ")
    List<CableType> searchByKeyword(String name);
}
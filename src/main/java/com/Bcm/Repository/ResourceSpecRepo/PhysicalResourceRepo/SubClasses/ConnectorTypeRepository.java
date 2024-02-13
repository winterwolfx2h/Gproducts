package com.Bcm.Repository.ResourceSpecRepo.PhysicalResourceRepo.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.ConnectorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectorTypeRepository extends JpaRepository<ConnectorType, Integer> {

    Optional<ConnectorType> findById(int CnTID);

    @Query("SELECT p FROM ConnectorType p WHERE p.name LIKE %:name% ")
    List<ConnectorType> searchByKeyword(String name);
}
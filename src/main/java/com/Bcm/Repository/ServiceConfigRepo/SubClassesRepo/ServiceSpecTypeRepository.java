package com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo;

import com.Bcm.Model.ServiceABE.SubClasses.ServiceSpecType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceSpecTypeRepository extends JpaRepository<ServiceSpecType, Integer> {

    Optional<ServiceSpecType> findById(int SST_code);

    @Query("SELECT p FROM ServiceSpecType p WHERE p.name LIKE %:name% ")
    List<ServiceSpecType> searchByKeyword(String name);
}

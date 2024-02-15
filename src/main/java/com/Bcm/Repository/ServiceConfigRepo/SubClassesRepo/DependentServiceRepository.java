package com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo;

import com.Bcm.Model.ServiceABE.SubClasses.DependentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DependentServiceRepository extends JpaRepository<DependentService, Integer> {

    Optional<DependentService> findById(int DS_code);

    @Query("SELECT p FROM DependentService p WHERE p.name LIKE %:name% ")
    List<DependentService> searchByKeyword(String name);
}

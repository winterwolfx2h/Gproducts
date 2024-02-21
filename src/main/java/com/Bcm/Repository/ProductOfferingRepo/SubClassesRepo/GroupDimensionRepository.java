package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.GroupDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupDimensionRepository extends JpaRepository<GroupDimension, Integer> {

    Optional<GroupDimension> findById(int po_GdCode);

    Optional<GroupDimension> findByName(String name);

    @Query("SELECT p FROM GroupDimension p WHERE p.name = :name")
    List<GroupDimension> searchByKeyword(String name);
}

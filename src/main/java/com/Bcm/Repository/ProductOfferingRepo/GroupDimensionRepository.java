package com.Bcm.Repository.ProductOfferingRepo;

import com.Bcm.Model.ProductOfferingABE.GroupDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupDimensionRepository extends JpaRepository<GroupDimension, Integer> {

    Optional<GroupDimension> findById(int po_GdCode);

    @Query("SELECT p FROM GroupDimension p WHERE p.name LIKE %:name% ")
    List<GroupDimension> searchByKeyword(String name);
}

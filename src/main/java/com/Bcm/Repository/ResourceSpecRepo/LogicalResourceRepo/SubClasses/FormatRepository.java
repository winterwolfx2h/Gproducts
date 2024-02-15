package com.Bcm.Repository.ResourceSpecRepo.LogicalResourceRepo.SubClasses;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FormatRepository extends JpaRepository<Format, Integer> {

    Optional<Format> findById(int FID);

    @Query("SELECT p FROM Format p WHERE p.name LIKE %:name% ")
    List<Format> searchByKeyword(String name);
}

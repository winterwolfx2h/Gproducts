package com.Bcm.Repository.ResourceSpecRepo.ResourceConfigRepo.SubClasses;

import com.Bcm.Model.ResourceSpecABE.ResourceConfigBE.SubClasses.ResourceConfigVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

    @Repository
    public interface ResourceConfigVersionRepository extends JpaRepository<ResourceConfigVersion, Integer> {

        Optional<ResourceConfigVersion> findById(int RCVID);

        @Query("SELECT p FROM ResourceConfigVersion p WHERE p.name LIKE %:name% ")
        List<ResourceConfigVersion> searchByKeyword(String name);
    }
package com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo;

import com.Bcm.Model.ServiceABE.SubClasses.SalesChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesChannelRepository extends JpaRepository<SalesChannel, Integer> {

    Optional<SalesChannel> findById(int SC_code);

    @Query("SELECT p FROM SalesChannel p WHERE p.name LIKE %:name% ")
    List<SalesChannel> searchByKeyword(String name);
}

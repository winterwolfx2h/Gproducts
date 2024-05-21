package com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {

    Optional<Channel> findById(int po_ChannelCode);

    Optional<Channel> findByName(String name);

    @Query("SELECT p FROM Channel p WHERE p.name = :name")
    List<Channel> searchByKeyword(String name);

    boolean existsByName(String name);
}

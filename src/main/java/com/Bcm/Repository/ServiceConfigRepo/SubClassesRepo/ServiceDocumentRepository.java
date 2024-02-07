package com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo;

import com.Bcm.Model.ServiceABE.SubClasses.SalesChannel;
import com.Bcm.Model.ServiceABE.SubClasses.ServiceDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceDocumentRepository extends JpaRepository<ServiceDocument, Integer> {

    Optional<ServiceDocument> findById(int SD_code);

    @Query("SELECT p FROM ServiceDocument p WHERE p.name LIKE %:name% ")
    List<ServiceDocument> searchByKeyword(String name);
}

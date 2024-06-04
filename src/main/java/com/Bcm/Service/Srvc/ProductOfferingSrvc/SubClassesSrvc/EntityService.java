package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Entity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EntityService {

    Entity create(Entity entity);

    List<Entity> read();

    Entity update(int entityCode, Entity entity);

    String delete(int entityCode);

    Entity findById(int entityCode);

    List<Entity> searchByKeyword(String name);

    Entity findByName(String name);

    boolean findByNameexist(String name);

    boolean existsById(int entityCode);


}

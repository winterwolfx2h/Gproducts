package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatusService {

    Status create(Status status);

    List<Status> read();

    Status update(int pos_code, Status status);

    String delete(int po_StatusCode);

    Status findById(int po_StatusCode);

    Status findByName(String name);

    List<Status> searchByKeyword(String name);


}

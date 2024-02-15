package com.Bcm.Service.Srvc.ResourceSpecService.PhysicalResourceService.SubClasses;

import com.Bcm.Model.ResourceSpecABE.PhysicalResourceBE.SubClasses.Gender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenderService {

    Gender create(Gender gender);

    List<Gender> read();

    Gender update(int GID, Gender gender);

    String delete(int GID);

    Gender findById(int GID);

    List<Gender> searchByKeyword(String name);
}
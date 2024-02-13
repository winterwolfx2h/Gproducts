package com.Bcm.Service.Srvc.ResourceSpecService.LogicalResourceService.SubClasses;

import com.Bcm.Model.ResourceSpecABE.LogicalResourceBE.SubClasses.Format;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FormatService {

    Format create(Format format);

    List<Format> read();

    Format update(int FID, Format format);

    String delete(int FID);

    Format findById(int FID);

    List<Format> searchByKeyword(String name);

}
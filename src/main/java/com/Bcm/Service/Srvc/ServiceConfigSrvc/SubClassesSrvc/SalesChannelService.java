package com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc;

import com.Bcm.Model.ServiceABE.SubClasses.SalesChannel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesChannelService {

    SalesChannel create(SalesChannel salesChannel);

    List<SalesChannel> read();

    SalesChannel update(int SC_code, SalesChannel salesChannel);

    String delete(int SC_code);

    SalesChannel findById(int SC_code);

    List<SalesChannel> searchByKeyword(String name);
}

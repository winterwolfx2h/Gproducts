package com.Bcm.Service.Impl.ServiceConfigServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ServiceABE.SubClasses.SalesChannel;
import com.Bcm.Repository.ServiceConfigRepo.SubClassesRepo.SalesChannelRepository;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.SubClassesSrvc.SalesChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesChannelServiceServiceImpl implements SalesChannelService {


    @Autowired
    SalesChannelRepository salesChannelRepository;

    @Autowired
    SalesChannelService salesChannelService;

    @Override
    public SalesChannel create(SalesChannel SalesChannel) {
        return salesChannelRepository.save(SalesChannel);
    }

    @Override
    public List<SalesChannel> read() {
        return salesChannelRepository.findAll();
    }


    @Override
    public SalesChannel update(int SC_code, SalesChannel updatedSalesChannel) {
        Optional<SalesChannel> existingSalesChannelOptional = salesChannelRepository.findById(SC_code);

        if (existingSalesChannelOptional.isPresent()) {
            SalesChannel existingSalesChannel = existingSalesChannelOptional.get();
            existingSalesChannel.setName(updatedSalesChannel.getName());
            return salesChannelRepository.save(existingSalesChannel);
        } else {
            throw new RuntimeException("Could not find Group Dimension with ID: " + SC_code);
        }
    }


    @Override
    public String delete(int SC_code) {
        salesChannelRepository.deleteById(SC_code);
        return ("Group Dimension was successfully deleted");
    }

    @Override
    public SalesChannel findById(int SC_code) {
        Optional<SalesChannel> optionalSalesChannel = salesChannelRepository.findById(SC_code);
        return optionalSalesChannel.orElseThrow(() -> new RuntimeException("SalesChannel with ID " + SC_code + " not found"));
    }


    @Override
    public List<SalesChannel> searchByKeyword(String name) {
        return salesChannelRepository.searchByKeyword(name);
    }


}

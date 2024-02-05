package com.Bcm.Service.Impl.ProductOfferingServiceImpl.SubClassesServiceImpl;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.MarketRepository;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketServiceImpl implements MarketService {


    @Autowired
    MarketRepository marketRepository;

    @Autowired
    MarketService marketService;

    @Override
    public Market create(Market market) {
        return marketRepository.save(market);
    }

    @Override
    public List<Market> read() {
        return marketRepository.findAll();
    }

    @Override
    public Market update(int po_MarketCode, Market updatedMarket) {
        Optional<Market> existingMarketOptional = marketRepository.findById(po_MarketCode);

        if (existingMarketOptional.isPresent()) {
            Market existingMarket = existingMarketOptional.get();
            existingMarket.setName(updatedMarket.getName());
            return marketRepository.save(existingMarket);
        } else {
            throw new RuntimeException("Could not find Group Dimension with ID: " + po_MarketCode);
        }
    }

    @Override
    public String delete(int po_MarketCode) {
        marketRepository.deleteById(po_MarketCode);
        return ("Market was successfully deleted");
    }

    @Override
    public Market findById(int po_MarketCode) {
        Optional<Market> optionalMarket = marketRepository.findById(po_MarketCode);
        return optionalMarket.orElseThrow(() -> new RuntimeException("Market with ID " + po_MarketCode + " not found"));
    }


    @Override
    public List<Market> searchByKeyword(String name) {
        return marketRepository.searchByKeyword(name);
    }


}

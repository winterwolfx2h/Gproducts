package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Market.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface MarketService {

    @Transactional
    MarketResponseDTO createOrUpdateMarket(MarketRequestDTO MarketRequestDTO);

    List<Market> read();

    MarketResponseDTO update(int po_MarketCode, MarketRequestDTOUpdate MarketRequestDTO);

    String delete(int po_MarketCode);

    Market findById(int po_MarketCode);

    List<Market> searchByKeyword(String name);

    Market findByName(String name);

    boolean findByNameexist(String name);

    boolean existsById(int po_MarketCode);

    List<MarketResponseDTO> getAllMarkets();

    List<SubMarket> readSubMarkets();

    boolean findBySubMarketNameExist(String subMarketName);

    void deleteSubMarket(int po_SubMarketCode);

    void unlinkSubMarketFromMarket(int MarketId, int subMarketId);
}

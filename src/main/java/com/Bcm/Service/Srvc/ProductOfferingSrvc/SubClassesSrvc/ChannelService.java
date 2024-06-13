package com.Bcm.Service.Srvc.ProductOfferingSrvc.SubClassesSrvc;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ChannelService {

  Channel create(Channel channel);

  List<Channel> read();

  Channel update(int po_ChannelCode, Channel channel);

  String delete(int po_ChannelCode);

  Channel findById(int po_ChannelCode);

  List<Channel> searchByKeyword(String name);

  Channel findByName(String name);

  boolean findByNameexist(String name);

  boolean existsById(int po_ChannelCode);
}

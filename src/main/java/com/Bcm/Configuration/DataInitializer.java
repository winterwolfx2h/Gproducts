package com.Bcm.Configuration;

import com.Bcm.Model.ProductOfferingABE.SubClasses.Channel;
import com.Bcm.Repository.ProductOfferingRepo.SubClassesRepo.ChannelRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

  private final ChannelRepository channelRepository;

  public DataInitializer(ChannelRepository channelRepository) {
    this.channelRepository = channelRepository;
  }

  @Bean
  public ApplicationRunner initializer() {
    return args -> {
      if (!channelRepository.existsById(0)) {
        Channel channel = new Channel();
        channel.setChannelCode(0);
        channel.setName("ALL");
        channel.setDescription("ALL OF THE ABOVE");
        channelRepository.save(channel);
      }
    };
  }
}

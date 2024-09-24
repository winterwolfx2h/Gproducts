package com.Bcm.Repository.ServiceConfigRepo;

import com.Bcm.Model.ServiceABE.CustomerFacingServiceSpec;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerFacingServiceSpecRepository extends JpaRepository<CustomerFacingServiceSpec, Integer> {

  List<CustomerFacingServiceSpec> findByServiceSpecType(String serviceSpecType);

  Optional<CustomerFacingServiceSpec> findById(int serviceId);

  @Query("SELECT p FROM CustomerFacingServiceSpec p WHERE p.description LIKE %:description% ")
  List<CustomerFacingServiceSpec> searchByKeyword(String description);

  List<CustomerFacingServiceSpec> findByName(String name);

  @Query(
      value =
          "SELECT DISTINCT "
              + "lr.lr_id AS lr_id, "
              + "cfss.name AS service_name, "
              + "cfss.service_id AS service_id, "
              + "lr.po_market_code AS market_code, "
              + "market.name AS market_name, "
              + "lr.po_sub_market_code AS sub_market_code, "
              + "sub_market.sub_market_name AS sub_market_name "
              + "FROM public.logical_resource lr "
              + "JOIN public.customer_facing_service_spec cfss "
              + "ON lr.lr_id = cfss.lr_id "
              + "JOIN public.market market "
              + "ON lr.po_market_code = market.po_market_code "
              + "JOIN public.sub_market sub_market "
              + "ON lr.po_sub_market_code = sub_market.po_sub_market_code "
              + "WHERE market.name = :marketName "
              + "AND sub_market.sub_market_name = :subMarketName "
              + "ORDER BY lr.lr_id ASC, cfss.service_id ASC",
      nativeQuery = true)
  List<Object[]> searchByMarketSubMarketRaw(
      @Param("marketName") String marketName, @Param("subMarketName") String subMarketName);
}

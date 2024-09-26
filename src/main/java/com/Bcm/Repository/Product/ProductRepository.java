package com.Bcm.Repository.Product;

import com.Bcm.Model.Product.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  Optional<Product> findById(int Product_id);

  List<Product> findByFamilyName(String familyName);

  List<Product> findByNameContainingIgnoreCase(String name);

  Optional<Product> findByName(String name);

  @Query(
      value =
          "SELECT pr.name AS physical_resource_name, cfss.name AS service_spec_name "
              + "FROM product_offering po "
              + "LEFT JOIN physical_resource pr ON po.pr_id = pr.pr_id "
              + "LEFT JOIN customer_facing_service_spec cfss ON po.service_id = cfss.service_id "
              + "WHERE po.product_id = :productId",
      nativeQuery = true)
  List<Object[]> findProductResourceDetails(@Param("productId") int productId);

  @Query(
      value =
          "SELECT DISTINCT "
              + "  p.product_id AS productId, "
              + "  c.channel_code AS channelCode, "
              + "  c.name AS channelName, "
              + "  e.entity_code AS entityCode, "
              + "  e.name AS entityName, "
              + "  pg.product_price_group_code AS productPriceGroupCode, "
              + "  pg.name AS productPriceGroupName, "
              + "  p.stock_ind AS stockInd "
              + "FROM "
              + "  Product p "
              + "  LEFT JOIN Product_Channel pc ON p.product_id = pc.product_id "
              + "  LEFT JOIN Channel c ON pc.channel_code = c.channel_code "
              + "  LEFT JOIN Product_Entity pe ON p.product_id = pe.product_id "
              + "  LEFT JOIN Entity e ON pe.entity_code = e.entity_code "
              + "  LEFT JOIN Product_PriceGroup pp ON p.product_id = pp.product_id "
              + "  LEFT JOIN Product_Price_Group pg ON pp.product_price_group_code = pg.product_price_group_code "
              + "WHERE "
              + "  p.product_id = :productId",
      nativeQuery = true)
  List<Map<String, Object>> findProductDetails(@Param("productId") int productId);

  boolean existsById(Integer productId);
}

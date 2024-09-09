package com.Bcm.Controller.ProductOfferingController.Relation;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PO-Business Process Controller", description = "All of the PO-Business Process's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/PO_BusinessProcess")
public class PoBusinessProcessController {

  final JdbcTemplate base;

  @Value("${insertRelation}")
  String sqlInsertPOBusiness;

  //    String sqlInsertPOBusiness = "INSERT INTO public.po_business_process(" +
  //            "product_id, business_process_id)" +
  //            "VALUES (?, ?)";

  String sqlAllPOBusiness = "SELECT product_id, business_process_id " + "FROM public.po_business_process";

  @PostMapping("/addPo_BusinessProcess")
  public Object addPo_BusinessProcess(@RequestParam Integer product_id, @RequestParam Integer business_process_id) {
    return base.update(sqlInsertPOBusiness, product_id, business_process_id);
  }

  @GetMapping("/getAllPo_BusinessProcess")
  public List<Map<String, Object>> getAllPo_BusinessProcess() {
    return base.queryForList(sqlAllPOBusiness);
  }
}

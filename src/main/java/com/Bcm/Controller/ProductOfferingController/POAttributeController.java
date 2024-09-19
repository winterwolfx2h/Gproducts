package com.Bcm.Controller.ProductOfferingController;

import com.Bcm.Exception.ErrorMessage;
import com.Bcm.Exception.InvalidInputException;
import com.Bcm.Exception.ServiceAlreadyExistsException;
import com.Bcm.Model.ProductOfferingABE.POAttributes;
import com.Bcm.Service.Srvc.ProductOfferingSrvc.POAttributesService;
import com.Bcm.Service.Srvc.ServiceConfigSrvc.CustomerFacingServiceSpecService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PO-Attribute Controller", description = "All of the PO-Attribute's methods")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/POAttribute")
public class POAttributeController {

  final JdbcTemplate base;

  final POAttributesService poAttributesService;
  final CustomerFacingServiceSpecService customerFacingServiceSpecService;

  @GetMapping("/listPOAttributes")
  @Cacheable(value = "AttributesCache")
  public List<POAttributes> read() {
    return poAttributesService.read();
  }

  @PostMapping("/add")
  @CacheEvict(value = "AttributesCache", allEntries = true)
  public ResponseEntity<?> create(@RequestBody List<POAttributes> POAttributesList) {
    try {
      List<POAttributes> createdPOAttributesList = new ArrayList<>();

      for (POAttributes poAttribute : POAttributesList) {
        // Validate dependentCfs
        String dependentCfs = poAttribute.getDependentCfs();
        if (dependentCfs != null
            && !dependentCfs.isEmpty()
            && !customerFacingServiceSpecService.findByNameexist(dependentCfs)) {
          return ResponseEntity.badRequest().body("Service with name '" + dependentCfs + "' does not exist.");
        }

        // Validate and create POAttributes
        String attributeCategoryName = poAttribute.getCategory();
        if (attributeCategoryName != null && !attributeCategoryName.isEmpty()) {
          for (POAttributes.ValueDescription valueDescription :
              (List<POAttributes.ValueDescription>)
                  (poAttribute.getValueDescription() != null ? poAttribute.getValueDescription() : new ArrayList<>())) {
            if (valueDescription.getDescription() == null) {
              valueDescription.setDescription("Default Description");
            }
          }

          if (poAttribute.getValueDescription() == null) {
            poAttribute.setValueDescription(new ArrayList<>());
          }

          if (poAttribute.getDefaultMaxSize() == null) {
            poAttribute.setDefaultMaxSize(new ArrayList<>());
          }

          POAttributes createdPlan = poAttributesService.create(poAttribute);
          createdPOAttributesList.add(createdPlan);
        } else {
          return ResponseEntity.badRequest().body("Attribute category is missing for one or more POAttributes.");
        }
      }

      return ResponseEntity.ok(createdPOAttributesList);
    } catch (ServiceAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (InvalidInputException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
    }
  }

  @GetMapping("/searchByProductId")
  public List<POAttributes> searchByProductID(@RequestParam Integer productId) {
    // SQL query to get all columns from POAttributes where product_id matches
    String sqlSearchByProductId =
        "SELECT po_attribute_code, name, category, bsexternal_id, csexternal_id, attribute_type, data_type, mandatory,"
            + " change_ind ,display_format, externalcfs, dependent_cfs, product_id FROM poattributes WHERE product_id ="
            + " ?";

    // Execute the query and map the result set to POAttributes objects
    List<POAttributes> poAttributesResponses;
    poAttributesResponses =
        base.query(
            sqlSearchByProductId,
            new Object[] {productId},
            new RowMapper<POAttributes>() {
              @Override
              public POAttributes mapRow(ResultSet rs, int rowNum) throws SQLException {
                POAttributes response = new POAttributes();
                response.setPoAttribute_code(rs.getInt("po_attribute_code"));
                response.setName(rs.getString("name"));
                response.setCategory(rs.getString("category"));
                response.setBsexternalId(rs.getString("bsexternal_id"));
                response.setCsexternalId(rs.getString("csexternal_id"));
                response.setAttributeType(rs.getString("attribute_type"));
                response.setDataType(rs.getString("data_type"));
                response.setMandatory(rs.getBoolean("mandatory"));
                response.setChangeInd(rs.getBoolean("change_ind"));
                response.setDisplayFormat(rs.getString("display_format"));
                response.setExternalcfs(rs.getBoolean("externalcfs"));
                response.setDependentCfs(rs.getString("dependent_cfs"));
                response.setProduct_id(rs.getInt("product_id"));

                // Query for ValueDescription list
                String sqlValueDescription =
                    "SELECT value, description, defaultvalue FROM attributes_value_des WHERE po_attribute_code = ?";
                List<POAttributes.ValueDescription> valueDescriptions =
                    base.query(
                        sqlValueDescription,
                        new Object[] {response.getPoAttribute_code()},
                        new RowMapper<POAttributes.ValueDescription>() {
                          @Override
                          public POAttributes.ValueDescription mapRow(ResultSet rs, int rowNum) throws SQLException {
                            POAttributes.ValueDescription valueDescription = new POAttributes.ValueDescription();
                            valueDescription.setValue(rs.getString("value"));
                            valueDescription.setDescription(rs.getString("description"));
                            valueDescription.setDefaultvalue(rs.getBoolean("defaultvalue"));
                            return valueDescription;
                          }
                        });
                response.setValueDescription(valueDescriptions);

                // Query for defaultMaxSize list
                String sqlDefaultMaxSize =
                    "SELECT max_size, defaultvalue, value_des FROM attributes_domaine WHERE po_attribute_code = ?";
                List<POAttributes.DefaultMaxSize> defaultMaxSizes =
                    base.query(
                        sqlDefaultMaxSize,
                        new Object[] {response.getPoAttribute_code()},
                        new RowMapper<POAttributes.DefaultMaxSize>() {
                          @Override
                          public POAttributes.DefaultMaxSize mapRow(ResultSet rs, int rowNum) throws SQLException {
                            POAttributes.DefaultMaxSize defaultMaxSize = new POAttributes.DefaultMaxSize();
                            defaultMaxSize.setMaxSize(rs.getString("max_size"));
                            defaultMaxSize.setDefaultvalue(rs.getString("defaultvalue"));
                            defaultMaxSize.setValueDes(rs.getString("value_des"));
                            return defaultMaxSize;
                          }
                        });

                response.setDefaultMaxSize(defaultMaxSizes);

                return response;
              }
            });

    return poAttributesResponses;
  }

  @PutMapping("/updatePOAttributes/{poAttribute_code}")
  @CacheEvict(value = "AttributesCache", allEntries = true)
  public ResponseEntity<?> update(@PathVariable int poAttribute_code, @RequestBody POAttributes POAttributes) {
    try {
      POAttributes updatedPlan = poAttributesService.update(poAttribute_code, POAttributes);
      return ResponseEntity.ok(updatedPlan);
    } catch (InvalidInputException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/updateOrSavePOAttributes")
  @CacheEvict(value = "AttributesCache", allEntries = true)
  public ResponseEntity<?> saveOrUpdate(@RequestBody List<POAttributes> poAttributesList) {
    try {
      List<POAttributes> savedOrUpdatedPlans = new ArrayList<>();
      for (POAttributes poAttributes : poAttributesList) {
        POAttributes savedOrUpdatedPlan =
            poAttributesService.saveOrUpdate(poAttributes.getPoAttribute_code(), poAttributes);
        savedOrUpdatedPlans.add(savedOrUpdatedPlan);
      }
      return ResponseEntity.ok(savedOrUpdatedPlans);
    } catch (InvalidInputException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{poAttribute_code}")
  @CacheEvict(value = "AttributesCache", allEntries = true)
  public String delete(@PathVariable int poAttribute_code) {
    return poAttributesService.delete(poAttribute_code);
  }

  @GetMapping("/getById/{poAttribute_code}")
  public ResponseEntity<POAttributes> getById(@PathVariable int poAttribute_code) {
    try {
      POAttributes foundPlan = poAttributesService.findById(poAttribute_code);
      return ResponseEntity.ok(foundPlan);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  private RuntimeException handleException(Exception e) {
    ErrorMessage errorMessage =
        new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new Date(),
            e.getMessage(),
            "There was an error processing the request.");
    return new RuntimeException(errorMessage.toString(), e);
  }

  @CacheEvict(value = "AttributesCache", allEntries = true)
  public void invalidateAttributesCache() {}
}

package com.Bcm.Repository.ExternalBSCSDBRepo;

import com.Bcm.Configuration.RatePlanBSCSRowMapper;
import com.Bcm.Model.BSCSModels.RatePlanBSCS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExternalDataRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RatePlanBSCS> findAll() {
        String query = "SELECT * FROM RATEPLANBSCS";
        return jdbcTemplate.query(query, new RatePlanBSCSRowMapper());
    }
}

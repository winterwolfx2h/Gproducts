package com.Bcm.Configuration;

import com.Bcm.Model.BSCSModels.RatePlanBSCS;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatePlanBSCSRowMapper implements RowMapper<RatePlanBSCS> {

    @Override
    public RatePlanBSCS mapRow(ResultSet rs, int rowNum) throws SQLException {
        RatePlanBSCS ratePlan = new RatePlanBSCS();
        ratePlan.setTmcode(rs.getString("TMCODE"));
        ratePlan.setDes(rs.getString("DES"));
        ratePlan.setShdes(rs.getString("SHDES"));
        return ratePlan;
    }
}

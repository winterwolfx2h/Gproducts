package com.Bcm.Cfg.MockTest;

import com.Bcm.Cfg.RatePlanBSCSRowMapper;
import com.Bcm.Model.BSCSModels.RatePlanBSCS;
import com.Bcm.Repository.ExternalBSCSDBRepo.ExternalDataRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ExternalDataRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ExternalDataRepository externalDataRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        // Mock the result of jdbcTemplate.query() method
        List<RatePlanBSCS> expectedRatePlans = Arrays.asList(
                new RatePlanBSCS("tmcode1", "des1", "shdes1"),
                new RatePlanBSCS("tmcode2", "des2", "shdes2")
                // Add more sample data if needed
        );
        // Mock the behavior of jdbcTemplate.query()
        when(jdbcTemplate.query("SELECT * FROM RATEPLANBSCS", new RatePlanBSCSRowMapper()))
                .thenReturn(expectedRatePlans);

        // Call the method to be tested
        List<RatePlanBSCS> actualRatePlans = externalDataRepository.findAll();

        // Assert the result
        assertEquals(expectedRatePlans.size(), actualRatePlans.size());
        // You can add more assertions as needed
    }
}

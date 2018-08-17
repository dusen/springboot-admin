package com.fastretailing.dcp.storecommon.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.ApplicationTest;

/**
 * BeanConfiguration test class.
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class BeanConfigurationTest {

    /** Model mapper. */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Model mapper is not null, set configuration test.
     */
    @Test
    public void testModelMapper() {
        assertNotNull(modelMapper);
        assertEquals(MatchingStrategies.STRICT,
                modelMapper.getConfiguration().getMatchingStrategy());
    }
}

package com.ideasquefluyen.ivr.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;


/**
 * Unit test for {@link ZipcodeService}
 *
 *
 * @author dmarafetti
 * @since 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com/ideasquefluyen/ivr/api/spring/application-context.xml")
@WebIntegrationTest({"server.port=8080"})
public class ZipcodeServiceHttpImplTest {


    @Autowired
    RestTemplate rest;


    @Test
    public void testValidateZipcode() {






    }

}

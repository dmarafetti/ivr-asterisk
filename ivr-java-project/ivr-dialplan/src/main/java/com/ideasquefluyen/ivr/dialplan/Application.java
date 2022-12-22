package com.ideasquefluyen.ivr.dialplan;

import java.io.IOException;

import org.asteriskjava.fastagi.DefaultAgiServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * IVR Application bootstrapper.
 *
 *
 * @author diego.marafetti
 * @since 1.0.0
 *
 */
@Configuration
@ComponentScan
@ImportResource(locations={"classpath:/com/ideasquefluyen/ivr/dialplan/spring/script-context.xml",
                           "classpath:/com/ideasquefluyen/ivr/dialplan/spring/mapping-context.xml",
                           "classpath:/com/ideasquefluyen/ivr/dialplan/spring/application-context.xml"})
public class Application {


    private static final Logger logger = LoggerFactory.getLogger(Application.class);



    /**
     *
     * Main entry point
     *
     * @throws IOException
     * @throws IllegalStateException
     */
    public static void main(String[] args) throws IllegalStateException, IOException {

        ApplicationContext context = SpringApplication.run(Application.class, args);

        logger.info("Starting Fastagi server...");
        DefaultAgiServer server = context.getBean("agiServer", DefaultAgiServer.class);
        server.startup();
    }

}

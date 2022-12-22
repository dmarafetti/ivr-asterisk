package com.ideasquefluyen.ivr.dialplan.scripts.flow.verify;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideasquefluyen.ivr.common.script.EnhancedAgiScript;



/**
 * The welcome flow
 *
 * @author diego.marafetti
 * @since 1.0.0
 */
public class MainVerifyScript extends EnhancedAgiScript {

    private static final Logger logger = LoggerFactory.getLogger(MainVerifyScript.class);



    /* (non-Javadoc)
     * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
     */
    @Override
    public void service(AgiRequest request, AgiChannel channel) throws AgiException {


        logger.debug("FlowTwoScript service execute");

        // playback

        // listen option

        // set option


    }

}

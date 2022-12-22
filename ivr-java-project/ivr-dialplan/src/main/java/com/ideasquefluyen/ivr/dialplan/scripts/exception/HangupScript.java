package com.ideasquefluyen.ivr.dialplan.scripts.exception;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideasquefluyen.ivr.common.script.EnhancedAgiScript;

/**
 * Logging for hang up event
 *
 *
 * @author ideasquefluyen
 * @since 1.0.0
 *
 */
public class HangupScript extends EnhancedAgiScript {


    private static final Logger logger = LoggerFactory.getLogger(HangupScript.class);



    /* (non-Javadoc)
     * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
     */
    public void service(AgiRequest request, AgiChannel channel) throws AgiException {

        logger.debug("Hangup event: script={}, tag={}",
                request.getParameter("script"),
                request.getParameter("tag"));

    }

}

package com.ideasquefluyen.ivr.dialplan.scripts;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideasquefluyen.ivr.common.script.EnhancedAgiScript;

/**
 * Presents main menu without verbio support. Only accept DTMFs
 *
 * @author ideasquefluyen
 * @since 1.0.0
 *
 */
public class MainMenuDtmfOnlyScript extends EnhancedAgiScript {


    private static final Logger logger = LoggerFactory.getLogger(MainMenuDtmfOnlyScript.class);



    /* (non-Javadoc)
     * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
     */
    public void service(AgiRequest request, AgiChannel channel) throws AgiException {

        logger.debug("Main menu without dtmf service execute");


        Character dtmf = playAudioAndWaitForDtmf("p/MMenuDTMF", "1234567890");


        // nothing was selected
        // or wrong option.
        if(dtmf == null || dtmf.charValue() == '0') {

            playAudio("p/wrongDTMF");

            playAudioAndWaitForRandomDtmf("p/TAgain%d", matched -> {

                if(matched) {

                    nextScript("mainMenuDtmf.agi");

                } else {

                    playAudio("p/wrongDTMF");
                    end();
                }
            });

        } else {

            switch(dtmf.charValue()) {

                case '1':
                    nextScript("flows/apply/mainApply.agi");
                    break;
                case '2':
                    nextScript("flows/verify/mainVerify.agi");
                    break;
                case '3':
                    nextScript("flows/plan/mainPlan.agi");
                    break;
                case '4':
                    nextScript("flows/application/mainApplication.agi");
                    break;
                case '5':
                    nextScript("flows/support/mainSupport.agi");
                    break;
                case '6':
                    nextScript("flows/information/mainInformation.agi");
                    break;
                case '7':
                    nextScript("flows/refer/mainRefer.agi");
                    break;
                case '8':
                    nextScript("flows/letter/mainLetter.agi");
                    break;
                case '9':
                    nextScript("flows/airtime/mainAirtime.agi");
                    break;
            }

        }
    }

}

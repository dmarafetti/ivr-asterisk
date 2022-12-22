package com.ideasquefluyen.ivr.dialplan.scripts;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideasquefluyen.ivr.common.listeners.RecognitionActionListener;
import com.ideasquefluyen.ivr.common.listeners.SelectionActionListener;
import com.ideasquefluyen.ivr.common.script.VerbioSupport;
import com.ideasquefluyen.ivr.common.verbio.Verbio;


/**
 * This script presents the main menu of options
 *
 *
 * @author ideasquefluyen
 * @since 1.0.0
 *
 */
public class MainMenuScript extends VerbioSupport {


    private static final Logger logger = LoggerFactory.getLogger(MainMenuScript.class);


    /*
     * (non-Javadoc)
     * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
     */
    @Override
    public void service(AgiRequest request, AgiChannel channel) throws AgiException {

        logger.debug("Main menu service execute");

        // Play Menu
        playMenu(dtmf -> {

            switch(dtmf) {

                case "-1":
                    playAudio("p/inputEmpty");
                    nextScript("mainMenuDtmf.agi");
                    return;
                case "1":
                    nextScript("flows/apply/mainApply.agi");
                    break;
                case "2":
                    nextScript("flows/verify/mainVerify.agi");
                    break;
                case "3":
                    nextScript("flows/plan/mainPlan.agi");
                    break;
                case "4":
                    nextScript("flows/application/mainApplication.agi");
                    break;
                case "5":
                    nextScript("flows/support/mainSupport.agi");
                    break;
                case "6":
                    nextScript("flows/information/mainInformation.agi");
                    break;
                case "7":
                    nextScript("flows/refer/mainRefer.agi");
                    break;
                case "8":
                    nextScript("flows/letter/mainLetter.agi");
                    break;
                case "9":
                    nextScript("flows/airtime/mainAirtime.agi");
                    break;
                default:
            }
        });

    }



    /**
     * Play the main menu using Verbio
     *
     * @throws AgiException
     */
    private void playMenu(SelectionActionListener<String> c) throws AgiException {

        // Setup Verbio for DTMF recognition and load
        // laguage dictionary
        Verbio verbio = getVerbioInstance().forChannel(getChannel())
                                           .dtmfLenght(1)
                                           .forLanguage(getLanguage())
                                           .withVocabulary("MMenu.bnf");
        playAudio("p/MMenu");

        verbio.rec(new RecognitionActionListener() {

            public void onWords(String result) throws AgiException {

                onDtmf(result);
            }

            public void onDtmf(String dtmf) throws AgiException {

                c.onSelection(dtmf);
            }

            public void onEmpty(String utterance, Integer score) throws AgiException {

                c.onSelection("-1");
            }

            public void onError(String ex) throws AgiException {

                c.onSelection("-1");
            }

        });

        verbio.release();
    }

}

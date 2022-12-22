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
 * The welcome flow
 *
 * @author diego.marafetti
 * @since 1.0.0
 */
public class WelcomeScript extends VerbioSupport {


    private static final Logger logger = LoggerFactory.getLogger(WelcomeScript.class);


    /*
     * (non-Javadoc)
     * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
     */
    @Override
    public void service(AgiRequest request, AgiChannel channel) throws AgiException {

        logger.debug("WelcomeScript service execute");

        askForLanguage(language -> {

            switch(language) {

                case "es":

                    switchToSpanish();
                    break;

                default:

                    switchToEnglish();
            }

        });

        // Play selected language
        playAudio("p/8");

        // Play Quality assurance audio
        playAudio("p/QualityAssurance");

        // Play new option alert
        playAudio("p/NewOptionsAlert");

        // set option
        nextScript("mainMenu.agi");
    }



    /**
     * Ask the user for language selection using either DTMF or speech
     * recognition.
     * DM_LANGUAGE_INPUT
     *
     * @param c
     * @throws AgiException
     */
    private void askForLanguage(SelectionActionListener<String> c) throws AgiException {

        // Setup Verbio for DTMF recognition and load
        // laguage dictionary
        // Set explicit verbio lang because we need to
        // recognize both languages
        Verbio verbio = getVerbioInstance().forChannel(getChannel())
                                             .dtmfLenght(1)
                                             .withVocabulary("language.bnf");

        // Play audio selection in
        // english and spanish
        playAudio("p/2");
        playAudio("p/2es");

        verbio.rec(new RecognitionActionListener() {

            public void onWords(String result) throws AgiException {

                c.onSelection(result);
            }

            public void onDtmf(String dtmf) throws AgiException {

                switch(dtmf) {

                    case "1":
                        c.onSelection("en");
                        break;

                    case "2":
                        c.onSelection("es");
                        break;

                    default:
                        c.onSelection("en");
                }
            }

            public void onEmpty(String utterance, Integer score) throws AgiException {

                c.onSelection("en");
            }

            public void onError(String ex) throws AgiException {

                c.onSelection("en");
            }

        });

        verbio.release();
    }

}

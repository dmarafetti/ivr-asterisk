package com.ideasquefluyen.ivr.dialplan.scripts.flow.apply;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;

import com.ideasquefluyen.ivr.api.service.ZipcodeService;
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
public class MainApplyScript extends VerbioSupport {


    private ZipcodeService zipcodeService;



    /* (non-Javadoc)
     * @see org.asteriskjava.fastagi.AgiScript#service(org.asteriskjava.fastagi.AgiRequest, org.asteriskjava.fastagi.AgiChannel)
     */
    @Override
    public void service(AgiRequest request, AgiChannel channel) throws AgiException {


        // play zipcode
        playAudio("p/NewMinutes");

        // listen option
        askForZipcode(zipcode -> {


            if(zipcode == "-1") {

                nextScript("flows/apply/mainApplyDtmf.agi");

            } else {

                // validate zipcode with external service
                String zipcodeStatus = "";

                switch(zipcodeStatus) {

                    case "invalid":

                        validZipcode(zipcode);
                        break;

                    case "valid-supported":

                        validZipcode(zipcode);
                        break;

                    case "unsupported-available":

                        unsupportedAndAvailableZipcode(zipcode);
                        break;

                    case "unsupported-unavailable":

                        unsupportedAndUnavailableZipcode(zipcode);
                        break;
                }
            }

        });


        // set option


    }



    /**
     *
     * @param c
     * @throws AgiException
     */
    private void askForZipcode(SelectionActionListener<String> c) throws AgiException {


        // Setup Verbio for DTMF recognition and load
        // laguage dictionary
        Verbio verbio = getVerbioInstance().forChannel(getChannel())
                                           .dtmfLenght(5)
                                           .forLanguage(getLanguage())
                                           .withVocabulary("ZipCode.bnf");

        playAudio("p/GetZipCode_01");

        verbio.rec(new RecognitionActionListener() {

            public void onDtmf(String dtmf) throws AgiException {

            }

            public void onWords(String result) throws AgiException {

            }

            public void onEmpty(String utterance, Integer score) throws AgiException {

                c.onSelection("-1");
            }

            public void onError(String ex) throws AgiException {

            }

        });

        verbio.release();
    }




    /**
     *
     * @param zipcode
     */
    private void validZipcode(String zipcode) throws AgiException {

        playAudio("p/Conf_01");

        getChannel().sayDigits(zipcode);

        playAudio("p/Conf_02a");
    }

    /**
     *
     * @param zipcode
     */
    private void invalidZipcode(String zipcode) throws AgiException {

        playAudio("p/InvalidZip_01");

        nextScript("");
    }

    /**
     *
     * @param zipcode
     */
    private void unsupportedAndAvailableZipcode(String zipcode) throws AgiException {


    }

    /**
     *
     * @param zipcode
     */
    private void unsupportedAndUnavailableZipcode(String zipcode) throws AgiException {


    }

}

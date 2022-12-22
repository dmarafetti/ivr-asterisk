/**
 *
 */
package com.ideasquefluyen.ivr.common.listeners;

import org.asteriskjava.fastagi.AgiException;

/**
 *
 *
 * @author dmarafetti
 * @since 1.0.0
 *
 */
public interface RecognitionActionListener {

    /**
     * Has detected a dtmf
     *
     * @param dtmf The detected dtmf
     * @throws AgiException
     */
    void onDtmf(String dtmf) throws AgiException;


    /**
     * Has detected a word
     *
     * @param result The word that has been recognized by Verbio
     * @throws AgiException
     */
    void onWords(String result) throws AgiException;


    /**
     * Either the user has not entered any dtmf or the recognition is
     * under the word threshold
     *
     * @param utterance
     * @param score
     * @throws AgiException
     */
    void onEmpty(String utterance, Integer score) throws AgiException;


    /**
     * An error has ocurred during the recognition
     *
     * @param ex
     * @throws AgiException
     */
    void onError(String ex) throws AgiException;

}

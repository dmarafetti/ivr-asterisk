package com.ideasquefluyen.ivr.common.script;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.BaseAgiScript;

import com.ideasquefluyen.ivr.common.listeners.SelectionActionListener;
import com.ideasquefluyen.ivr.common.util.NumbersUtil;


/**
 * Wraps agi commands with a more natural language.
 *
 * @author diego.marafetti
 * @since 1.0.0
 */
public abstract class EnhancedAgiScript extends BaseAgiScript {



    /**
     * Get channel for current thread. Check wether the channel
     * is available or not.
     *
     */
    @Override
    protected AgiChannel getChannel() {

        return super.getChannel();
    }

    /**
     * Play an audio. Block the execution until the audio finish.
     *
     * @param recordName
     */
    protected void playAudio(String recordName) throws AgiException {

        getChannel().exec("Playback", recordName);
    }

    /**
     * Play an audio and wait for digit. Can be interrumpted. Wrapper of {@link AgiChannel#getOption(String, String, long)}
     *
     * @param recordName
     * @return null if no digit was pressed
     * @throws AgiException
     */
    protected Character playAudioAndWaitForDtmf(String recordName, String escapeDigits) throws AgiException {

        char option = getChannel().getOption(recordName, escapeDigits, 7000);

        if(option == 0x0) return null;

        return Character.valueOf(option);
    }


    /**
     * Play an audio and wait for a random dtmf.
     *
     * @param recordName Use {@link String#format(java.util.Locale, String, Object...)} in order to replace the number.
     * @param c {@link SelectionActionListener}
     * @throws AgiException
     */
    protected void playAudioAndWaitForRandomDtmf(String recordNamePattern, SelectionActionListener<Boolean> c) throws AgiException {

        int randomDigit = NumbersUtil.getRandomDigit();

        String recordName = String.format(recordNamePattern, randomDigit);

        char randomOption = getChannel().getOption(recordName, Integer.toString(randomDigit), 7000);

        c.onSelection(randomOption == Character.forDigit(randomDigit, 10));
    }


    /**
     * Change language to spanish <code>es</code>
     *
     * @param channel
     * @throws AgiException
     */
    protected void switchToSpanish() throws AgiException {

        setVariable("VERBIO_LANG", "es-ar");
        setLanguage("es");
    }

    /**
     * Change language to english <code>en</code>
     *
     * @param channel
     * @throws AgiException
     */
    protected void switchToEnglish() throws AgiException {

        setVariable("VERBIO_LANG", "en-us");
        setLanguage("en");
    }

    /**
     * Set the current language
     *
     * @param channel
     * @param language
     * @throws AgiException
     */
    protected void setLanguage(String language) throws AgiException {

        setVariable("CHANNEL(language)", language);
    }

    /**
     * Get the selected language
     *
     * @return
     * @throws AgiException
     */
    protected String getLanguage()  throws AgiException {

        return getVariable("CHANNEL(language)");
    }

    /**
     * Get a variable as {@link Integer}
     *
     * @param name
     * @return
     * @throws AgiException
     */
    protected Integer getVariableInt(String name) throws AgiException {

        return new Integer(Integer.parseInt(getChannel().getVariable(name)));
    }

    /**
     * Get variable as {@link String}
     *
     * @param name
     * @return
     * @throws AgiException
     */
    protected String getVariableString(String name) throws AgiException {

        return getChannel().getVariable(name);
    }

    /**
     * Next agi script to be executed. Doesn't prevent the current thread execution.
     *
     * @param scriptName
     * @throws AgiException
     */
    protected void nextScript(String scriptName) throws AgiException {

        getChannel().setVariable("JAVAAPP", scriptName);
    }

    /**
     * End the IVR execution. Doesn't prevent the current thread execution.
     *
     * @param scriptName
     * @throws AgiException
     */
    protected void end() throws AgiException {

        getChannel().setVariable("JAVAAPP", "ended");
    }

}

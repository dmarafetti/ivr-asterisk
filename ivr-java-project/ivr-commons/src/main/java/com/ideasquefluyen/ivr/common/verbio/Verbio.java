package com.ideasquefluyen.ivr.common.verbio;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideasquefluyen.ivr.common.listeners.RecognitionActionListener;

/**
 * Wraps all Verbio functionality
 *
 * @author dmarafetti
 * @since 1.0.0
 *
 */
public class Verbio {

    private static final Logger logger = LoggerFactory.getLogger(Verbio.class);

    /** current agi channel */
    private AgiChannel channel;

    /** current language */
    private String playbackLanguage = "";

    /** verbio vocabulary language */
    private String defaultVerbioLanguage;

    /** number of seconds verbio opens the channel for recording */
    private String recordingTime;

    /** threshold where we accept a word as accepted */
    private Integer wordThreshold;


    /**
     * Default constructor
     *
     */
    public Verbio() {}


    /**
     * Construct with default language
     *
     * @param defaultVerbioLanguage
     * @param recordingTime
     * @param wordThreshold
     */
    public Verbio(String defaultVerbioLanguage,
                  String recordingTime,
                  Integer wordThreshold) {

        this.defaultVerbioLanguage = defaultVerbioLanguage;
        this.recordingTime = recordingTime;
        this.wordThreshold = wordThreshold;
    }


    /**
     *
     *
     * @param channel
     * @return
     */
    public Verbio forChannel(AgiChannel channel) {

        this.channel = channel;

        return this;
    }

    /**
     *
     * @param lenght
     * @return
     * @throws AgiException
     */
    public Verbio dtmfLenght(Integer lenght) throws AgiException {

        channel.setVariable("VERBIO_DTMF_MAXLEN", lenght.toString());

        return this;
    }

    /**
     *
     * Set the current language
     *
     * @param language
     * @return
     * @throws AgiException
     */
    public Verbio forLanguage(String language)  throws AgiException {

        this.playbackLanguage = language + "/";

        return this;
    }

    /**
     * Match with <code>VerbioLoadVcb(gram_file|gram_type[|config][|lang][|options])</code>
     *
     * @param grammarFile
     * @return
     * @throws AgiException
     */
    public Verbio withVocabulary(String grammarFile) throws AgiException {

        return this.withVocabulary(grammarFile, getVerbioLanguage());
    }


    /**
     * Match with <code>VerbioLoadVcb(gram_file|gram_type[|config][|lang][|options])</code>
     *
     * @param grammarFile
     * @return
     * @throws AgiException
     */
    public Verbio withVocabulary(String grammarFile, String verbioLang) throws AgiException {

        channel.exec("VerbioLoadVcb", this.playbackLanguage + grammarFile, "ABNF", verbioLang, verbioLang, "v");

        return this;
    }


    /**
     * Match with <code>VerbioRec([|config][|lang][|initsil][|maxsil][|abs_timeout][|options])</code>
     *
     * @return
     * @throws AgiException
     */
    public void rec(RecognitionActionListener c) throws AgiException {


        int ret = channel.exec("VerbioRec", getVerbioLanguage(), getVerbioLanguage(), "", "", this.recordingTime, "vbd");

        if(2 == ret) {

            c.onError("Verbio recording application was not found");
            return;
        }

        try {

            Recognition recognition = new Recognition(channel);

            if(recognition.hasError()) {

                c.onError("Verbio server might be unavailable.");

                return;
            }

            if(recognition.isDtmf()) {

                logger.debug("dtmf={} detected",recognition.getLastDtmf());

                c.onDtmf(recognition.getLastDtmf());

                return;
            }

            if(recognition.hasWords()) {

                logger.debug("word={} detected", recognition.getDetectedWord());

                c.onWords(recognition.getDetectedWord());

            } else {

                logger.debug("Nothing detected");

                c.onEmpty(recognition.getLastUtterance(), recognition.getLastScore());
            }

        } catch (AgiException ex) {

            c.onError(ex.getMessage());
        }


    }


    /**
     * Get the current language for recognition
     *
     */
    public String getVerbioLanguage() throws AgiException {

        String verbioLang = channel.getVariable("VERBIO_LANG");

        return (verbioLang == null) ? this.defaultVerbioLanguage : verbioLang;
    }


    /**
     * Free Verbio resources (memory and licences).
     *
     */
    public void release() throws AgiException {

        channel.exec("VerbioUnloadVcb", "-1", "", "", "v");

        this.channel = null;
    }




    /**
     * This class represents a Verbio recognition result
     *
     * @author dmarafetti
     * @since 1.0.0
     *
     */
    private final class Recognition {

        private Boolean hasError = false;
        private Boolean dtmfDetected;
        private String  dtmfResult;
        private Integer words;
        private Integer score;
        private String  result;
        private String  utterance;

        public Recognition(AgiChannel channel) throws AgiException {

            String rawWordsVar = channel.getVariable("VASR_WORDS");
            String rawDtfmVar  = channel.getVariable("VDTMF_DETECTED");

            if(rawWordsVar == null || rawDtfmVar == null) {

                this.hasError = true;
                logger.debug("verbio detect: error");

            } else {

                this.dtmfDetected   = Boolean.parseBoolean(rawDtfmVar);
                this.dtmfResult     = channel.getVariable("VDTMF_RESULT");
                this.utterance      = channel.getVariable("VASR_UTTERANCE0");
                this.result         = channel.getVariable("VASR_RESULT");
                this.words          = Integer.parseInt(rawWordsVar);
                this.score          = Integer.parseInt(channel.getVariable("VASR_SCORE0"));

                logger.debug("verbio detect: dtmfDetected={}, dtmfResult={}, words={}, utterance={}, score={}, result={}",
                        dtmfDetected, dtmfResult, words, utterance, score, result);
            }
        }


        /**
         * There was an error during recognition ?
         *
         * @return
         */
        public Boolean hasError() {

            return this.hasError;
        }


        /**
         * Has dtmf been detected?
         *
         * @return
         * @throws AgiException
         */
        public Boolean isDtmf() throws AgiException {

            return this.dtmfDetected;
        }

        /**
         * Get last dtmf selected
         *
         * @return
         * @throws AgiException
         */
        public String getLastDtmf() throws AgiException {

            return this.dtmfResult;
        }

        /**
         * Has any word been detected?
         *
         * @return
         * @throws AgiException
         */
        public Boolean hasWords() throws AgiException {

            // less than 40 percent of probability
            // set english as default
            return (this.words > 0 && this.score >= wordThreshold);
        }

        /**
         * Get last detected word
         *
         * @return
         * @throws AgiException
         */
        public String getDetectedWord() throws AgiException {

            return this.result;
        }

        /**
         * Get last utterance
         *
         * @return
         * @throws AgiException
         */
        public String getLastUtterance() throws AgiException {

            return this.utterance;
        }

        /**
         * Get last score
         *
         * @return
         * @throws AgiException
         */
        public Integer getLastScore() throws AgiException {

            return this.score;
        }

    }
}

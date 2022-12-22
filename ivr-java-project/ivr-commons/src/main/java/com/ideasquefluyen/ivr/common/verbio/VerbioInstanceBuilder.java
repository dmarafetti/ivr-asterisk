package com.ideasquefluyen.ivr.common.verbio;

/**
 * Factory builder of {@link Verbio} instances
 *
 *
 * @author dmarafetti
 * @since 1.0.0
 *
 */
public final class VerbioInstanceBuilder {


    private String defaultVerbioLanguage;

    private String recordingTime;

    private Integer wordThreshold;



    public Verbio getInstance() {

        return new Verbio(defaultVerbioLanguage,
                          recordingTime,
                          wordThreshold);
    }



    /**
     * @param defaultVerbioLanguage the defaultVerbioLanguage to set
     */
    public void setDefaultVerbioLanguage(String defaultVerbioLanguage) {
        this.defaultVerbioLanguage = defaultVerbioLanguage;
    }

    /**
     * @param recordingTime the recordingTime to set
     */
    public void setRecordingTime(String recordingTime) {
        this.recordingTime = recordingTime;
    }

    /**
     * @param wordThreshold the wordThreshold to set
     */
    public void setWordThreshold(Integer wordThreshold) {
        this.wordThreshold = wordThreshold;
    }

}

package com.ideasquefluyen.ivr.common.verbio;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.ideasquefluyen.ivr.common.listeners.RecognitionActionListener;

/**
 *
 * Unit test for {@link Verbio} class
 *
 * @author dmarafetti
 * @since 1.0.0
 *
 */
public class VerbioTest {



    @Test
    public void testDTMFRecognition() throws AgiException {

        Verbio verbio = getMockedVerbioDTMFInstance();

        verbio.rec(new RecognitionActionListener() {

            public void onWords(String result) throws AgiException {

                Assert.fail("Should detect a dtmf");
            }

            public void onEmpty(String utterance, Integer score) throws AgiException {

                Assert.fail("Should detect a dtmf");
            }

            public void onDtmf(String dtmf) throws AgiException {

                Assert.assertTrue(dtmf.equals("1"));
            }

            public void onError(String ex) throws AgiException {

                Assert.fail("Should detect a dtmf");
            }
        });

    }


    @Test
    public void testWordRecognition() throws AgiException {

        Verbio verbio = getMockedVerbioWordInstance();

        verbio.rec(new RecognitionActionListener() {

            public void onWords(String result) throws AgiException {

                Assert.assertTrue(result.equals("es"));
            }

            public void onEmpty(String utterance, Integer score) throws AgiException {

                Assert.fail("Should detect a word");
            }

            public void onDtmf(String dtmf) throws AgiException {

                Assert.fail("Should detect a word");
            }

            public void onError(String ex) throws AgiException {

                Assert.fail("Should detect a word");
            }
        });

    }


    @Test
    public void testWordThresholdRecognition() throws AgiException {

        Verbio verbio = getMockedVerbioEmptyInstance();

        verbio.rec(new RecognitionActionListener() {

            public void onWords(String result) throws AgiException {

                Assert.fail("Recognition should be under accepted threshold.");
            }

            public void onEmpty(String utterance, Integer score) throws AgiException {

                Assert.assertTrue(utterance.equals("espaniol"));
                Assert.assertTrue(score < 40);
            }

            public void onDtmf(String dtmf) throws AgiException {

                Assert.fail("Recognition should be under accepted threshold.");
            }

            public void onError(String ex) throws AgiException {

                Assert.fail("Recognition should be under accepted threshold.");
            }

        });

    }





    private Verbio getMockedVerbioDTMFInstance() {

        AgiChannel agiChannel = Mockito.mock(AgiChannel.class);

        try {

            Mockito.when(agiChannel.getVariable("VERBIO_LANG")).thenReturn("es-ar");
            Mockito.when(agiChannel.getVariable("VDTMF_DETECTED")).thenReturn("TRUE");
            Mockito.when(agiChannel.getVariable("VDTMF_RESULT")).thenReturn("1");
            Mockito.when(agiChannel.getVariable("VASR_UTTERANCE0")).thenReturn("espaniol");
            Mockito.when(agiChannel.getVariable("VASR_RESULT")).thenReturn("es");
            Mockito.when(agiChannel.getVariable("VASR_WORDS")).thenReturn("1");
            Mockito.when(agiChannel.getVariable("VASR_SCORE0")).thenReturn("80");
            Mockito.when(agiChannel.exec("VerbioRec", "es-ar", "es-ar", "", "", "1000", "vbd")).thenReturn(1);

        } catch (AgiException ex) {}

        VerbioInstanceBuilder builder = new VerbioInstanceBuilder();
        builder.setDefaultVerbioLanguage("es-ar");
        builder.setRecordingTime("1000");
        builder.setWordThreshold(40);

        Verbio verbio = builder.getInstance();
        return verbio.forChannel(agiChannel);
    }



    private Verbio getMockedVerbioWordInstance() {

        AgiChannel agiChannel = Mockito.mock(AgiChannel.class);

        try {

            Mockito.when(agiChannel.getVariable("VERBIO_LANG")).thenReturn("es-ar");
            Mockito.when(agiChannel.getVariable("VDTMF_DETECTED")).thenReturn("FALSE");
            Mockito.when(agiChannel.getVariable("VDTMF_RESULT")).thenReturn("NULL");
            Mockito.when(agiChannel.getVariable("VASR_UTTERANCE0")).thenReturn("espaniol");
            Mockito.when(agiChannel.getVariable("VASR_RESULT")).thenReturn("es");
            Mockito.when(agiChannel.getVariable("VASR_WORDS")).thenReturn("1");
            Mockito.when(agiChannel.getVariable("VASR_SCORE0")).thenReturn("80");
            Mockito.when(agiChannel.exec("VerbioRec", "es-ar", "es-ar", "", "", "1000", "vbd")).thenReturn(1);

        } catch (AgiException ex) {}

        VerbioInstanceBuilder builder = new VerbioInstanceBuilder();
        builder.setDefaultVerbioLanguage("es-ar");
        builder.setRecordingTime("1000");
        builder.setWordThreshold(40);

        Verbio verbio = builder.getInstance();
        return verbio.forChannel(agiChannel);
    }


    private Verbio getMockedVerbioEmptyInstance() {

        AgiChannel agiChannel = Mockito.mock(AgiChannel.class);

        try {

            Mockito.when(agiChannel.getVariable("VERBIO_LANG")).thenReturn("es-ar");
            Mockito.when(agiChannel.getVariable("VDTMF_DETECTED")).thenReturn("FALSE");
            Mockito.when(agiChannel.getVariable("VDTMF_RESULT")).thenReturn("NULL");
            Mockito.when(agiChannel.getVariable("VASR_UTTERANCE0")).thenReturn("espaniol");
            Mockito.when(agiChannel.getVariable("VASR_RESULT")).thenReturn("NULL");
            Mockito.when(agiChannel.getVariable("VASR_WORDS")).thenReturn("0");
            Mockito.when(agiChannel.getVariable("VASR_SCORE0")).thenReturn("15");
            Mockito.when(agiChannel.exec("VerbioRec", "es-ar", "es-ar", "", "", "1000", "vbd")).thenReturn(1);

        } catch (AgiException ex) {}

        VerbioInstanceBuilder builder = new VerbioInstanceBuilder();
        builder.setDefaultVerbioLanguage("es-ar");
        builder.setRecordingTime("1000");
        builder.setWordThreshold(40);

        Verbio verbio = builder.getInstance();
        return verbio.forChannel(agiChannel);
    }

}

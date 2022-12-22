package com.ideasquefluyen.ivr.common.util;

import java.util.Random;

/**
 * Some utility methods for numbers manipulation.
 *
 *
 * @author dmarafetti
 * @since 1.0.0
 *
 */
public final class NumbersUtil {

    /** Random number generator */
    private static Random random = new Random();


    /**
     *
     * @return
     */
    public static int getRandomDigit() {

        return random.nextInt(10);
    }



}

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
@FunctionalInterface
public interface SelectionActionListener<T> {


    void onSelection(T option) throws AgiException;


}

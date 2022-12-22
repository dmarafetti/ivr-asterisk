/**
 *
 */
package com.ideasquefluyen.ivr.common.script;

import org.springframework.beans.factory.annotation.Autowired;

import com.ideasquefluyen.ivr.common.verbio.Verbio;
import com.ideasquefluyen.ivr.common.verbio.VerbioInstanceBuilder;

/**
 * Add Verbio support
 *
 *
 * @author dmarafetti
 * @since 1.0.0
 *
 */
public abstract class VerbioSupport extends EnhancedAgiScript {


    /** Build verbio recognition requests */
    @Autowired
    private VerbioInstanceBuilder verbioBuilder;



    /**
     * Get a new Verbio instance for ready for recognition
     *
     * @return
     */
    protected Verbio getVerbioInstance() {

        if(verbioBuilder == null) throw new RuntimeException("None VerbioBuilder has been registered");

        return verbioBuilder.getInstance();
    }

}

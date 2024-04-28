package threading;

/**
 * RunInThread and other accompanying files are licensed under the MIT
 * license.  Copyright (C) Frank Appel 2016-2021. All rights reserved
 */
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;


public class RunInThreadRule implements TestRule {

    @Override
    public Statement apply( Statement base, Description description ) {
        Statement result = base;
        RunInThread annotation = description.getAnnotation( RunInThread.class );
        if( annotation != null ) {
            result = new RunInThreadStatement( base );
        }
        return result;
    }
}
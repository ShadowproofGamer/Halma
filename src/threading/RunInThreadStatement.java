package threading;

/**
 * RunInThread and other accompanying files are licensed under the MIT
 * license.  Copyright (C) Frank Appel 2016-2021. All rights reserved
 */
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.runners.model.Statement;

class RunInThreadStatement extends Statement {

    private final Statement baseStatement;
    private Future<?> future;
    private volatile Throwable throwable;

    RunInThreadStatement( Statement baseStatement ) {
        this.baseStatement = baseStatement;
    }

    @Override
    public void evaluate() throws Throwable {
        ExecutorService executorService = runInThread();
        try {
            waitTillFinished();
        } finally {
            executorService.shutdown();
        }
        rethrowAssertionsAndErrors();
    }

    private ExecutorService runInThread() {
        ExecutorService result = Executors.newSingleThreadExecutor();
        future = result.submit( new Runnable() {
            @Override
            public void run() {
                try {
                    baseStatement.evaluate();
                } catch( Throwable throwable ) {
                    RunInThreadStatement.this.throwable = throwable;
                }
            }
        } );
        return result;
    }

    private void waitTillFinished() {
        try {
            future.get();
        } catch( ExecutionException shouldNotHappen ) {
            throw new IllegalStateException( shouldNotHappen );
        } catch( InterruptedException shouldNotHappen ) {
            throw new IllegalStateException( shouldNotHappen );
        }
    }

    private void rethrowAssertionsAndErrors() throws Throwable {
        if( throwable != null ) {
            throw throwable;
        }
    }
}

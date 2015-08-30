package com.gschw.ljwc.lj.ljagent.managed;

import com.gschw.ljwc.lj.ljagent.core.Processor;
import com.gschw.ljwc.lj.ljagent.core.ProcessorFactory;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Managed wrapper for {@link Processor}. Also, serves as scheduler for processors.
 */
public class ProcessorManager implements Managed {
    private static Logger logger = LoggerFactory.getLogger(ProcessorManager.class);

    private ScheduledExecutorService executorService;

    private ProcessorFactory processorFactory;

    private ProcessorManagerParameters parameters;

    public ProcessorManager(ProcessorFactory processorFactory, ProcessorManagerParameters parameters) {
        this.processorFactory = processorFactory;
        this.parameters = parameters;
    }

    @Override
    public void start() throws Exception {
        int n = parameters.getNumberOfProcesorQueues();
        executorService = Executors.newScheduledThreadPool(n);

        for (int i = 0; i < n; i++) {
            Processor processor = processorFactory.createProcessor();
            Runnable rIterate = () -> processor.iterate();
            final ScheduledFuture<?> iterateFuture =
                    executorService.scheduleWithFixedDelay(
                            rIterate,
                            parameters.getQueueTimerRateMsec(),
                            parameters.getQueueTimerRateMsec(),
                            TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void stop() throws Exception {
        logger.info("Stopping...");
        executorService.shutdown();
        try {
            boolean b = executorService.awaitTermination(parameters.getShutdownTimeoutMsec(), TimeUnit.MILLISECONDS);
            if (!b) {
                logger.info("Did not wait until awaitTermination");
            }

            logger.info("Stopped");
        } catch(InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}

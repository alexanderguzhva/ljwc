package com.gschw.ljwc.lj.ljcalendaragent.managed;

import com.gschw.ljwc.lj.ljcalendaragent.calendar.Processor;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by nop on 8/22/15.
 */
public class ProcessorManager implements Managed {
    private static Logger logger = LoggerFactory.getLogger(ProcessorManager.class);

    private ScheduledExecutorService executorService;

    private Processor processor;

    private ProcessorManagerParameters parameters;

    public ProcessorManager(Processor processor, ProcessorManagerParameters parameters) {
        this.processor = processor;
        this.parameters = parameters;
    }

    @Override
    public void start() throws Exception {
        executorService = Executors.newScheduledThreadPool(1);

        Runnable rIterate = () -> processor.iterate();
        final ScheduledFuture<?> iterateFuture =
                executorService.scheduleWithFixedDelay(
                        rIterate,
                        parameters.getQueueTimerRateMsec(),
                        parameters.getQueueTimerRateMsec(),
                        TimeUnit.MILLISECONDS);
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

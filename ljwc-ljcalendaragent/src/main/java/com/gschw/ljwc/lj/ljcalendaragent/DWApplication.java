package com.gschw.ljwc.lj.ljcalendaragent;

import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClient;
import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClientParameters;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import com.gschw.ljwc.lj.ljcalendaragent.calendar.Processor;
import com.gschw.ljwc.lj.ljcalendaragent.calendar.ProcessorParameters;
import com.gschw.ljwc.lj.ljscheduler.client.ILJCalendarTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJCalendarTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJCalendarTaskClientParameters;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;

/**
 * Created by nop on 8/12/15.
 */
public class DWApplication extends Application<DWConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwc-ljcalendaragent";
    }

    @Override
    public void initialize(Bootstrap<DWConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor()
                )
        );
    }

    @Override
    public void run(DWConfiguration configuration, Environment environment) throws Exception {
        final Client client = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());

        ////
        DGDownloadTaskClientParameters downloadTaskClientParameters =
            configuration.getDownloadTaskClientParameters();
        IDGDownloadTaskClient downloadTaskClient =
                new DGDownloadTaskClient(client, downloadTaskClientParameters);

        LJCalendarTaskClientParameters calendarTaskClientParameters =
                configuration.getCalendarTaskClientParameters();
        ILJCalendarTaskClient calendarTaskClient =
                new LJCalendarTaskClient(client, calendarTaskClientParameters);

        ProcessorParameters processorParameters =
                configuration.getProcessorParameters();
        Processor processor =
                new Processor(
                    downloadTaskClient,
                    calendarTaskClient,
                    processorParameters);

        environment.jersey().register(processor);
    }

}

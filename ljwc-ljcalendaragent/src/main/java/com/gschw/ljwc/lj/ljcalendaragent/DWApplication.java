package com.gschw.ljwc.lj.ljcalendaragent;

import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClient;
import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClientParameters;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import com.gschw.ljwc.lj.ljcalendaragent.calendar.Processor;
import com.gschw.ljwc.lj.ljcalendaragent.calendar.ProcessorParameters;
import com.gschw.ljwc.lj.ljcalendaragent.managed.ProcessorManager;
import com.gschw.ljwc.lj.ljcalendaragent.managed.ProcessorManagerParameters;
import com.gschw.ljwc.lj.ljcalendaragent.resources.ControllerResource;
import com.gschw.ljwc.lj.ljscheduler.client.ILJCalendarTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJCalendarTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJTaskClientParameters;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.client.ClientProperties;
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

        client.property(ClientProperties.CONNECT_TIMEOUT, 10000);
        client.property(ClientProperties.READ_TIMEOUT, 10000);

        ////
        DGDownloadTaskClientParameters downloadTaskClientParameters =
            configuration.getDownloadTaskClientParameters();
        IDGDownloadTaskClient downloadTaskClient =
                new DGDownloadTaskClient(client, downloadTaskClientParameters);

        LJTaskClientParameters calendarTaskClientParameters =
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

//        ControllerResource controllerResource =
//                new ControllerResource(processor);
//        environment.jersey().register(controllerResource);
        ProcessorManagerParameters processorManagerParameters =
                configuration.getProcessorManagerParameters();
        ProcessorManager processorManager =
                new ProcessorManager(processor, processorManagerParameters);

        environment.lifecycle().manage(processorManager);
    }

}

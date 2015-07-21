package com.gschw.ljwc.lj.ljagent;

import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClient;
import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClientParameters;
import com.gschw.ljwc.lj.ljagent.core.Processor;
import com.gschw.ljwc.lj.ljagent.core.ProcessorParameters;
import com.gschw.ljwc.lj.ljagent.resources.ControllerResource;
import com.gschw.ljwc.lj.ljscheduler.client.LJDownloadTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJDownloadTaskClientParameters;
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
 * Created by nop on 7/20/15.
 */
public class DWApplication extends Application<DWConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwc-ljagent";
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
    public void run(DWConfiguration configuration,
                    Environment environment) {
        final Client client = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());

        ////
        DGDownloadTaskClientParameters dgDownloadTaskClientParameters =
                configuration.getDownloadTaskClientParameters();
        DGDownloadTaskClient dgDownloadTaskClient =
                new DGDownloadTaskClient(client, dgDownloadTaskClientParameters);

        LJDownloadTaskClientParameters ljDownloadTaskClientParameters =
                configuration.getLjDownloadTaskClientParameters();
        LJDownloadTaskClient ljDownloadTaskClient =
                new LJDownloadTaskClient(client, ljDownloadTaskClientParameters);

        final ProcessorParameters processorParameters =
                configuration.getProcessorParameters();
        Processor processor = new Processor(
                dgDownloadTaskClient,
                ljDownloadTaskClient,
                processorParameters);


        ControllerResource controllerResource =
                new ControllerResource(processor);

        environment.jersey().register(controllerResource);
    }
}
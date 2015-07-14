package com.gschw.ljwc.grabber.datagrabber;

import com.gschw.ljwc.grabber.datagrabber.core.GrabberParameters;
import com.gschw.ljwc.grabber.datagrabber.core.GrabbersKeeper;
import com.gschw.ljwc.grabber.datagrabber.resources.DGDownloadTaskResource;

import com.gschw.ljwc.uploader.client.DGUploaderClient;
import com.gschw.ljwc.uploader.client.DGUploaderClientParameters;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import org.glassfish.jersey.client.ClientProperties;


/**
 * Created by nop on 6/29/15.
 */
public class DWApplication extends Application<DWConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwc-datagrabber";
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

        client.property(ClientProperties.CONNECT_TIMEOUT, 10000);
        client.property(ClientProperties.READ_TIMEOUT, 10000);

        ////
        DGUploaderClientParameters uploaderClientParameters = configuration.getUploaderClientParameters();
        DGUploaderClient uploaderClient = new DGUploaderClient(client, uploaderClientParameters);

        ////
        GrabbersKeeper keeper = new GrabbersKeeper(
                configuration.getGrabbersKeeperParameters(),
                configuration.getGrabberParameters());

        ////
        DGDownloadTaskResource downloadTaskResource =
                new DGDownloadTaskResource(keeper, uploaderClient);

        environment.jersey().register(downloadTaskResource);
    }
}

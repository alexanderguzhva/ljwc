package com.gschw.ljwc.lj.executor;

import com.gschw.ljwc.lj.executor.core.X;
import com.gschw.ljwc.lj.executor.resources.DownloadResource;
import com.gschw.ljwc.lj.ljscheduler.client.LJCalendarTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJSinglePageTaskClient;
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
 * Created by hadoop on 8/21/15.
 */
public class DWApplication extends Application<DWConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwc-ljexecutor";
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

        client.property(ClientProperties.CONNECT_TIMEOUT, 1000000);
        client.property(ClientProperties.READ_TIMEOUT, 1000000);

        //
        LJTaskClientParameters ljSinglePageClientParameters =
                configuration.getLjSinglePageClientParameters();
        LJSinglePageTaskClient singlePageTaskClient =
                new LJSinglePageTaskClient(client, ljSinglePageClientParameters);

        LJTaskClientParameters ljCalendarClientParameters =
                configuration.getLjCalendarClientParameters();
        LJCalendarTaskClient calendarTaskClient =
                new LJCalendarTaskClient(client, ljCalendarClientParameters);


        X x = new X(calendarTaskClient, singlePageTaskClient);
        DownloadResource downloadResource = new DownloadResource(x);

        environment.jersey().register(downloadResource);
    }
}

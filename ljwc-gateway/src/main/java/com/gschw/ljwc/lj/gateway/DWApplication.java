package com.gschw.ljwc.lj.gateway;

import com.gschw.ljwc.lj.gateway.servlet.GatewayServlet;
import com.gschw.ljwc.lj.gateway.servlet.GatewayServletParameters;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloader;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloaderParameters;
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
 * Created by nop on 8/22/15.
 */
public class DWApplication extends Application<DWConfiguration>{

    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwc-ljgateway";
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


        SimpleDownloaderParameters simpleDownloaderParameters =
                configuration.getSimpleDownloaderParameters();
        SimpleDownloader simpleDownloader =
                new SimpleDownloader(
                        client,
                        simpleDownloaderParameters);

        GatewayServletParameters gatewayServletParameters =
                configuration.getGatewayServletParameters();

        GatewayServlet gatewayServlet =
                new GatewayServlet(
                        simpleDownloader,
                        gatewayServletParameters);
        environment.servlets()
                .addServlet("gateway", gatewayServlet)
                .addMapping(
                        String.format("%s*", gatewayServletParameters.getRootUrl()));
    }
}

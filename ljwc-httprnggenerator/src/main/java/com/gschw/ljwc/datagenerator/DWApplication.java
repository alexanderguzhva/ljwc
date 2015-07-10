package com.gschw.ljwc.datagenerator;

import com.gschw.ljwc.datagenerator.core.DataGenerator;
import com.gschw.ljwc.datagenerator.core.DataGeneratorParameters;
import com.gschw.ljwc.datagenerator.core.GeneratorResource;

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
 * Created by nop on 7/10/15.
 */
public class DWApplication extends Application<DWConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwc-httprnggen";
    }

    @Override
    public void initialize(Bootstrap<DWConfiguration> bootstrap) {
        //bootstrap.addBundle(discoveryBundle);
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
        DataGeneratorParameters dataGeneratorParameters = configuration.getDataGeneratorParameters();
        DataGenerator generator = new DataGenerator(dataGeneratorParameters);
        GeneratorResource generatorResource = new GeneratorResource(generator);
        environment.jersey().register(generatorResource);
    }


}

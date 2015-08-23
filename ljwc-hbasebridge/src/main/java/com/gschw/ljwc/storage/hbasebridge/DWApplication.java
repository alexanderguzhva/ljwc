package com.gschw.ljwc.storage.hbasebridge;

import com.gschw.ljwc.storage.hbasebridge.core.HBaseDB;
import com.gschw.ljwc.storage.hbasebridge.core.HBaseDBSettings;
import com.gschw.ljwc.storage.hbasebridge.resources.DBResource;
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
 * Created by nop on 8/23/15.
 */
public class DWApplication extends Application<DWConfiguration> {
    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwc-hbasebridge";
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

        HBaseDBSettings hBaseDBSettings = configuration.getHbaseDBSettings();
        HBaseDB hBaseDB = new HBaseDB(client, hBaseDBSettings);

        DBResource dbResource = new DBResource(hBaseDB);
        environment.jersey().register(dbResource);

    }
}

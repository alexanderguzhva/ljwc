package com.gschw.ljwc.lj.ljcalendaragent;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nop on 8/12/15.
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
    public void run(DWConfiguration configuration, Environment environment) throws Exception {

    }

}

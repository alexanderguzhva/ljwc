package com.gschw.ljwc.html.htmlparser;

import com.gschw.ljwc.html.htmlparser.parse.ParserResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

/**
 * Created by nop on 6/29/15.
 */
public class DWApplication extends Application<DWConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwc-htmparser";
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
        ParserResource parserResource = new ParserResource();
        environment.jersey().register(parserResource);
    }

}

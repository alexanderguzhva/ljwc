package com.gschw.ljwc.lj.ljscheduler;

import com.gschw.ljwc.lj.ljscheduler.pscheduler.CalendarKeeper;
import com.gschw.ljwc.lj.ljscheduler.pscheduler.SinglePageKeeper;
import com.gschw.ljwc.lj.ljscheduler.resources.CalendarResource;
import com.gschw.ljwc.lj.ljscheduler.resources.LJSinglePageResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nop on 7/6/15.
 */
public class DWApplication extends Application<DWConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwc-ljscheduler";
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
        ////
        CalendarKeeper calendarKeeper = new CalendarKeeper();
        CalendarResource calendarResource = new CalendarResource(calendarKeeper);
        environment.jersey().register(calendarResource);

        ////
        SinglePageKeeper singlePageKeeper = new SinglePageKeeper();
        LJSinglePageResource singlePageResource = new LJSinglePageResource(singlePageKeeper);
        environment.jersey().register(singlePageResource);
    }
}
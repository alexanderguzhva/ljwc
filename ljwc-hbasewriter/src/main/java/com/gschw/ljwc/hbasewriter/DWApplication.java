package com.gschw.ljwc.hbasewriter;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nop on 6/22/15.
 */
public class DWApplication extends Application<DWConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    @Override
    public String getName() {
        return "ljwriter";
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
                    Environment environment) throws URISyntaxException {

        final Client client = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());

        client.property(ClientProperties.CONNECT_TIMEOUT, 10000);
        client.property(ClientProperties.READ_TIMEOUT, 10000);


        ////
        HBaseSettings hBaseSettings = configuration.getHBaseSettings();
        logger.info("hbase service URI: {}", hBaseSettings.getRESTServiceURI());


        HBaseWriter hBaseWriter = new HBaseWriter(client, hBaseSettings);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        PortalResource portalResource = new PortalResource(executorService, hBaseWriter);
        environment.jersey().register(portalResource);
    }
}
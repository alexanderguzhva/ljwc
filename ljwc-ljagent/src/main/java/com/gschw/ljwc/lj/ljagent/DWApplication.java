package com.gschw.ljwc.lj.ljagent;

import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClient;
import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClientParameters;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import com.gschw.ljwc.html.htmlparser.client.HTMLParserClient;
import com.gschw.ljwc.html.htmlparser.client.HTMLParserClientParameters;
import com.gschw.ljwc.html.htmlparser.client.IHTMLParserClient;
import com.gschw.ljwc.lj.ljagent.core.Processor;
import com.gschw.ljwc.lj.ljagent.core.ProcessorFactory;
import com.gschw.ljwc.lj.ljagent.core.ProcessorParameters;
import com.gschw.ljwc.lj.ljagent.managed.ProcessorManager;
import com.gschw.ljwc.lj.ljagent.managed.ProcessorManagerParameters;
import com.gschw.ljwc.lj.ljagent.resources.ControllerResource;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloader;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloaderParameters;
import com.gschw.ljwc.lj.ljscheduler.client.ILJSinglePageTaskClient;
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
        JerseyClientBuilder jerseyClientBuilder = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration());


////
//        DGDownloadTaskClientParameters dgDownloadTaskClientParameters =
//                configuration.getDownloadTaskClientParameters();
//        IDGDownloadTaskClient dgDownloadTaskClient =
//                new DGDownloadTaskClient(client, dgDownloadTaskClientParameters);
//
//        LJTaskClientParameters ljSinglePageTaskClientParameters =
//                configuration.getLjSinglePageTaskClientParameters();
//        ILJSinglePageTaskClient ljSinglePageTaskClient =
//                new LJSinglePageTaskClient(client, ljSinglePageTaskClientParameters);
//
//        HTMLParserClientParameters htmlParserClientParameters =
//                configuration.getHtmlParserClientParameters();
//        IHTMLParserClient htmlParserClient =
//                new HTMLParserClient(client, htmlParserClientParameters);
//
//        SimpleDownloaderParameters simpleDownloaderParameters =
//                configuration.getSimpleDownloaderParameters();
//        SimpleDownloader simpleDownloader =
//                new SimpleDownloader(client, simpleDownloaderParameters);

        final ProcessorParameters processorParameters =
                configuration.getProcessorParameters();
        ProcessorFactory processorFactory = new ProcessorFactory(
                configuration.getDownloadTaskClientParameters(),
                configuration.getLjSinglePageTaskClientParameters(),
                configuration.getHtmlParserClientParameters(),
                configuration.getSimpleDownloaderParameters(),
                processorParameters,
                jerseyClientBuilder,
                getName());

//        ControllerResource controllerResource =
//                new ControllerResource(processor);
//        environment.jersey().register(controllerResource);

        ProcessorManagerParameters processorManagerParameters =
                configuration.getProcessorManagerParameters();
        ProcessorManager processorManager =
                new ProcessorManager(processorFactory, processorManagerParameters);

        environment.lifecycle().manage(processorManager);

    }
}
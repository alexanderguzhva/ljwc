package com.gschw.ljwc.lj.ljagent.core;

import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClient;
import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClientParameters;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import com.gschw.ljwc.html.htmlparser.client.HTMLParserClient;
import com.gschw.ljwc.html.htmlparser.client.HTMLParserClientParameters;
import com.gschw.ljwc.html.htmlparser.client.IHTMLParserClient;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloader;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloaderParameters;
import com.gschw.ljwc.lj.ljscheduler.client.ILJSinglePageTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJSinglePageTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJTaskClientParameters;
import io.dropwizard.client.JerseyClientBuilder;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;

/**
 * Factory for {@link Processor}
 */
public class ProcessorFactory {
    private DGDownloadTaskClientParameters dgDownloadTaskClientParameters;
    private LJTaskClientParameters ljSinglePageTaskClientParameters;
    private HTMLParserClientParameters htmlParserClientParameters;
    private SimpleDownloaderParameters simpleDownloaderParameters;

    private ProcessorParameters parameters;

    private JerseyClientBuilder clientBuilder;
    private String clientBuilderName;

    public ProcessorFactory(
            DGDownloadTaskClientParameters dgDownloadTaskClientParameters,
            LJTaskClientParameters ljSinglePageTaskClientParameters,
            HTMLParserClientParameters htmlParserClientParameters,
            SimpleDownloaderParameters simpleDownloaderParameters,
            ProcessorParameters parameters,
            JerseyClientBuilder clientBuilder,
            String clientBuilderName) {
        this.dgDownloadTaskClientParameters = dgDownloadTaskClientParameters;
        this.ljSinglePageTaskClientParameters = ljSinglePageTaskClientParameters;
        this.htmlParserClientParameters = htmlParserClientParameters;
        this.simpleDownloaderParameters = simpleDownloaderParameters;
        this.parameters = parameters;
        this.clientBuilder = clientBuilder;
        this.clientBuilderName = clientBuilderName;
    }

    public Processor createProcessor() {
        final Client client = clientBuilder.build(clientBuilderName);

        client.property(ClientProperties.CONNECT_TIMEOUT, 10000);
        client.property(ClientProperties.READ_TIMEOUT, 10000);

        IDGDownloadTaskClient dgDownloadClient = new DGDownloadTaskClient(client, dgDownloadTaskClientParameters);
        ILJSinglePageTaskClient ljSinglePageTaskClient = new LJSinglePageTaskClient(client, ljSinglePageTaskClientParameters);
        IHTMLParserClient htmlParserClient = new HTMLParserClient(client, htmlParserClientParameters);
        SimpleDownloader simpleDownloader = new SimpleDownloader(client, simpleDownloaderParameters);

        return new Processor(
                dgDownloadClient,
                ljSinglePageTaskClient,
                htmlParserClient,
                simpleDownloader,
                parameters);
    }
}

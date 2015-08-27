package com.gschw.ljwc.lj.ljagent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClientParameters;
import com.gschw.ljwc.html.htmlparser.client.HTMLParserClientParameters;
import com.gschw.ljwc.lj.ljagent.core.ProcessorParameters;
import com.gschw.ljwc.lj.ljagent.managed.ProcessorManagerParameters;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloaderParameters;
import com.gschw.ljwc.lj.ljscheduler.client.LJTaskClientParameters;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by nop on 7/20/15.
 */
public class DWConfiguration extends Configuration {
    @Valid
    @NotNull
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @JsonProperty("httpClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }


    //
    @Valid
    @NotNull
    private ProcessorParameters processorParameters;

    @JsonProperty("processorParameters")
    public ProcessorParameters getProcessorParameters() {
        return processorParameters;
    }

    @JsonProperty("processorParameters")
    public void setProcessorParameters(ProcessorParameters processorParameters) {
        this.processorParameters = processorParameters;
    }


    //
    @Valid
    @NotNull
    private DGDownloadTaskClientParameters downloadTaskClientParameters;

    @JsonProperty("downloadTaskClientParameters")
    public DGDownloadTaskClientParameters getDownloadTaskClientParameters() {
        return downloadTaskClientParameters;
    }

    @JsonProperty("downloadTaskClientParameters")
    public void setDownloadTaskClientParameters(DGDownloadTaskClientParameters downloadTaskClientParameters) {
        this.downloadTaskClientParameters = downloadTaskClientParameters;
    }


    //
    @Valid
    @NotNull
    private LJTaskClientParameters ljSinglePageTaskClientParameters;

    @JsonProperty("ljSinglePageTaskClientParameters")
    public LJTaskClientParameters getLjSinglePageTaskClientParameters() {
        return ljSinglePageTaskClientParameters;
    }

    @JsonProperty("ljSinglePageTaskClientParameters")
    public void setLjSinglePageTaskClientParameters(LJTaskClientParameters ljSinglePageTaskClientParameters) {
        this.ljSinglePageTaskClientParameters = ljSinglePageTaskClientParameters;
    }


    //
    @NotNull
    private HTMLParserClientParameters htmlParserClientParameters;

    @JsonProperty("htmlParserClientParameters")
    public HTMLParserClientParameters getHtmlParserClientParameters() {
        return htmlParserClientParameters;
    }

    @JsonProperty("htmlParserClientParameters")
    public void setHtmlParserClientParameters(HTMLParserClientParameters htmlParserClientParameters) {
        this.htmlParserClientParameters = htmlParserClientParameters;
    }


    //
    @Valid
    @NotNull
    private ProcessorManagerParameters processorManagerParameters;

    @JsonProperty("processorManagerParameters")
    public ProcessorManagerParameters getProcessorManagerParameters() {
        return processorManagerParameters;
    }

    @JsonProperty("processorManagerParameters")
    public void setProcessorManagerParameters(ProcessorManagerParameters processorManagerParameters) {
        this.processorManagerParameters = processorManagerParameters;
    }


    //
    @Valid
    @NotNull
    private SimpleDownloaderParameters simpleDownloaderParameters;

    @JsonProperty("simpleDownloaderParameters")
    public SimpleDownloaderParameters getSimpleDownloaderParameters() {
        return simpleDownloaderParameters;
    }

    @JsonProperty("simpleDownloaderParameters")
    public void setSimpleDownloaderParameters(SimpleDownloaderParameters simpleDownloaderParameters) {
        this.simpleDownloaderParameters = simpleDownloaderParameters;
    }
}

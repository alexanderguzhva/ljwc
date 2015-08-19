package com.gschw.ljwc.lj.ljagent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gschw.ljwc.grabber.datagrabber.client.DGDownloadTaskClientParameters;
import com.gschw.ljwc.html.htmlparser.client.HTMLParserClientParameters;
import com.gschw.ljwc.lj.ljagent.core.ProcessorParameters;
import com.gschw.ljwc.lj.ljscheduler.client.LJDownloadTaskClientParameters;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

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
    private LJDownloadTaskClientParameters ljDownloadTaskClientParameters;

    @JsonProperty("ljDownloadTaskClientParameters")
    public LJDownloadTaskClientParameters getLjDownloadTaskClientParameters() {
        return ljDownloadTaskClientParameters;
    }

    @JsonProperty("ljDownloadTaskClientParameters")
    public void setLjDownloadTaskClientParameters(LJDownloadTaskClientParameters ljDownloadTaskClientParameters) {
        this.ljDownloadTaskClientParameters = ljDownloadTaskClientParameters;
    }


    //
    private HTMLParserClientParameters htmlParserClientParameters;

    @JsonProperty
    public HTMLParserClientParameters getHtmlParserClientParameters() {
        return htmlParserClientParameters;
    }

    @JsonProperty
    public void setHtmlParserClientParameters(HTMLParserClientParameters htmlParserClientParameters) {
        this.htmlParserClientParameters = htmlParserClientParameters;
    }
}

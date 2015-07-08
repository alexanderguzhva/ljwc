package com.gschw.ljwc.hbasewriter;

/**
 * Created by nop on 6/22/15.
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
    private HBaseSettings hbaseSettings =
            new HBaseSettings("http://localhost:8080", "lj", "d:r");

    @JsonProperty("hbaseSettings")
    public HBaseSettings getHBaseSettings() {
        return hbaseSettings;
    }

    @JsonProperty("hbaseSettings")
    public void setHBaseSettings(HBaseSettings hbaseSettings) {
        this.hbaseSettings = hbaseSettings;
    }
}


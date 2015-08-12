package com.gschw.ljwc.uploader.cli;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.uploader.DWConfiguration;
import com.gschw.ljwc.uploader.api.DGUploadTask;
import com.gschw.ljwc.uploader.client.DGUploaderClient;
import com.gschw.ljwc.uploader.client.DGUploaderClientParameters;
import io.dropwizard.Application;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.cli.EnvironmentCommand;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.glassfish.jersey.client.JerseyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by nop on 8/11/15.
 */
public class UploadLocalFileCommand extends EnvironmentCommand<DWConfiguration> {
    private static Logger logger = LoggerFactory.getLogger(UploadLocalFileCommand.class);

    @Override
    protected void run(Environment environment, Namespace namespace, DWConfiguration configuration) throws Exception {
        String filename = namespace.getString("filename");

        //// let us read file
        File file = new File(filename);
        byte[] data;
        try(InputStream inputStream = new FileInputStream(file)) {
            data = new byte[(int)file.length()];
            inputStream.read(data);
        }

        //// dgUploadTask
        Identity taskIdentity = new Identity("");
        DGUploadTask dgUploadTask = new DGUploadTask(taskIdentity, filename, data);

        ////
        final Client client = new JerseyClientBuilder(environment)
                .using(configuration.getJerseyClientConfiguration())
                .build(getName());


        ////
        String uploadService = namespace.getString("uploadService");

        DGUploaderClientParameters uploaderClientParameters = new DGUploaderClientParameters();
        DGUploaderClient dgUploaderClient = new DGUploaderClient(client, uploaderClientParameters);
        boolean result = dgUploaderClient.upload(uploadService, dgUploadTask);

        logger.info("Upload {}: {}", filename, result);
    }


    public UploadLocalFileCommand(Application<DWConfiguration> application) {
        super(application, "upload", "Upload local file to database");
    }


    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);

        subparser.addArgument("-f", "--filename")
                .required(true)
                .dest("filename")
                .help("Filename to upload");
        subparser.addArgument("-s", "--service")
                .required(true)
                .dest("uploadService")
                .help("Service to upload to");
    }
}

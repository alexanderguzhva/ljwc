package com.gschw.ljwc.storage.client;

import com.gschw.ljwc.storage.IAbstractStorage;
import com.gschw.ljwc.storage.StorageElementCollection;
import com.gschw.ljwc.storage.StorageReadOperationResult;
import com.gschw.ljwc.storage.StorageWriteOperationResult;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Entity;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nop on 6/22/15.
 */
public class StorageRESTClient implements IAbstractStorage {
    private static Logger logger = LoggerFactory.getLogger(StorageRESTClient.class);

    private Client client;

    private StorageRESTClientParameters parameters;

    //
    public StorageRESTClient(Client client, StorageRESTClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }

    //
    public StorageWriteOperationResult write(StorageElementCollection elements) {
        ////
        try {
            Invocation invocation = client.target(parameters.getRESTServiceURI())
                    .request()
                    .buildPost(Entity.entity(elements, MediaType.APPLICATION_JSON_TYPE));

            Response response = invocation.invoke();

            //
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                return StorageWriteOperationResult.createFailed();
            }

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return StorageWriteOperationResult.createFailed();
        }

        return StorageWriteOperationResult.createSuccess();
    }

    //
    public StorageReadOperationResult read(byte[] key) {
        String keyUri = new String(key);

        ////
        try {
            Response response = client.target(parameters.getRESTServiceURI())
                    .path(String.format("/%s", keyUri))
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .buildGet()
                    .invoke();

            //
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                return StorageReadOperationResult.createFailed();
            }

            //
            StorageElementCollection elements = response.readEntity(StorageElementCollection.class);
            return new StorageReadOperationResult(key, elements);

        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return StorageReadOperationResult.createFailed();
        }
    }

}

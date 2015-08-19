package com.gschw.ljwc.uploader.client;

import com.gschw.ljwc.uploader.api.DGUploadTask;

/**
 * Created by nop on 7/12/15.
 */
public interface IDGUploaderClient {
    boolean upload(String uploadServiceURL, DGUploadTask uploadTask);
}

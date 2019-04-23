/*
 * Copyright (c) 2019 iterate GmbH. All rights reserved.
 */

package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2FileResponse;

import java.io.IOException;

/**
 * When calling b2_copy_file, by default the entire source file will be copied to the destination. If you wish, you may supply a range param to only copy a portion of the source file over.
 */
public class B2CopyFileByIdRequest extends BaseB2Request {
    private static final String B2_COPY_FILE = BASE_API_VERSION + "b2_copy_file";

    /**
     * Create a copy file request
     *
     * @param client                     the http client to use
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param sourceFileId               The ID of the source file being copied
     * @param fileName                   The name of the new file being created
     */
    public B2CopyFileByIdRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String sourceFileId, String fileName) {
        super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_COPY_FILE);

        this.addProperty(B2RequestProperties.KEY_SOURCE_FILE_ID, sourceFileId);
        this.addProperty(B2RequestProperties.KEY_FILE_NAME, fileName);
    }

    /**
     * Return the copy file response
     *
     * @return the copy file response
     * @throws B2ApiException if something went wrong
     * @throws IOException    if there was an error communicating with the API service
     */
    public B2FileResponse getResponse() throws B2ApiException, IOException {
        return new B2FileResponse(EntityUtils.toString(executePost().getEntity()));
    }
}

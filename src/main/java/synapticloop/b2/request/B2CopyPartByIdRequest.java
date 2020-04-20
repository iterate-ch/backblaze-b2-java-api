/*
 * Copyright (c) 2019 iterate GmbH. All rights reserved.
 */

package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2UploadPartResponse;

import java.io.IOException;

/**
 * When calling b2_copy_part, by default the entire source file will be copied to the destination. If you wish, you may supply a range param to only copy a portion of the source file over.
 */
public class B2CopyPartByIdRequest extends BaseB2Request {
    private static final String B2_COPY_PART_FILE = BASE_API_VERSION + "b2_copy_part";

    /**
     * Create a copy file request
     *
     * @param client                     the http client to use
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param sourceFileId               The ID of the source file being copied
     * @param largeFileId                The ID of the large file the created file will belong to
     * @param partNumber                 Indicates which part of the large file the created file is
     * @param range                      The range of bytes to copy
     */
    public B2CopyPartByIdRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String sourceFileId, String largeFileId,
                                 int partNumber, String range) {
        super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_COPY_PART_FILE);

        this.addProperty(B2RequestProperties.KEY_SOURCE_FILE_ID, sourceFileId);
        this.addProperty(B2RequestProperties.KEY_LARGE_FILE_ID, largeFileId);
        this.addProperty(B2RequestProperties.KEY_PART_NUMBER, partNumber);
        this.addProperty(B2RequestProperties.KEY_PART_RANGE, range);
    }

    /**
     * Return the copy file response
     *
     * @return the copy file response
     * @throws B2ApiException if something went wrong
     * @throws IOException    if there was an error communicating with the API service
     */
    public B2UploadPartResponse getResponse() throws B2ApiException, IOException {
        return new B2UploadPartResponse(EntityUtils.toString(executePost().getEntity()));
    }
}

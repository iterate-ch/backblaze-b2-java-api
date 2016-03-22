package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import synapticloop.b2.BucketType;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.*;

import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.*;

public class B2StartLargeFileRequestTest {

	@Test
	public void getResponse() throws Exception {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();

		B2BucketResponse privateBucket = B2TestHelper.createRandomPrivateBucket();

		String privateBucketId = privateBucket.getBucketId();
		final CloseableHttpClient client = HttpClients.createDefault();

		B2StartLargeFileResponse b2StartLargeFileResponse = new B2StartLargeFileRequest(client, b2AuthorizeAccountResponse,
				privateBucketId, UUID.randomUUID().toString(), null, Collections.<String, String>emptyMap()).getResponse();
		assertNotNull(b2StartLargeFileResponse.getFileId());

		final B2GetUploadPartUrlRequest b2GetUploadPartUrlRequest = new B2GetUploadPartUrlRequest(client,
				b2AuthorizeAccountResponse, b2StartLargeFileResponse.getFileId());
		assertNotNull(b2GetUploadPartUrlRequest.getResponse().getUploadUrl());

		final B2ListPartsResponse b2ListPartsResponse = new B2ListPartsRequest(client, b2AuthorizeAccountResponse,
				b2StartLargeFileResponse.getFileId()).getResponse();
		assertTrue(b2ListPartsResponse.getFiles().isEmpty());

		final B2FileResponse b2FileResponse = new B2CancelLargeFileRequest(client, b2AuthorizeAccountResponse,
				b2StartLargeFileResponse.getFileId()).getResponse();
		assertEquals(b2StartLargeFileResponse.getFileId(), b2FileResponse.getFileId());

		B2TestHelper.deleteBucket(privateBucketId);
	}
}
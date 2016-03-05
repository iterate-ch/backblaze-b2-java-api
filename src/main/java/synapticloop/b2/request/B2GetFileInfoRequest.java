package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2FileResponse;

/**
 * <p>Gets information about one file stored in B2.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_get_file_info</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_get_file_info.html">http://www.backblaze.com/b2/docs/b2_get_file_info.html</a>
 * 
 * @author synapticloop
 */
public class B2GetFileInfoRequest extends BaseB2Request {
	private static final String B2_GET_FILE_INFO = BASE_API_VERSION + "b2_get_file_info";

	/**
	 * Create a new get file info request object
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileId the id for the file
	 */
	public B2GetFileInfoRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_GET_FILE_INFO);

		requestBodyData.put(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	/**
	 * Execute the call and retrieve the response for the get file info
	 * 
	 * @return The details for the file information
	 * 
	 * @throws B2Exception if there was an error with the call
	 */
	public B2FileResponse getResponse() throws B2Exception {
		try {
			return(new B2FileResponse(EntityUtils.toString(executePost().getEntity())));
		}
		catch(IOException e) {
			throw new B2Exception(e);
		}
	}
}

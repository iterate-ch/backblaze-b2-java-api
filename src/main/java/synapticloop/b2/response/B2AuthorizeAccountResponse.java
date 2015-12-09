package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public class B2AuthorizeAccountResponse extends BaseB2Response {
	private String accountId = null;
	private String apiUrl = null;
	private String authorizationToken = null;
	private String downloadUrl = null;

	public B2AuthorizeAccountResponse(String response) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(response);

		this.accountId = jsonObject.optString(KEY_ACCOUNT_ID);
		this.apiUrl = jsonObject.optString(KEY_API_URL);
		this.authorizationToken = jsonObject.optString(KEY_AUTHORIZATION_TOKEN);
		this.downloadUrl = jsonObject.optString(KEY_DOWNLOAD_URL);
	}

	/**
	 * Return the account ID used to authorize this account
	 * 
	 * @return the account ID
	 */
	public String getAccountId() {
		return this.accountId;
	}

	/**
	 * The API URL to be used for all subsequent calls to the API
	 * 
	 * @return the api url to use for all subsequent calls
	 */
	public String getApiUrl() {
		return this.apiUrl;
	}

	/**
	 * Return the authorization token to be used for all subsequent calls
	 * 
	 * @return the authorization token to be used for all subsequent calls
	 */
	public String getAuthorizationToken() {
		return this.authorizationToken;
	}

	/**
	 * Return the url top be used for downloading files
	 * 
	 * @return the url to be used for downloading files
	 */
	public String getDownloadUrl() {
		return this.downloadUrl;
	}
}

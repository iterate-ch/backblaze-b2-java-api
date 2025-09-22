package synapticloop.b2.response;

/*
 * Copyright (c) 2016 - 2017 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import synapticloop.b2.exception.B2ApiException;

import java.util.ArrayList;
import java.util.List;

public class B2AuthorizeAccountResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2AuthorizeAccountResponse.class);

	private final String accountId;
	private final String apiUrl;
	private final String authorizationToken;
	private final String downloadUrl;
	private final Integer recommendedPartSize;
	private final Integer absoluteMinimumPartSize;
	private final List<String> allowedCapabilities;
	private final List<String> allowedBuckets;
	private final String allowedNamePrefix;
	/**
	 * Instantiate an authorize account response with the JSON response as a 
	 * string from the API call.  This response is then parsed into the 
	 * relevant fields.
	 * 
	 * @param json the response (in JSON format)
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2AuthorizeAccountResponse(String json) throws B2ApiException {
		super(json);

		this.accountId = this.readString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.apiUrl = this.readString(B2ResponseProperties.KEY_API_URL);
		this.authorizationToken = this.readString(B2ResponseProperties.KEY_AUTHORIZATION_TOKEN);
		this.downloadUrl = this.readString(B2ResponseProperties.KEY_DOWNLOAD_URL);
		this.recommendedPartSize = this.readInt(B2ResponseProperties.KEY_RECOMMENDED_PART_SIZE);
		this.absoluteMinimumPartSize = this.readInt(B2ResponseProperties.KEY_ABSOLUTE_MINIMUM_PART_SIZE);

		// Parse allowed object (added in API v4)
		this.allowedCapabilities = new ArrayList<String>();
		this.allowedBuckets = new ArrayList<String>();

		JSONObject allowedObject = this.readObject(B2ResponseProperties.KEY_ALLOWED);
		if (allowedObject != null) {
			// Parse capabilities array
			JSONArray capabilitiesArray = allowedObject.optJSONArray(B2ResponseProperties.KEY_ALLOWED_CAPABILITIES);
			if (capabilitiesArray != null) {
				for (int i = 0; i < capabilitiesArray.length(); i++) {
					this.allowedCapabilities.add(capabilitiesArray.getString(i));
				}
			}

			// Parse buckets array (can be null for full access)
			JSONArray bucketsArray = allowedObject.optJSONArray(B2ResponseProperties.KEY_ALLOWED_BUCKETS);
			if (bucketsArray != null) {
				for (int i = 0; i < bucketsArray.length(); i++) {
					this.allowedBuckets.add(bucketsArray.getString(i));
				}
			}

			// Parse namePrefix (can be null)
			this.allowedNamePrefix = allowedObject.optString(B2ResponseProperties.KEY_ALLOWED_NAME_PREFIX, null);
		} else {
			this.allowedNamePrefix = null;
		}

		this.warnOnMissedKeys();
	}

	/**
	 * Return the account ID used to authorize this account
	 * 
	 * @return the account ID
	 */
	public String getAccountId() { return this.accountId; }

	/**
	 * The API URL to be used for all subsequent calls to the API
	 * 
	 * @return the api url to use for all subsequent calls
	 */
	public String getApiUrl() { return this.apiUrl; }

	/**
	 * Get authorization token to use with all calls, other than b2_authorize_account, 
	 * that need an Authorization header. This authorization token is valid for at 
	 * most 24 hours.
	 * 
	 * @return the authorization token to be used for all subsequent calls
	 */
	public String getAuthorizationToken() { return this.authorizationToken; }

	/**
	 * Return the url to be used for downloading files
	 * 
	 * @return the URL to be used for downloading files
	 */
	public String getDownloadUrl() { return this.downloadUrl; }

	/**
	 * The recommended size for each part of a large file. We recommend using
	 * this part size for optimal upload performance.
	 *
	 * @return the recommended part size for optimal upload performance
	 */
	public int getRecommendedPartSize() { return recommendedPartSize; }

	/**
	 * The smallest possible size of a part of a large file (except the last one).
	 * This is smaller than the recommendedPartSize. If you use it, you may find
	 * that it takes longer overall to upload a large file.
	 *
	 * @return the absolute minimum part size for downloads
	 */
	public int getAbsoluteMinimumPartSize() { return absoluteMinimumPartSize; }

	/**
	 * Get the list of capabilities that this authorization token allows.
	 * This field was added in API v4 to provide more fine-grained access control.
	 *
	 * @return the list of allowed capabilities for this authorization token
	 */
	public List<String> getAllowedCapabilities() { return new ArrayList<String>(allowedCapabilities); }

	/**
	 * Get the list of bucket IDs that this authorization token allows access to.
	 * If null or empty, the token has access to all buckets.
	 * This field was added in API v4 to provide more fine-grained access control.
	 *
	 * @return the list of allowed bucket IDs for this authorization token
	 */
	public List<String> getAllowedBuckets() { return new ArrayList<String>(allowedBuckets); }

	/**
	 * Get the file name prefix that this authorization token allows access to.
	 * If null, the token has access to all file names.
	 * This field was added in API v4 to provide more fine-grained access control.
	 *
	 * @return the allowed file name prefix for this authorization token
	 */
	public String getAllowedNamePrefix() { return allowedNamePrefix; }

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("B2AuthorizeAccountResponse [accountId=");
		stringBuilder.append(this.accountId);
		stringBuilder.append(", apiUrl=");
		stringBuilder.append(this.apiUrl);
		stringBuilder.append(", authorizationToken=");
		stringBuilder.append(this.authorizationToken);
		stringBuilder.append(", downloadUrl=");
		stringBuilder.append(this.downloadUrl);
		stringBuilder.append(", allowedCapabilities=");
		stringBuilder.append(this.allowedCapabilities);
		stringBuilder.append(", allowedBuckets=");
		stringBuilder.append(this.allowedBuckets);
		stringBuilder.append(", allowedNamePrefix=");
		stringBuilder.append(this.allowedNamePrefix);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}



}

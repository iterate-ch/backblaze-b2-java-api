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

import java.util.*;

public class B2AuthorizeAccountResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2AuthorizeAccountResponse.class);

	private final String accountId;
	private final String apiUrl;
	private final String authorizationToken;
	private final String downloadUrl;
	private final Integer recommendedPartSize;
	private final Integer absoluteMinimumPartSize;
	private final Set<String> capabilities = new HashSet<>();
	private final Map<String, String> buckets = new HashMap<>();
	private final String namePrefix;
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
        this.authorizationToken = this.readString(B2ResponseProperties.KEY_AUTHORIZATION_TOKEN);
        final JSONObject apiInfo = this.readObject(B2ResponseProperties.KEY_API_INFO);
        if (apiInfo != null) {
            final JSONObject storageApi = apiInfo.optJSONObject(B2ResponseProperties.KEY_STORAGE_API);
            if (storageApi != null) {
                this.apiUrl = storageApi.optString(B2ResponseProperties.KEY_API_URL);
                this.downloadUrl = storageApi.optString(B2ResponseProperties.KEY_DOWNLOAD_URL);
                this.recommendedPartSize = storageApi.optIntegerObject(B2ResponseProperties.KEY_RECOMMENDED_PART_SIZE);
                this.absoluteMinimumPartSize = storageApi.optIntegerObject(B2ResponseProperties.KEY_ABSOLUTE_MINIMUM_PART_SIZE);
                final JSONObject allowedObject = storageApi.optJSONObject(B2ResponseProperties.KEY_ALLOWED);
                if (allowedObject != null) {
                    // Parse capabilities array
                    JSONArray capabilitiesArray = allowedObject.optJSONArray(B2ResponseProperties.KEY_ALLOWED_CAPABILITIES);
                    if (capabilitiesArray != null) {
                        for (int i = 0; i < capabilitiesArray.length(); i++) {
                            this.capabilities.add(capabilitiesArray.getString(i));
                        }
                    }
                    // Parse buckets array (can be null for full access)
                    JSONArray bucketsArray = allowedObject.optJSONArray(B2ResponseProperties.KEY_ALLOWED_BUCKETS);
                    if (bucketsArray != null) {
                        for (int i = 0; i < bucketsArray.length(); i++) {
                            final JSONObject bucket = bucketsArray.getJSONObject(i);
                            this.buckets.put(bucket.getString("id"), bucket.getString("name"));
                        }
                    }
                    // Parse namePrefix (can be null)
                    this.namePrefix = allowedObject.optString(B2ResponseProperties.KEY_ALLOWED_NAME_PREFIX, null);
                } else {
                    this.namePrefix = null;
                }
            } else {
                this.apiUrl = null;
                this.downloadUrl = null;
                this.recommendedPartSize = null;
                this.absoluteMinimumPartSize = null;
                this.namePrefix = null;
            }
        }
        else {
            this.apiUrl = null;
            this.downloadUrl = null;
            this.recommendedPartSize = null;
            this.absoluteMinimumPartSize = null;
            this.namePrefix = null;
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
	public Integer getRecommendedPartSize() { return recommendedPartSize; }

	/**
	 * The smallest possible size of a part of a large file (except the last one).
	 * This is smaller than the recommendedPartSize. If you use it, you may find
	 * that it takes longer overall to upload a large file.
	 *
	 * @return the absolute minimum part size for downloads
	 */
	public Integer getAbsoluteMinimumPartSize() { return absoluteMinimumPartSize; }

	/**
	 * Get the list of capabilities that this authorization token allows.
	 * This field was added in API v4 to provide more fine-grained access control.
	 *
	 * @return the list of allowed capabilities for this authorization token
	 */
	public Set<String> getCapabilities() { return new HashSet<>(capabilities); }

	/**
	 * Get the list of bucket IDs that this authorization token allows access to.
	 * If null or empty, the token has access to all buckets.
	 * This field was added in API v4 to provide more fine-grained access control.
	 *
	 * @return the set of allowed bucket IDs mapped to bucket name for this authorization token
	 */
	public Map<String, String> getBuckets() { return new HashMap<>(buckets); }

	/**
	 * Get the file name prefix that this authorization token allows access to.
	 * If null, the token has access to all file names.
	 * This field was added in API v4 to provide more fine-grained access control.
	 *
	 * @return the allowed file name prefix for this authorization token
	 */
	public String getNamePrefix() { return namePrefix; }

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
		stringBuilder.append(this.capabilities);
		stringBuilder.append(", allowedBuckets=");
		stringBuilder.append(this.buckets);
		stringBuilder.append(", allowedNamePrefix=");
		stringBuilder.append(this.namePrefix);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}



}

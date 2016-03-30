package synapticloop.b2.response;

/*
 * Copyright (c) 2016 Synapticloop.
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2FileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2FileResponse.class);

	private final String fileId;
	private final String fileName;
	private final String accountId;
	private final String bucketId;
	private final Long contentLength;
	private final String contentSha1;
	private final String contentType;
	private final Map<String, String> fileInfo;

	/**
	 * Instantiate a file response with the JSON response as a string from 
	 * the API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param json The response (in JSON format)
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	@SuppressWarnings("rawtypes")
	public B2FileResponse(String json) throws B2ApiException {
		super(json);

		this.fileId = this.readString(B2ResponseProperties.KEY_FILE_ID);
		this.fileName = this.readString(B2ResponseProperties.KEY_FILE_NAME);
		this.accountId = this.readString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.bucketId = this.readString(B2ResponseProperties.KEY_BUCKET_ID);
		this.contentLength = this.readLong(B2ResponseProperties.KEY_CONTENT_LENGTH);
		this.contentSha1 = this.readString(B2ResponseProperties.KEY_CONTENT_SHA1);
		this.contentType = this.readString(B2ResponseProperties.KEY_CONTENT_TYPE);
		this.fileInfo = new HashMap<String, String>();
		JSONObject fileInfoObject = this.readObject(B2ResponseProperties.KEY_FILE_INFO);
		if(null != fileInfoObject) {
			for (String key:  fileInfoObject.keySet().toArray(new String[fileInfoObject.keySet().size()])) {
				fileInfo.put(key, this.readString(fileInfoObject, key));
			}
		}

		this.warnOnMissedKeys();
	}

	/**
	 * Get the id of the file that was operated on
	 * 
	 * @return the id of the file that was operated on
	 */
	public String getFileId() { return this.fileId; }

	/**
	 * Get the name of the file that was operated on
	 * 
	 * @return the name of the file that was operated on
	 */
	public String getFileName() { return this.fileName; }

	/**
	 * Get the id of the account that was operated on
	 * 
	 * @return the id of the account that was operated on
	 */
	public String getAccountId() { return this.accountId; }

	/**
	 * Get the id of the bucket that was operated on
	 * 
	 * @return the id of the bucket that was operated on
	 */
	public String getBucketId() { return this.bucketId; }

	/**
	 * Get content length of the file that was operated on, or null if not returned 
	 * in the response
	 * 
	 * @return the content length of the file that was operated on
	 */
	public long getContentLength() { return this.contentLength; }

	/**
	 * Get content SHA1 of the file that was operated on, or null if not returned 
	 * in the response
	 * 
	 * @return the content SHA1 of the file that was operated on
	 */
	public String getContentSha1() { return this.contentSha1; }

	/**
	 * Get content type of the file that was operated on, or null if not returned 
	 * in the response
	 * 
	 * @return the content type of the file that was operated on
	 */
	public String getContentType() { return this.contentType; }

	/**
	 * Get the map of the file info for the file that was operated on, or an empty 
	 * map if not set.
	 * 
	 * @return the map of the file info for the file that was operated on
	 */
	public Map<String, String> getFileInfo() { return this.fileInfo; }

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2FileResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", accountId='").append(accountId).append('\'');
		sb.append(", bucketId='").append(bucketId).append('\'');
		sb.append(", contentLength=").append(contentLength);
		sb.append(", contentSha1='").append(contentSha1).append('\'');
		sb.append(", contentType='").append(contentType).append('\'');
		sb.append(", fileInfo=").append(fileInfo);
		sb.append('}');
		return sb.toString();
	}
}

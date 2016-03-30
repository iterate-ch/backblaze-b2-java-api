package synapticloop.b2.response;

/*
 * Copyright (c) 2016 iterate GmbH.
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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import synapticloop.b2.exception.B2ApiException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class B2StartLargeFileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2StartLargeFileResponse.class);

	private final String fileId;
	private final String fileName;
	private final String accountId;
	private final String bucketId;
	private final String contentType;
	private final Map<String, Object> fileInfo;

	public B2StartLargeFileResponse(String json) throws B2ApiException {
		super(json);

		this.fileId = this.readString(B2ResponseProperties.KEY_FILE_ID);
		this.fileName = this.readString(B2ResponseProperties.KEY_FILE_NAME);
		this.accountId = this.readString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.bucketId = this.readString(B2ResponseProperties.KEY_BUCKET_ID);
		this.contentType = this.readString(B2ResponseProperties.KEY_CONTENT_TYPE);
		this.fileInfo = new HashMap<String, Object>();
		JSONObject fileInfoObject = this.readObject(B2ResponseProperties.KEY_FILE_INFO);
		if (null != fileInfoObject) {
			for (String key:  fileInfoObject.keySet().toArray(new String[fileInfoObject.keySet().size()])) {
				fileInfo.put(key, fileInfoObject.opt(key));
			}
		}

		this.warnOnMissedKeys();
	}

	public String getBucketId() { return this.bucketId; }

	public String getFileId() { return this.fileId; }

	public String getFileName() { return this.fileName; }

	public String getAccountId() { return this.accountId; }

	public String getContentType() { return this.contentType; }

	public Map<String, Object> getFileInfo() { return this.fileInfo; }

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2StartLargeFileResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", bucketId='").append(bucketId).append('\'');
		sb.append(", contentType='").append(contentType).append('\'');
		sb.append(", fileInfo=").append(fileInfo);
		sb.append('}');
		return sb.toString();
	}
}

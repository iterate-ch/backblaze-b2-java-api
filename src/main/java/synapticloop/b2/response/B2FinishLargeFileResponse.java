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
import synapticloop.b2.Action;
import synapticloop.b2.exception.B2ApiException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class B2FinishLargeFileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2FinishLargeFileResponse.class);

	private final String fileId;
	private final String fileName;
	private final String accountId;
	private final String bucketId;
	private final Long contentLength;
	private final String contentSha1;
	private final String contentType;

	private final Map<String, String> fileInfo;
	private final Action action;

	public B2FinishLargeFileResponse(final String json) throws B2ApiException {
		super(json);

		this.fileId = this.readString(B2ResponseProperties.KEY_FILE_ID);
		this.fileName = this.readString(B2ResponseProperties.KEY_FILE_NAME);
		this.accountId = this.readString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.bucketId = this.readString(B2ResponseProperties.KEY_BUCKET_ID);
		this.contentLength = this.readLong(B2ResponseProperties.KEY_CONTENT_LENGTH);
		this.contentType = this.readString(B2ResponseProperties.KEY_CONTENT_TYPE);
		this.contentSha1 = this.readString(B2ResponseProperties.KEY_CONTENT_SHA1);
		this.fileInfo = new HashMap<String, String>();

		JSONObject fileInfoObject = this.readObject(B2ResponseProperties.KEY_FILE_INFO);
		if(null != fileInfoObject) {
			Iterator keys = fileInfoObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				fileInfo.put(key, this.readString(fileInfoObject, key));
			}
		}

		String action = this.readString(B2ResponseProperties.KEY_ACTION);
		if(null != action) {
			this.action = Action.valueOf(action);
		} else {
			// Default
			this.action = Action.upload;
		}

		this.warnOnMissedKeys();
	}

	public String getFileId() {
		return fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public Long getContentLength() {
		return contentLength;
	}

	public String getContentSha1() {
		return contentSha1;
	}

	public String getContentType() {
		return contentType;
	}

	public Map<String, String> getFileInfo() {
		return fileInfo;
	}

	public Action getAction() {
		return action;
	}

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2FinishLargeFileResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", contentLength=").append(contentLength);
		sb.append(", contentSha1='").append(contentSha1).append('\'');
		sb.append(", contentType='").append(contentType).append('\'');
		sb.append(", fileInfo=").append(fileInfo);
		sb.append(", action=").append(action);
		sb.append('}');
		return sb.toString();
	}
}

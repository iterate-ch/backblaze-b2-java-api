/*
 * Copyright (c) 2025 iterate GmbH. All rights reserved.
 */

package synapticloop.b2.response;

import junit.framework.TestCase;
import org.junit.Test;

public class B2AuthorizeAccountResponseTest extends TestCase {

    @Test
    public void testParseJson() throws Exception {
        final String json = "{\n" +
                "  \"accountId\" : \"ad96b6ea5ed0\",\n" +
                "  \"apiInfo\" : {\n" +
                "    \"storageApi\" : {\n" +
                "      \"absoluteMinimumPartSize\" : 5000000,\n" +
                "      \"allowed\" : {\n" +
                "        \"buckets\" : null,\n" +
                "        \"capabilities\" : [ \"writeBuckets\", \"listBuckets\", \"readFiles\", \"deleteBuckets\", \"deleteFiles\", \"listKeys\", \"writeFileRetentions\", \"listAllBucketNames\", \"readBuckets\", \"writeBucketReplications\", \"listFiles\", \"shareFiles\", \"readBucketNotifications\", \"writeBucketEncryption\", \"writeFiles\", \"writeKeys\", \"writeFileLegalHolds\", \"writeBucketRetentions\", \"readBucketRetentions\", \"writeBucketNotifications\", \"bypassGovernance\", \"readBucketEncryption\", \"readFileLegalHolds\", \"writeBucketLogging\", \"readFileRetentions\", \"deleteKeys\", \"readBucketReplications\", \"readBucketLogging\" ],\n" +
                "        \"namePrefix\" : null\n" +
                "      },\n" +
                "      \"apiUrl\" : \"https://api001.backblazeb2.com\",\n" +
                "      \"downloadUrl\" : \"https://f001.backblazeb2.com\",\n" +
                "      \"recommendedPartSize\" : 100000000,\n" +
                "      \"s3ApiUrl\" : \"https://s3.us-west-001.backblazeb2.com\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"applicationKeyExpirationTimestamp\" : null,\n" +
                "  \"authorizationToken\" : \"invalid\"\n" +
                "}";
        final B2AuthorizeAccountResponse response = new B2AuthorizeAccountResponse(json);
        assertNotNull(response);
        assertNotNull(response.getApiUrl());
        assertFalse(response.getCapabilities().isEmpty());
        assertTrue(response.getBuckets().isEmpty());
        assertEquals(5000000, response.getAbsoluteMinimumPartSize());
        assertEquals(100000000, response.getRecommendedPartSize());
        assertEquals("invalid", response.getAuthorizationToken());
    }
}
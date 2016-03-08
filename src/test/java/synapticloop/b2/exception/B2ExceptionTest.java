package synapticloop.b2.exception;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class B2ExceptionTest {

	@Test
	public void testEmptyException() {
		B2ApiException b2Exception = new B2ApiException("");
		assertEquals("", b2Exception.getMessage());
	}

	@Test
	public void testNullResponseException() {
		B2ApiException b2Exception = new B2ApiException((String)null);
		assertEquals(null, b2Exception.getMessage());
	}

	@Test
	public void testJsonException() {
		B2ApiException b2Exception = new B2ApiException("{\n" +
				"      \"code\": \"bad_json\",\n" + 
				"      \"message\": \"unknown field in com.backblaze.modules.b2.data.FileNameAndId: accountId\",\n" + 
				"      \"status\": 400\n" + 
				"    }\n" + 
				")");
		assertEquals(400, b2Exception.getStatus());
		assertEquals("bad_json", b2Exception.getCode());
		assertEquals("unknown field in com.backblaze.modules.b2.data.FileNameAndId: accountId", b2Exception.getMessage());
	}
}

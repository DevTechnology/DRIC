package com.devtechnology.api.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.devtechnology.api.domain.NdcImage;
import com.devtechnology.api.domain.RxImageResponse;
import com.devtechnology.api.domain.RxImageResult;

/**
 * Tests for RxImageUtil
 * @author jbnimble
 *
 */
public class RxImageUtilTest {

	@Before
	public void setup() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void testNullNdcImageResponse() {
		RxImageUtil util = new RxImageUtil();
		util.setHttpOperations(new HttpOpsNullMock());
		NdcImage response = util.getNdcUrl(null);
		assertNotNull("NdcImage is null", response);
		response = util.getNdcUrl("test1");
		assertNotNull("NdcImage is null", response);
		assertNotNull("Ndc is null", response.getNdc());
		assertTrue("ndc is not 'test1'", response.getNdc().equals("test1"));
		assertTrue("URL list is not empty", response.getUrl().isEmpty());
	}
	
	@Test
	public void testMapResponse() {
		RxImageUtil util = new RxImageUtil();
		RxImageResponse r = new RxImageResponse();
		RxImageResult ir = new RxImageResult();
		r.getNlmRxImages().add(ir);
		NdcImage response = util.mapResponse("test1", r);
		assertNotNull("NdcImage is null", response);
		assertTrue("ndc is not 'test1'", response.getNdc().equals("test1"));
		assertTrue("URL list is not empty", response.getUrl().isEmpty());
		
		ir.setImageUrl("testUrl1");
		response = util.mapResponse("test1", r);
		assertTrue("url size != 1", response.getUrl().size() == 1);
		RxImageResult ir2 = new RxImageResult();
		ir2.setImageUrl("testUrl1");
		r.getNlmRxImages().add(ir2);
		response = util.mapResponse("test1", r);
		assertTrue("url size != 1", response.getUrl().size() == 1);
	}
}

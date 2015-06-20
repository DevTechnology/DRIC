package com.devtechnology.api.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.devtechnology.api.domain.FdaOpen;
import com.devtechnology.api.domain.FdaResponse;
import com.devtechnology.api.domain.FdaResults;
import com.devtechnology.api.domain.RecallResponse;

/**
 * Tests for FdaUtil
 * @author jbnimble
 */
public class FdaUtilTest {

	@Before
	public void setup() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void testNullRecentRecallResponse() {
		FdaUtil fdaUtil = new FdaUtil();
		fdaUtil.setHttpOperations(new HttpOpsNullMock());
		RecallResponse response = fdaUtil.getRecentRecalls(null, null);
		assertNotNull("RecallResponse is null", response);
		assertNotNull("RecallResponse.error is null", response.getError());
		assertNotNull("RecallResponse.error.code is null", response.getError().getCode());
		assertTrue("Did not match 'externalFailure'", response.getError().getCode().equals("externalFailure"));
	}
	
	@Test
	public void testNullRecallsResponse() {
		FdaUtil fdaUtil = new FdaUtil();
		fdaUtil.setHttpOperations(new HttpOpsNullMock());
		RecallResponse response = fdaUtil.getRecalls(null, null, null, null, null, null);
		assertNotNull("RecallResponse is null", response);
		assertNotNull("RecallResponse.error is null", response.getError());
		assertNotNull("RecallResponse.error.code is null", response.getError().getCode());
		assertTrue("Did not match 'externalFailure'", response.getError().getCode().equals("externalFailure"));
	}
	
	@Test
	public void testSimpleRecentRecallResponse() {
		FdaUtil fdaUtil = new FdaUtil();
		fdaUtil.setHttpOperations(new HttpOpsSimpleMock());
		RecallResponse response = fdaUtil.getRecentRecalls(null, null);
		assertNotNull("RecallResponse is null", response);
		assertNull("RecallResponse.error is not null", response.getError());
	}
	
	@Test
	public void testSimpleRecallsResponse() {
		FdaUtil fdaUtil = new FdaUtil();
		fdaUtil.setHttpOperations(new HttpOpsSimpleMock());
		RecallResponse response = fdaUtil.getRecalls(null, null, null, null, null, null);
		assertNotNull("RecallResponse is null", response);
		assertNull("RecallResponse.error is not null", response.getError());
	}
	
	@Test
	public void testMapping() {
		FdaUtil fdaUtil = new FdaUtil();
		FdaResponse f = new FdaResponse();
		FdaResults item = new FdaResults();
		item.setProduct_description("test1");
		item.setOpenfda(new FdaOpen());
		item.getOpenfda().getGeneric_name().add("product1");
		item.getOpenfda().getBrand_name().add("product2");
		item.getOpenfda().getBrand_name().add("product1");
		item.getOpenfda().getSubstance_name().add("product3");
		item.getOpenfda().getProduct_ndc().addAll(Arrays.asList("ndc1","ndc2","ndc3","ndc1"));
		f.getResults().add(item);
		RecallResponse r = fdaUtil.mapResponse(f);
		assertNotNull("RecallResponse is null", r);
		assertNotNull("RecallItem list is null", r.getRecalls());
		int size = r.getRecalls().size();
		assertTrue("RecallItem list is not size=1 instead "+size, size == 1);
		assertTrue("product_description does not match", r.getRecalls().get(0).getProduct_description().equals("test1"));
		assertTrue("product does not contain 'product1'", r.getRecalls().get(0).getProduct().contains("product1"));
		assertTrue("product does not contain 'product2'", r.getRecalls().get(0).getProduct().contains("product2"));
		assertTrue("product does not contain 'product3'", r.getRecalls().get(0).getProduct().contains("product3"));
		assertTrue("product has more than 3 unique items", r.getRecalls().get(0).getProduct().size() == 3);
		assertTrue("ndc has more than 3 unique items", r.getRecalls().get(0).getProduct_ndc().size() == 3);
	}
}

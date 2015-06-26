package com.devtechnology.api.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.devtechnology.api.domain.FdaClassificationFilter;
import com.devtechnology.api.domain.FdaOpen;
import com.devtechnology.api.domain.FdaReportDateFilter;
import com.devtechnology.api.domain.FdaResponse;
import com.devtechnology.api.domain.FdaResults;
import com.devtechnology.api.domain.FdaStatusFilter;
import com.devtechnology.api.domain.RecallResponse;

/**
 * Tests for FdaUtil
 * @author jbnimble
 */
public class FdaUtilTest {
	private static Logger logger = Logger.getLogger(FdaUtilTest.class);
	@Before
	public void setup() {
		BasicConfigurator.configure();
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
	public void testSimpleRecallsResponse() {
		FdaUtil fdaUtil = new FdaUtil();
		fdaUtil.setHttpOperations(new HttpOpsSimpleMock());
		RecallResponse response = fdaUtil.getRecalls(null, null, null, null, null, null);
		assertNotNull("RecallResponse is null", response);
		assertNull("RecallResponse.error is not null", response.getError());
	}
	
	@Test
	public void testMapResponse() {
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
	
	@Test
	public void testMapSearchFilter() {
		FdaUtil fdaUtil = new FdaUtil();
		String result = fdaUtil.mapSearchFilter(null, null, null, null);
		assertTrue("result != '' with all null parameters result="+result, "".equals(result));
		result = fdaUtil.mapSearchFilter("", null, null, null);
		assertTrue("result != '' with searchFilter='' result="+result, "".equals(result));
		result = fdaUtil.mapSearchFilter("undefined", null, null, null);
		assertTrue("result != '' with with searchFilter='undefined' result="+result, "".equals(result));
		result = fdaUtil.mapSearchFilter(null, FdaReportDateFilter.ALL, null, null);
		assertTrue("result != '' with with FdaReportDateFilter='ALL' result="+result, "".equals(result));
		result = fdaUtil.mapSearchFilter(null, null, FdaStatusFilter.ALL, null);
		assertTrue("result != '' with with FdaStatusFilter='ALL' result="+result, "".equals(result));
		result = fdaUtil.mapSearchFilter(null, null, null, FdaClassificationFilter.ALL);
		assertTrue("result != '' with with FdaClassificationFilter='ALL' result="+result, "".equals(result));
		result = fdaUtil.mapSearchFilter("testing123", FdaReportDateFilter.SIXMONTH, FdaStatusFilter.ONGOING, FdaClassificationFilter.CLASS1);
		assertTrue("result missing textFilter result="+result, result.indexOf("testing123") != -1);
		assertTrue("result missing reportDate result="+result, result.indexOf("report_date:") != -1);
		assertTrue("result missing status result="+result, result.indexOf("status:Ongoing") != -1);
		assertTrue("result missing classification result="+result, result.indexOf("classification:%22Class+I%22") != -1);
		assertTrue("result wrong number of ANDs result="+result, result.split("AND").length == 4);
	}
	
	@Test
	public void testGetFormattedDate() {
		FdaUtil util = new FdaUtil();
		Map<String,String> map = new HashMap<>();
		map.put("19000110", "01/10/1900");
		map.put(null, null);
		map.put("", "");
		map.put("2", "2");
		map.put("20221225", "12/25/2022");
		map.put("1234567", "1234567");
		map.put("ABC", "ABC");
		map.put("20150631", "07/01/2015");
		map.put("201506310", "201506310");
		for (String key : map.keySet()) {
			String val = map.get(key);
			String res = util.getFormattedDate(key);
			logger.info("key="+key+" val="+val+" res="+res);
			assertTrue(key+" should be "+val+" but is "+res, (val == null && res == null) || val.equals(res));
		}
	}
	
	@Test
	public void testGetShortDescription() {
		FdaUtil util = new FdaUtil();
		Map<String,String> map = new HashMap<>();
		map.put(null, 
				"No description available");
		map.put("", 
				"No description available");
		map.put("Testing 1 2 3", 
				"Testing 1 2 3");
		map.put("Testing for some drugs, here are some more drugs", 
				"Testing for some drugs");
		map.put("Testing for some drugs still testing yep still testing still testing yep still testing here you goat are some more drugs", 
				"Testing for some drugs still testing yep still testing still testing yep still testing here you goat");
		map.put("Testing for some drugs (data, data, data) and here we go, still testing yep still testing still testing", 
				"Testing for some drugs (data, data, data) and here we go");
		map.put("Testing for some drugs (data, data, data) and here we go still testing yep still testing still test. to go", 
				"Testing for some drugs (data, data, data) and here we go still testing yep still testing still test.");
		for (String key : map.keySet()) {
			String val = map.get(key);
			String res = util.getShortDescription(key);
			logger.info("val='"+val+"' res='"+res+"'");
			assertTrue("should be "+val+" but is "+res, val.equals(res));
		}
	}
}

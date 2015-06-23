package com.devtechnology.api.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.devtechnology.api.domain.FdaOpen;
import com.devtechnology.api.domain.FdaReportDateFilter;
import com.devtechnology.api.domain.FdaResponse;
import com.devtechnology.api.domain.NdcImage;
import com.devtechnology.api.domain.RecallItem;
import com.devtechnology.api.domain.RecallResponse;
import com.devtechnology.api.domain.RxImageResponse;

/**
 * Tests for domain POJOs
 * @author jbnimble
 *
 */
public class TestDomainPojos {

	@Before
	public void setup() {
		BasicConfigurator.configure();
	}
	
	/**
	 * All getXList() in our POJOs return an empty list if it is internally null, test that this is the case
	 */
	@Test
	public void testNullList() {
		FdaOpen fo = new FdaOpen();
		assertNotNull("brand_name list is null", fo.getBrand_name());
		assertNotNull("Generic_name list is null", fo.getGeneric_name());
		assertNotNull("Product_ndc list is null", fo.getProduct_ndc());
		assertNotNull("Substance_name list is null", fo.getSubstance_name());
		FdaResponse fr = new FdaResponse();
		assertNotNull("Results list is null", fr.getResults());
		NdcImage ni = new NdcImage();
		assertNotNull("Url list is null", ni.getUrl());
		RecallItem ri = new RecallItem();
		assertNotNull("Product list is null", ri.getProduct());
		assertNotNull("Product_ndc list is null", ri.getProduct_ndc());
		RecallResponse rr = new RecallResponse();
		assertNotNull("Recalls list is null", rr.getRecalls());
		RxImageResponse rir = new RxImageResponse();
		assertNotNull("NlmRxImages list is null", rir.getNlmRxImages());
	}
	
	@Test
	public void testFdaReportDateFilter() {
		String result = FdaReportDateFilter.ALL.getFilter();
		assertNull("ALL filter is not null, result="+result, result);
		result = FdaReportDateFilter.ONEMONTH.getFilter();
		assertTrue("ONEMONTH missing value, result="+result, result.indexOf("report_date:[") != -1);
		result = FdaReportDateFilter.SIXMONTH.getFilter();
		assertTrue("SIXMONTH missing value, result="+result, result.indexOf("report_date:[") != -1);
	}
}

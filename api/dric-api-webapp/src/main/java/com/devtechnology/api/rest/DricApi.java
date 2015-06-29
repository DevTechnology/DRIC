package com.devtechnology.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.devtechnology.api.domain.FdaClassificationFilter;
import com.devtechnology.api.domain.FdaReportDateFilter;
import com.devtechnology.api.domain.FdaStatusFilter;
import com.devtechnology.api.domain.NdcImage;
import com.devtechnology.api.domain.RecallResponse;
import com.devtechnology.api.util.FdaUtil;
import com.devtechnology.api.util.HttpOperations;
import com.devtechnology.api.util.RxImageUtil;

/**
 * API for Drug Recall Information Center (DRIC)
 * @author jbnimble
 */
@Path("/drug")
public class DricApi {
	private static Logger logger = Logger.getLogger(DricApi.class);
	/**
	 * Get the latest recall data, or recalls that match the given drug name
	 * Usage: /dric/api/drug/recall
	 * @param textFilter, full data set text filter
	 * @param reportDate, see FdaReportDateFilter for options
	 * @param status, see FdaStatusFilter for options
	 * @param classification, see FdaClassificationFilter for options
	 * @param limit, defaults to 100, used to specify how many results to return per request
	 * @param skip, defaults to 0, used to specify the starting index of results, used in combination with limit can be used for pagination
	 * @return
	 */
	@GET
	@Path("/recall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDrugRecalls(@QueryParam("name") String textFilter, @QueryParam("reportDate") FdaReportDateFilter reportDate, @QueryParam("status") FdaStatusFilter status, @QueryParam("classification") FdaClassificationFilter classification, @QueryParam("limit") Integer limit, @QueryParam("skip") Integer skip) {
		logger.info("GET request for /recall?name="+textFilter+"&reportDate="+reportDate+"&status="+status+"&classification="+classification+"&limit="+limit+"&skip="+skip);
		FdaUtil fda = new FdaUtil();
		fda.setHttpOperations(new HttpOperations());
		RecallResponse result = fda.getRecalls(textFilter, reportDate, status, classification, limit, skip);
		Response response = Response.ok(result).build();
		return response;
	}
	
	/**
	 * Get the image URL data for a given NDC
	 * Usage: /dric/api/drug/image/{ndc}
	 * @param ndc
	 * @return
	 */
	@GET
	@Path("/image/{ndc}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDrugImage(@PathParam("ndc") String ndc) {
		RxImageUtil rxImage = new RxImageUtil();
		rxImage.setHttpOperations(new HttpOperations());
		NdcImage ndcImage = rxImage.getNdcUrl(ndc);
		return Response.ok(ndcImage).build();
	}
	
	/**
	 * Get the first image that becomes available from a comma separated list of NDC values
	 * /dric/api/drug/image?ndcs=val1,val2,val3
	 * @param ndcs
	 * @return
	 */
	@GET
	@Path("/image")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDrugImages(@QueryParam("ndcs") String ndcs) {
		RxImageUtil rxImage = new RxImageUtil();
		rxImage.setHttpOperations(new HttpOperations());
		NdcImage ndcImage = rxImage.getNdcsImageUrl(ndcs);
		return Response.ok(ndcImage).build();
	}
}

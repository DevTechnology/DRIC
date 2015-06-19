package com.devtechnology.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.devtechnology.api.domain.NdcImage;
import com.devtechnology.api.domain.RecallResponse;
import com.devtechnology.api.util.FdaUtil;
import com.devtechnology.api.util.RxImageUtil;

/**
 * API for Drug Recall Information Center (DRIC)
 * @author jbnimble
 */
@Path("/drug")
public class DricApi {
	
	/**
	 * Get the latest recall data, or recalls that match the given drug name
	 * @param drugName
	 * @return
	 */
	@GET
	@Path("/recall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDrugRecallListing(@QueryParam("name") String name) {
		Response response = Response.ok().build();
		FdaUtil fda = new FdaUtil();
		if (name == null || name.trim().equals("") || name.trim().equals("undefined")) {
			RecallResponse result = fda.getRecentRecalls();
			response = Response.ok(result).build();
		} else {
			RecallResponse result = fda.getRecalls(name);
			response = Response.ok(result).build();
		}
		return response;
	}
	
	/**
	 * Get the recall details for the given key
	 * @param key
	 * @return
	 */
	@GET
	@Path("/image/{ndc}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDrugImage(@PathParam("ndc") String ndc) {
		// find the recall from the key and return
		RxImageUtil rxImage = new RxImageUtil();
		NdcImage ndcImage = rxImage.getNdcUrl(ndc);
		return Response.ok(ndcImage).build();
	}
}

package com.devtechnology.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * API for Drug Recall Information Center (DRIC)
 * @author jbnimble
 */
@Path("/api")
public class DricApi {
	
	/**
	 * Get the latest recall data, or recalls that match the given drug name
	 * @param drugName
	 * @return
	 */
	@GET
	@Path("/drugrecall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDrugRecallListing(@QueryParam("name") String drugName) {
		if (drugName == null) {
			// return latest X recalls
		} else {
			// find recalls that match drug name
			// return results
		}
		return Response.ok().build();
	}
	
	/**
	 * Get the recall details for the given key
	 * @param key
	 * @return
	 */
	@GET
	@Path("/drugrecall/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDrugRecallDetails(@PathParam("key") String key) {
		// find the recall from the key and return
		return Response.ok().build();
	}
}

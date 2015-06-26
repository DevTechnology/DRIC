package com.devtechnology.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.devtechnology.api.domain.FdaClassificationFilter;
import com.devtechnology.api.domain.FdaError;
import com.devtechnology.api.domain.FdaMeta;
import com.devtechnology.api.domain.FdaReportDateFilter;
import com.devtechnology.api.domain.FdaResponse;
import com.devtechnology.api.domain.FdaResults;
import com.devtechnology.api.domain.FdaStatusFilter;
import com.devtechnology.api.domain.RecallItem;
import com.devtechnology.api.domain.RecallResponse;
import com.google.gson.Gson;

/**
 * Utility methods for getting data from Open FDA Enforcement API
 * Reference: https://open.fda.gov/api/reference/
 * Reference: https://open.fda.gov/drug/enforcement/
 * @author jbnimble
 */
public class FdaUtil {
	private static Logger logger = Logger.getLogger(FdaUtil.class);
	private String baseUrl = "https://api.fda.gov/drug/enforcement.json?";
	private static Integer defaultLimit = 100; // 100 is max OpenFDA API will give in a single request
	private static Integer defaultSkip = 0;
	static String dbQuote = "%22"; // used for 'exact' search
	private HttpOps httpOps;
	
	/**
	 * RecallResponse object with the recall results matching the given filter values
	 * @param textFilter
	 * @return
	 */
	public RecallResponse getRecalls(String textFilter, FdaReportDateFilter reportDate, FdaStatusFilter status, FdaClassificationFilter classification, Integer limit, Integer skip) {
		String criteria = buildCriteria(mapSearchFilter(textFilter, reportDate, status, classification), getLimit(limit), getSkip(skip), getApiKey());
		String url = baseUrl+criteria;
		FdaResponse fdaResponse = httpOps.getMappedFromUlr(url, FdaResponse.class);
		if (fdaResponse == null) {
			fdaResponse = getError();
		}
		if (fdaResponse.getMeta() == null) {
			fdaResponse.setMeta(new FdaMeta());
		}
		fdaResponse.getMeta().setHttpStatus(httpOps.getHttpStatus());
//		logger.info(new Gson().toJson(fdaResponse));
		RecallResponse result = mapResponse(fdaResponse);
		return result;
	}
	
	/**
	 * Used to create a proper GET criteria, separating fields with an ampersand
	 * @param searchFilter
	 * @param limit
	 * @param skip
	 * @param apiKey
	 * @return
	 */
	public String buildCriteria(String searchFilter, String limit, String skip, String apiKey) {
		String r = "";
		List<String> criteria = new ArrayList<String>();
		if (searchFilter != null && !"".equals(searchFilter.trim())) {
			criteria.add(searchFilter);
		}
		if (limit != null && !"".equals(limit.trim())) {
			criteria.add(limit);
		}
		if (skip != null && !"".equals(skip.trim())) {
			criteria.add(skip);
		}
		if (apiKey != null && !"".equals(apiKey.trim())) {
			criteria.add(apiKey);
		}
		if (!criteria.isEmpty()) {
			StringBuilder b = new StringBuilder();
			for (String f : criteria) {
				b.append(f);
				if (criteria.indexOf(f) < criteria.size() - 1) {
					b.append("&");
				}
			}
			r = b.toString();
		}
		return r;
	}
	
	/**
	 * Map the 'search=value' fields to filter the data. Creating an exact text search and multiple filters use AND logic
	 * @param textFilter
	 * @param reportDate
	 * @param status
	 * @param classification
	 * @return
	 */
	public String mapSearchFilter(String textFilter, FdaReportDateFilter reportDate, FdaStatusFilter status, FdaClassificationFilter classification) {
		// only add valid values, and return as 'search=key1:val1+AND+key2:val2'
		String r = "";
		List<String> filters = new ArrayList<String>();
		if (textFilter != null && !"".equals(textFilter.trim()) && !"undefined".equals(textFilter.trim())) {
			textFilter = textFilter.replaceAll(" ", "+");
			filters.add(textFilter);
		}
		if (reportDate != null && reportDate.getFilter() != null) {
			filters.add(reportDate.getFilter());
		}
		if (status != null && status.getFilter() != null) {
			filters.add(status.getFilter());
		}
		if (classification != null && classification.getFilter() != null) {
			filters.add(classification.getFilter());
		}
		if (!filters.isEmpty()) {
			StringBuilder b = new StringBuilder("search=");
			for (String f : filters) {
				b.append(f);
				if (filters.indexOf(f) < filters.size() - 1) {
					b.append("+AND+");
				}
			}
			r = b.toString();
		}
		return r;
	}
	
	/**
	 * Map an FdaResponse to a RecallResponse
	 * @param fdaResponse
	 * @return
	 */
	public RecallResponse mapResponse(FdaResponse fdaResponse) {
		RecallResponse result = new RecallResponse();
		if (fdaResponse != null) {
			result.setMeta(fdaResponse.getMeta());
			result.setError(fdaResponse.getError());
			List<RecallItem> list = new ArrayList<>();
			for (FdaResults fdaResult : fdaResponse.getResults()) {
				Set<String> productSet = new HashSet<String>();
				Set<String> ndcSet = new HashSet<String>();
				RecallItem item = new RecallItem();
				if (fdaResult != null) {
					// fill basic information
					item.setRecall_number(fdaResult.getRecall_number());
					item.setReason_for_recall(fdaResult.getReason_for_recall());
					item.setStatus(fdaResult.getStatus());
					item.setDistribution_pattern(fdaResult.getDistribution_pattern());
					item.setProduct_quantity(fdaResult.getProduct_quantity());
					item.setRecall_initiation_date(getFormattedDate(fdaResult.getRecall_initiation_date()));
					item.setState(fdaResult.getState());
					item.setEvent_id(fdaResult.getEvent_id());
					item.setProduct_type(fdaResult.getProduct_type());
					item.setProduct_description(fdaResult.getProduct_description());
					item.setShort_product_description(getShortDescription(fdaResult.getProduct_description()));
					item.setCountry(fdaResult.getCountry());
					item.setCity(fdaResult.getCity());
					item.setRecalling_firm(fdaResult.getRecalling_firm());
					item.setReport_date(getFormattedDate(fdaResult.getReport_date()));
					item.setVoluntary_mandated(fdaResult.getVoluntary_mandated());
					item.setClassification(fdaResult.getClassification());
					item.setCode_info(fdaResult.getCode_info());
					item.setInitial_firm_notification(fdaResult.getInitial_firm_notification());
					if (fdaResult.getOpenfda() != null) {
						// attempt to get unique product names from multiple fields
						productSet.addAll(fdaResult.getOpenfda().getGeneric_name());
						productSet.addAll(fdaResult.getOpenfda().getBrand_name());
						productSet.addAll(fdaResult.getOpenfda().getSubstance_name());
						// get unique NDC values
						ndcSet.addAll(fdaResult.getOpenfda().getProduct_ndc());
						ndcSet.addAll(fdaResult.getOpenfda().getPackage_ndc());
						item.getProduct_ndc().addAll(ndcSet);
					}
				}
				item.getProduct().addAll(productSet);
				list.add(item);
			}
			result.getRecalls().addAll(list);
		}
		return result;
	}
	
	/**
	 * Shorten the product_description
	 * @param desc
	 * @return
	 */
	public String getShortDescription(String desc) {
		int max = 100;
		String result = "No description available";
		if (desc != null && desc.trim().length() > 0) {
			int pos = desc.indexOf(",");
			if (pos == -1) { // no comma
				if (desc.trim().length() > max) {
					result = desc.substring(0,max); // return first max characters
				} else {
					result = desc;
				}
			} else if (pos > max) {
				result = desc.substring(0,max); // return first max characters
			} else {
				// check for "data (data, data) data, data" and get the data before the comma but after the ')'
				int parenPos = desc.indexOf(")"); // close parenthesis position
				int commaPos = parenPos != -1 ? desc.substring(parenPos).indexOf(",") : -1; // comma after close paren position
				if (commaPos == -1 && parenPos != -1 && parenPos < 100) {
					// no comma after paren so either go to max or maxlength
					if (desc.length() < max) {
						result = desc.substring(0);
					} else {
						result = desc.substring(0,max);
					}
				} else if (commaPos != -1) {
					if (commaPos+parenPos < 100) {
						result = desc.substring(0,commaPos+parenPos);
					} else {
						result = desc.substring(0,max);
					}
				} else {
					result = desc.substring(0,pos);
				}
			}
		}
		return result;
	}
	
	/**
	 * Convert yyyyMMdd format to MM/dd/yyyy
	 * @param dateStr
	 * @return
	 */
	public String getFormattedDate(String dateStr) {
		String d = dateStr;
		if (d != null && d.length() == 8) {
			SimpleDateFormat formatyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatMMddyyyy = new SimpleDateFormat("MM/dd/yyyy");
			try {
				Date date = formatyyyyMMdd.parse(d);
				d = formatMMddyyyy.format(date);
			} catch (ParseException e) {
				logger.warn("Failed to parse date="+d);
			}
		}
		return d;
	}
	
	/**
	 * Get the system property, if set, of the Open FDA API key. property name is 'openFdaApiKey'
	 * The OpenFDA API does not require an API key, but if applicable then inject it into the container as a server property
	 * @return URL parameter to add the FDA API key to the criteria
	 */
	private String getApiKey() {
		String key = System.getProperty("openFdaApiKey");
		String s = "";
		if (key != null) {
			s = "api_key="+key;
			logger.info("Using openFdaApiKey="+key);
		}
		return s;
	}
	
	/**
	 * get the limit value and use default if an invalid value
	 * @param limit
	 * @return
	 */
	private String getLimit(Integer limit) {
		if (limit == null || limit < 1) {
			limit = defaultLimit;
		}
		return "limit="+limit;
	}
	
	/**
	 * get the skip value and use default if an invalid value
	 * @param skip
	 * @return
	 */
	private String getSkip(Integer skip) {
		if (skip == null || skip < 0) {
			skip = defaultSkip;
		}
		return "skip="+skip.toString();
	}
	
	/**
	 * get a default error object when external services have a failure
	 * @return
	 */
	private FdaResponse getError() {
		FdaResponse fdaResponse = new FdaResponse();
		FdaError error = new FdaError();
		error.setCode("externalFailure");
		error.setMessage("Failed to get data from Open FDA");
		fdaResponse.setError(error);
		fdaResponse.setMeta(new FdaMeta());
		return fdaResponse;
	}
	
	/**
	 * Used to inject an instance of HttpOps for ease of unit testing
	 * @param httpOps
	 */
	public void setHttpOperations(HttpOps httpOps) {
		this.httpOps = httpOps;
	}
}

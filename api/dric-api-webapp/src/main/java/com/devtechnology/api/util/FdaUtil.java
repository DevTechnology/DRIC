package com.devtechnology.api.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.devtechnology.api.domain.FdaClassificationFilter;
import com.devtechnology.api.domain.FdaError;
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
	private static Integer defaultLimit = 10;
	private static Integer defaultSkip = 0;
	private HttpOps httpOps;
	
	/**
	 * RecallResponse object with the recent recall results
	 * @return
	 */
	public RecallResponse getRecentRecalls(Integer limit, Integer skip) {
		String today = getTodayYYYYMMDD();
		String lastMonth = getLastMonthYYYYMMDD();
		// search=status:Ongoing+AND+classification:Class+II
		String criteria = "search=report_date:["+lastMonth+"+TO+"+today+"]"+getLimit(limit)+getSkip(skip);
		String apiKey = getApiKey();
		FdaResponse fdaResponse = httpOps.getMappedFromUlr(baseUrl+criteria+apiKey, FdaResponse.class);
		if (fdaResponse == null) {
			fdaResponse = getError();
		}
		logger.info(new Gson().toJson(fdaResponse));
		RecallResponse result = mapResponse(fdaResponse);
		return result;
	}
	
	/**
	 * RecallResponse object with the recall results matching the given 'name' value
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
		logger.info(new Gson().toJson(fdaResponse));
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
	 * Map the 'search=value' fields to filter the data
	 * @param textFilter
	 * @param reportDate
	 * @param status
	 * @param classification
	 * @return
	 */
	public String mapSearchFilter(String textFilter, FdaReportDateFilter reportDate, FdaStatusFilter status, FdaClassificationFilter classification) {
		String r = "";
		List<String> filters = new ArrayList<String>();
		if (textFilter != null && !"".equals(textFilter.trim()) && !"undefined".equals(textFilter.trim())) {
			textFilter = "%22"+textFilter.replaceAll(" ", "+")+"%22";
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
					item.setRecall_initiation_date(fdaResult.getRecall_initiation_date());
					item.setState(fdaResult.getState());
					item.setEvent_id(fdaResult.getEvent_id());
					item.setProduct_type(fdaResult.getProduct_type());
					item.setProduct_description(fdaResult.getProduct_description());
					item.setCountry(fdaResult.getCountry());
					item.setCity(fdaResult.getCity());
					item.setRecalling_firm(fdaResult.getRecalling_firm());
					item.setReport_date(fdaResult.getReport_date());
					item.setVoluntary_mandated(fdaResult.getVoluntary_mandated());
					item.setClassification(fdaResult.getClassification());
					item.setCode_info(fdaResult.getCode_info());
					item.setInitial_firm_notification(fdaResult.getInitial_firm_notification());
					if (fdaResult.getOpenfda() != null) {
						productSet.addAll(fdaResult.getOpenfda().getGeneric_name());
						productSet.addAll(fdaResult.getOpenfda().getBrand_name());
						productSet.addAll(fdaResult.getOpenfda().getSubstance_name());
						ndcSet.addAll(fdaResult.getOpenfda().getProduct_ndc());
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
	 * Get the String representation needed for a date range from Open FDA for the value of today
	 * @return yyyyMMdd formatted value for today
	 */
	private String getTodayYYYYMMDD() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String s = formatter.format(c.getTime());
		return s;
	}
	
	/**
	 * Get the String representation needed for a date range from Open FDA for the value of the first day of last month
	 * @return yyyyMMdd formatted value for the first day of last month
	 */
	private String getLastMonthYYYYMMDD() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DATE, 1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String s = formatter.format(c.getTime());
		return s;
	}
	
	/**
	 * Get the system property, if set, of the Open FDA API key. property name is 'openFdaApiKey'
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
		return fdaResponse;
	}
	
	public void setHttpOperations(HttpOps httpOps) {
		this.httpOps = httpOps;
	}
}

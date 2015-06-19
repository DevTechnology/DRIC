package com.devtechnology.api.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.devtechnology.api.domain.FdaResponse;
import com.devtechnology.api.domain.FdaResults;
import com.devtechnology.api.domain.RecallItem;
import com.devtechnology.api.domain.RecallResponse;
import com.google.gson.Gson;

/**
 * Utility methods for getting data from Open FDA Enforcement API
 * @author jbnimble
 */
public class FdaUtil {
	private static Logger logger = Logger.getLogger(FdaUtil.class);
	private String baseUrl = "https://api.fda.gov/drug/enforcement.json?";
	private static Integer limit = 10;
	// TODO paginate results
	public RecallResponse getRecentRecalls() {
		String today = getTodayYYYYMMDD();
		String lastMonth = getLastMonthYYYYMMDD();
		String criteria = "search=report_date:["+lastMonth+"+TO+"+today+"]&limit="+limit;
		String apiKey = getApiKey();
		HttpOperations ops = new HttpOperations();
		String httpResultStr = ops.getFromUrl(baseUrl+criteria+apiKey);
		FdaResponse fdaResponse = new Gson().fromJson(httpResultStr, FdaResponse.class);
		logger.info(new Gson().toJson(fdaResponse));
		RecallResponse result = mapResponse(fdaResponse);
		return result;
	}
	public RecallResponse getRecalls(String name) {
		String searchValue = name.replaceAll(" ", "+");
		String criteria = "search="+searchValue+"&limit="+limit;
		String apiKey = getApiKey();
		HttpOperations ops = new HttpOperations();
		String httpResultStr = ops.getFromUrl(baseUrl+criteria+apiKey);
		FdaResponse fdaResponse = new Gson().fromJson(httpResultStr, FdaResponse.class);
		logger.info(new Gson().toJson(fdaResponse));
		RecallResponse result = mapResponse(fdaResponse);
		return result;
	}
	
	private RecallResponse mapResponse(FdaResponse fdaResponse) {
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
	private String getTodayYYYYMMDD() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String s = formatter.format(c.getTime());
		return s;
	}
	private String getLastMonthYYYYMMDD() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DATE, 1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String s = formatter.format(c.getTime());
		return s;
	}
	private String getApiKey() {
		String key = System.getProperty("openFdaApiKey");
		String s = "";
		if (key != null) {
			s = "&api_key="+key;
		}
		return s;
	}
}

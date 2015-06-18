package com.devtechnology.api.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.devtechnology.api.domain.FdaDrug;
import com.devtechnology.api.domain.FdaResponse;
import com.devtechnology.api.domain.FdaResults;
import com.devtechnology.api.domain.RecallItem;
import com.google.gson.Gson;

public class FdaUtil {
	Logger logger = Logger.getLogger(FdaUtil.class);
	// TODO change to using the 'enforcement' service and not the 'drug' service
	//https://api.fda.gov/drug/enforcement.json?search=report_date:[20040101+TO+20131231]&limit=10
	private String baseFdaDrugUrl = "https://api.fda.gov/drug/event.json?";
	
	public List<RecallItem> getRecentRecalls() {
		List<RecallItem> list = new ArrayList<>();
		String criteria = "search=receivedate:[20140601+TO+20150618]&limit=10";
		HttpOperations ops = new HttpOperations();
		String resultStr = ops.getFromUrl(baseFdaDrugUrl+criteria);
		FdaResponse response = new Gson().fromJson(resultStr, FdaResponse.class);
		logger.info(new Gson().toJson(response));
		if (response != null && !response.getResults().isEmpty()) {
			Set<String> products = new HashSet<String>();
			for (FdaResults result : response.getResults()) {
				RecallItem item = new RecallItem();
				if (result != null && result.getPatient() != null && !result.getPatient().getDrug().isEmpty()) {
					for (FdaDrug drug : result.getPatient().getDrug()) {
						if (drug != null) {
							if (drug.getMedicinalproduct() != null) {
								products.add(drug.getMedicinalproduct());
							}
							if (drug.getOpenfda() != null) {
								products.addAll(drug.getOpenfda().getGeneric_name());
								products.addAll(drug.getOpenfda().getBrand_name());
							}
						}
					}
				}
				item.getProduct().addAll(products);
				item.setReceivedate(result.getReceivedate());
				list.add(item);
			}
		}
//		logger.info(result);
		return list;
	}
}

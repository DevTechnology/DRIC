package com.devtechnology.api.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO representing DRIC response object
 * @author jbnimble
 *
 */
public class RecallItem {
	private String recall_number;
	private String reason_for_recall;
	private String status;
	private String distribution_pattern;
	private String product_quantity;
	private String recall_initiation_date;
	private String state;
	private String event_id;
	private String product_type;
	private String product_description;
	private String short_product_description;
	private String country;
	private String city;
	private String recalling_firm;
	private String report_date;
	private String voluntary_mandated;
	private String classification;
	private String code_info;
	private String initial_firm_notification;
	private List<String> product;
	private List<String> product_ndc;
	
	public String getRecall_number() {
		return recall_number;
	}
	public void setRecall_number(String recall_number) {
		this.recall_number = recall_number;
	}
	public String getReason_for_recall() {
		return reason_for_recall;
	}
	public void setReason_for_recall(String reason_for_recall) {
		this.reason_for_recall = reason_for_recall;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDistribution_pattern() {
		return distribution_pattern;
	}
	public void setDistribution_pattern(String distribution_pattern) {
		this.distribution_pattern = distribution_pattern;
	}
	public String getProduct_quantity() {
		return product_quantity;
	}
	public void setProduct_quantity(String product_quantity) {
		this.product_quantity = product_quantity;
	}
	public String getRecall_initiation_date() {
		return recall_initiation_date;
	}
	public void setRecall_initiation_date(String recall_initiation_date) {
		this.recall_initiation_date = recall_initiation_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	public String getShort_product_description() {
		return short_product_description;
	}
	public void setShort_product_description(String short_product_description) {
		this.short_product_description = short_product_description;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRecalling_firm() {
		return recalling_firm;
	}
	public void setRecalling_firm(String recalling_firm) {
		this.recalling_firm = recalling_firm;
	}
	public String getReport_date() {
		return report_date;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}
	public String getVoluntary_mandated() {
		return voluntary_mandated;
	}
	public void setVoluntary_mandated(String voluntary_mandated) {
		this.voluntary_mandated = voluntary_mandated;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getCode_info() {
		return code_info;
	}
	public void setCode_info(String code_info) {
		this.code_info = code_info;
	}
	public String getInitial_firm_notification() {
		return initial_firm_notification;
	}
	public void setInitial_firm_notification(String initial_firm_notification) {
		this.initial_firm_notification = initial_firm_notification;
	}
	public List<String> getProduct() {
		product = (product == null) ? new ArrayList<String>() : product;
		return product;
	}
	public void setProduct(List<String> product) {
		this.product = product;
	}
	public List<String> getProduct_ndc() {
		product_ndc = (product_ndc == null) ? new ArrayList<String>() : product_ndc;
		return product_ndc;
	}
	public void setProduct_ndc(List<String> product_ndc) {
		this.product_ndc = product_ndc;
	}
}

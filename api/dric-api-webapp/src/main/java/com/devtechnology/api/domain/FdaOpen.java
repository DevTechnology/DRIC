package com.devtechnology.api.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO representing FDA JSON object
 * @author jbnimble
 *
 */
public class FdaOpen {
	private List<String> product_ndc;
	private List<String> brand_name;
	private List<String> generic_name;
	private List<String> substance_name;
	private List<String> package_ndc;
	
	public List<String> getProduct_ndc() {
		product_ndc = (product_ndc == null) ? new ArrayList<String>() : product_ndc;
		return product_ndc;
	}
	public void setProduct_ndc(List<String> product_ndc) {
		this.product_ndc = product_ndc;
	}
	public List<String> getBrand_name() {
		brand_name = (brand_name == null) ? new ArrayList<String>() : brand_name;
		return brand_name;
	}
	public void setBrand_name(List<String> brand_name) {
		this.brand_name = brand_name;
	}
	public List<String> getGeneric_name() {
		generic_name = (generic_name == null) ? new ArrayList<String>() : generic_name;
		return generic_name;
	}
	public void setGeneric_name(List<String> generic_name) {
		this.generic_name = generic_name;
	}
	public List<String> getSubstance_name() {
		substance_name = (substance_name == null) ? new ArrayList<String>() : substance_name;
		return substance_name;
	}
	public void setSubstance_name(List<String> substance_name) {
		this.substance_name = substance_name;
	}
	public List<String> getPackage_ndc() {
		package_ndc = (package_ndc == null) ? new ArrayList<String>() : package_ndc;
		return package_ndc;
	}
	public void setPackage_ndc(List<String> package_ndc) {
		this.package_ndc = package_ndc;
	}
}

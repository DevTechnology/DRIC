package com.devtechnology.api.domain;

public class FdaDrug {
	private String medicinalproduct;
	private FdaOpen openfda;
	public String getMedicinalproduct() {
		return medicinalproduct;
	}
	public void setMedicinalproduct(String medicinalproduct) {
		this.medicinalproduct = medicinalproduct;
	}
	public FdaOpen getOpenfda() {
		return openfda;
	}
	public void setOpenfda(FdaOpen openfda) {
		this.openfda = openfda;
	}
}

package com.devtechnology.api.domain;

public class FdaResults {

	private String receivedate;
	private FdaPatient patient;
	public String getReceivedate() {
		return receivedate;
	}
	public void setReceivedate(String receivedate) {
		this.receivedate = receivedate;
	}
	public FdaPatient getPatient() {
		return patient;
	}
	public void setPatient(FdaPatient patient) {
		this.patient = patient;
	}
}

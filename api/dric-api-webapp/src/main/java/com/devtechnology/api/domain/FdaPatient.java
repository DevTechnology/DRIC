package com.devtechnology.api.domain;

import java.util.ArrayList;
import java.util.List;

public class FdaPatient {
	private List<FdaDrug> drug;

	public List<FdaDrug> getDrug() {
		drug = (drug == null) ? new ArrayList<FdaDrug>() : drug;
		return drug;
	}

	public void setDrug(List<FdaDrug> drug) {
		this.drug = drug;
	}
}

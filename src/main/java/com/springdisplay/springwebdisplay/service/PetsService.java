package com.springdisplay.springwebdisplay.service;

public class PetsService {

	public String apiEnv;

	public String getApiEnv() {
		return apiEnv;
	}

	public void setApiEnv(String apiEnv) {
		this.apiEnv = apiEnv;
	}

	@Override
	public String toString() {
		return "PetsService [apiEnv=" + apiEnv + "]";
	}
	
}

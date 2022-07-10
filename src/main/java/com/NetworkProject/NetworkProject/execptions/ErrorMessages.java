package com.NetworkProject.NetworkProject.execptions;

public enum ErrorMessages {

	NO_USER_FOUND("No User Found"),
	INVALID_INPUT("Input cannot be empty"),
	NO_DATA_FOUND("no data found");

	private String error;

	ErrorMessages(String error){
		this.setError(error);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}

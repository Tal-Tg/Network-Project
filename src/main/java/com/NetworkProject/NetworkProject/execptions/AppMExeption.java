package com.NetworkProject.NetworkProject.execptions;

public class AppMExeption extends Exception{

	public AppMExeption(ErrorMessages errorMessages) {
		super(errorMessages.getError());
	}


}

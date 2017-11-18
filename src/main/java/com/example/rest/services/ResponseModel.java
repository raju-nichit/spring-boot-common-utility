package com.example.rest.services;

public class ResponseModel {

	private Object object;
	private String status;
	private String message;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static ResponseModel getInstance() {
		ResponseModel response = new ResponseModel();
		response.setStatus("SUCCESS");
		return response;
	}

}

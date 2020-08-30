package com.joniks.loyalty.api.model;

import java.io.Serializable;

public class TransactionResult implements Serializable {
	private static final long serialVersionUID = -4701730501025433554L;
	private boolean success;
	private String message;
	private String type;
	private Object obj;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getObj() {
		return this.obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}

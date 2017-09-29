package com.feicent.zhang.util.http.cnblog;

import com.feicent.zhang.entity.BaseModel;

public class HttpClientResponseEntity extends BaseModel{

	private static final long serialVersionUID = -6897695035985414414L;
	
	private String responseCode;
	private String responseMsg;
	private String responseBody;
	private byte[] responseByteArray;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	public byte[] getResponseByteArray() {
		return responseByteArray;
	}
	public void setResponseByteArray(byte[] responseByteArray) {
		this.responseByteArray = responseByteArray;
	}
	
}

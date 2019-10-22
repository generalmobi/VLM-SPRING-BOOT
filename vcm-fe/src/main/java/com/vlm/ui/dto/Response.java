package com.vlm.ui.dto;

public class Response<T>
{
public static final int SUCCESS=2000;
public static final int ERROR=5000;
	
private int code;
private String message;
private T result;
public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public T getResult() {
	return result;
}
public void setResult(T result) {
	this.result = result;
}

public static <T>Response<T> instance(int code,String message,T result)
{
	Response<T> response = new Response<T>();
	response.code=code;
	response.message=message;
	response.result=result;
	return response;
}

public static <T>Response<T> instance(int code,String message)
{
	Response<T> response = new Response<T>();
	response.code=code;
	response.message=message;
 	return response;
}


}

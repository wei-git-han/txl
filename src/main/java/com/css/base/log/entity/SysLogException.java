package com.css.base.log.entity;

import java.util.Date;

public class SysLogException {

	private String exId; //

	private String exceptionName;
	private String stackTraceMsg;
	private String systemCode;
	private Date executeTime;

	public String getExId() {
		return exId;
	}

	public void setExId(String exId) {
		this.exId = exId;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public String getStackTraceMsg() {
		return stackTraceMsg;
	}

	public void setStackTraceMsg(String stackTraceMsg) {
		this.stackTraceMsg = stackTraceMsg;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

}

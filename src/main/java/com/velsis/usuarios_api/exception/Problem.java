package com.velsis.usuarios_api.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.context.i18n.LocaleContextHolder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class Problem implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6745892944858812119L;

	private Integer status;

	private LocalDateTime timestamp;

	private String type;

	private String title;

	private String detail;

	private String userMessage;

	private String developerMessage;

	public Problem(Integer status, String title, String detail, String userMessage, String developerMessage) {
		this.status = status;
		this.title = title;
		this.timestamp = LocalDateTime.now(LocaleContextHolder.getTimeZone().toZoneId());
		this.detail = detail;
		this.userMessage = userMessage;
		this.developerMessage = developerMessage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
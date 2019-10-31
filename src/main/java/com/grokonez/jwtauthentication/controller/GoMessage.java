package com.grokonez.jwtauthentication.controller;

import java.util.Arrays;

public class GoMessage {
	GoMessageType messageType;
	String[] args;
	String message;
	Integer code;



	public Integer getCode() {
		return code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GoMessage other = (GoMessage) obj;
		if (!Arrays.equals(args, other.args))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (messageType != other.messageType)
			return false;
		return true;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public GoMessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(GoMessageType messageType) {
		this.messageType = messageType;
		this.code = messageType.code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GoMessage(GoMessageType messageType) {
		this.messageType = messageType;
		this.code = messageType.code;
	}
	

	public GoMessage(GoMessageType messageType, String[] args) {
		this.messageType = messageType;
		this.args = args;
	}

	public GoMessage(GoMessageType messageType, String[] args, Integer code) {
		this.messageType = messageType;
		this.args = args;
		this.code = code;
	}
}
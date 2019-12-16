package com.ecommerce.globalExeption;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.controller.GoMessage;
import com.ecommerce.controller.GoMessageType;

public class GoException extends Exception {

	public List<GoMessage> getGoMessages() {
		return goMessages;
	}

	public void setGoMessages(List<GoMessage> goMessages) {
		this.goMessages = goMessages;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 8537595892723837714L;

	private List<GoMessage> goMessages;

	public GoException(List<GoMessage> goMessages) {
		this.goMessages = goMessages;
	}

	public GoException(GoMessage message) {
		goMessages = new ArrayList<>();
		goMessages.add(message);
	}

	public GoException(GoMessageType messageType) {
		goMessages = new ArrayList<>();
		goMessages.add(new GoMessage(messageType));
	}

	public GoException(GoMessageType messageType, String... args) {
		goMessages = new ArrayList<>();
		goMessages.add(new GoMessage(messageType, args, messageType.getCode()));
	}

}

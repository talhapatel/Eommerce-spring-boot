package com.grokonez.jwtauthentication.controller;

import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
@RestController
@Scope("request")
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BaseController {


	@Autowired
	ApiResponse apiResponse;

	@Autowired
	private MessageSource messageSource;

	public @ResponseBody ApiResponse renderResponse() {
		if (apiResponse.getStatus() == null)
			setResponseAsSuccess();
		populateMessages();
		// response.status = 200
		return apiResponse;
	}

	public void setData(String property, Object value) {
		apiResponse.setData(property, value);
	}

	private void setResponseAsSuccess() {
		apiResponse.setStatus("SUCCESS");
	}

	protected void addSuccess(GoMessage message) {
		apiResponse.messages.add(message);
	}

	protected void addSuccess(GoMessageType message) {
		apiResponse.messages.add(new GoMessage(message));
	}

	protected void addSuccess(GoMessageType messageType, String... args) {
		apiResponse.messages.add(new GoMessage(messageType, args, messageType.code));
	}

	protected void addFailure(GoMessage message) {
		apiResponse.messages.add(message);
		setResponseAsFailure();
	}

	public void addFailure(GoMessageType messageType) {
		apiResponse.messages.add(new GoMessage(messageType));
		setResponseAsFailure();
	}

	protected void addFailure(GoMessageType messageType, String... args) {
		apiResponse.messages.add(new GoMessage(messageType, args, messageType.code));
		setResponseAsFailure();
	}

	protected boolean addFailure(Errors errors) {
		if (!errors.hasErrors()) {
			return false;
		}
		for (ObjectError error : errors.getAllErrors()) {
			switch (error.getCode()) {
			case "NotNull":
				break;
			case "NotBlank":
				break;
			}
			addFailure(GoMessageType.EXCEPTION_OCCURED, error.getDefaultMessage());
		}
		return true;
	}

	protected void clearMessages() {
		apiResponse.messages.clear();
	}

	private void setResponseAsFailure() {
		apiResponse.setStatus("FAILURE");
	}

	private void populateMessages() {
		for (GoMessage msg : apiResponse.messages) {
			msg.setMessage(
					messageSource.getMessage(msg.messageType.toString(), msg.args, LocaleContextHolder.getLocale()));
		}
	}

	protected String getLabel(String label) {
		return messageSource.getMessage(label, null, LocaleContextHolder.getLocale());
	}

}

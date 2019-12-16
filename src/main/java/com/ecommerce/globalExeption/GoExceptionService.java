package com.ecommerce.globalExeption;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.annotation.RequestScope;

import com.ecommerce.controller.GoMessage;
import com.ecommerce.controller.GoMessageType;

@Scope("prototype")
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Service
public class GoExceptionService {

	@Override
	public String toString() {
		return "GoExceptionService [messages=" + messages + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
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
		GoExceptionService other = (GoExceptionService) obj;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;
		return true;
	}

	private List<GoMessage> messages;

	public GoExceptionService() {
		messages = new ArrayList<>();
	}
	
	public GoExceptionService addFailure(GoMessage message) {
		messages.add(message);
		return this;
	}

	public GoExceptionService addFailure(GoMessageType messageType) {
		messages.add(new GoMessage(messageType));
		return this;
	}

	public GoExceptionService addFailure(GoMessageType messageType, String... args) {
		messages.add(new GoMessage(messageType, args, messageType.getCode()));
		return this;
	}

	public boolean addFailure(Errors errors) {
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
	
	public void throwException() throws GoException {
		throw new GoException(messages);
	}

}

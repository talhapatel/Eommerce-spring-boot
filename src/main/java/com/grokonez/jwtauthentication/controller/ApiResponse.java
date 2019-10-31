package com.grokonez.jwtauthentication.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.context.WebApplicationContext;


@Component
@Scope("request")
public class ApiResponse {

    private String status;
    private HashMap<String, Object> data;
    List<GoMessage> messages;

    public List<GoMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<GoMessage> messages) {
		this.messages = messages;
	}
	
	public void clearMessages() {
		this.messages = new ArrayList<GoMessage>();
	}

	public ApiResponse() {
        data = new HashMap<String, Object>();
        messages = new ArrayList<GoMessage>();
    }
 
    public void setData(String property, Object value) {
        data.put(property, value);
    }

	public String getStatus() {
		return status;
	}
	
	public HashMap<String, Object> getData() {
		return data;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}



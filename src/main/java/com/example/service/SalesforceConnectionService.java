package com.example.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Config;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@Service
public class SalesforceConnectionService {

	@Autowired
	Config propertiesConfig;

	EnterpriseConnection connection;
	String authEndPoint = "https://login.salesforce.com/services/Soap/c/51.0";
	String authPassword = "";

	public EnterpriseConnection getConnection() {
		return connection;
	}
	
	@PostConstruct
	protected void doLogin() {
		System.out.println("***" + propertiesConfig.getAuthEndPoint());
		authEndPoint = propertiesConfig.getAuthEndPoint();
		authPassword = propertiesConfig.getPassword() + propertiesConfig.getToken();
		System.out.println("***" + propertiesConfig.getAuthName());
		System.out.println("***" + authPassword);

		login();

	}

	private boolean login() {
		boolean success = false;
		String username = propertiesConfig.getAuthName();
		// String password = "Sarc";
		// String username = propertiesConfig.getUsername();
		String password = authPassword;

		try {
			ConnectorConfig config = new ConnectorConfig();
			config.setUsername(username);
			config.setPassword(password);

			System.out.println("AuthEndPoint: " + authEndPoint);
			config.setAuthEndpoint(authEndPoint);

			connection = new EnterpriseConnection(config);
			printUserInfo(config);

			success = true;
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}

		return success;
	}

	private void printUserInfo(ConnectorConfig config) {
		try {
			GetUserInfoResult userInfo = connection.getUserInfo();

			System.out.println("\nLogging in ...\n");
			System.out.println("UserID: " + userInfo.getUserId());
			System.out.println("User Full Name: " + userInfo.getUserFullName());
			System.out.println("User Email: " + userInfo.getUserEmail());
			System.out.println();
			System.out.println("SessionID: " + config.getSessionId());
			System.out.println("Auth End Point: " + config.getAuthEndpoint());
			System.out.println("Service End Point: " + config.getServiceEndpoint());
			System.out.println();
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}

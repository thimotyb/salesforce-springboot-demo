package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

@RestController
public class AccountController {

	@Autowired
	Config propertiesConfig;
	
   EnterpriseConnection connection;
   String authEndPoint = "https://login.salesforce.com/services/Soap/c/51.0";
   String authPassword = "";

   
    @PostConstruct
    protected void doLogin() {
    	System.out.println("***"+propertiesConfig.getAuthEndPoint());
    	authEndPoint = propertiesConfig.getAuthEndPoint();
    	authPassword = propertiesConfig.getPassword()+propertiesConfig.getToken();
    	System.out.println("***"+propertiesConfig.getAuthName());    	
    	System.out.println("***"+authPassword);
    	
    	login();    	
    	
    }

	@GetMapping("/account")
	private List<AccountResult> querySample() {
				  
		  List<AccountResult> results = new ArrayList<>();
	      String soqlQuery = "SELECT Name, BillingCity FROM Account";
	      try {
	         QueryResult qr = connection.query(soqlQuery);
	         boolean done = false;

	         if (qr.getSize() > 0) {
	            System.out.println("\nLogged-in user can see "
	                  + qr.getRecords().length + " contact records.");

	            while (!done) {
	               System.out.println("");
	               SObject[] records = qr.getRecords();
	               for (int i = 0; i < records.length; ++i) {
	                  Account account = (Account) records[i];
	                  String fName = account.getName();
	                  String lName = account.getBillingCity();

	                  if (fName == null) {
	                     System.out.println("Account " + (i + 1) + ": " + lName);
	                  } else {
	                     System.out.println("Account " + (i + 1) + ": " + fName
	                           + " " + lName);
	                  }
	                  results.add(new AccountResult(lName, fName));
	               }

	               if (qr.isDone()) {
	                  done = true;
	               } else {
	                  qr = connection.queryMore(qr.getQueryLocator());
	               }
	            }
	         } else {
	            System.out.println("No records found.");
	         }
	      } catch (ConnectionException ce) {
	         ce.printStackTrace();
	      } finally {
	    	  return results;
	      }
	}
	      
	      private boolean login() {
	          boolean success = false;
	          String username = propertiesConfig.getAuthName();
	          //String password = "Sarc";
	          //String username = propertiesConfig.getUsername();
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
	             System.out
	                   .println("Service End Point: " + config.getServiceEndpoint());
	             System.out.println();
	          } catch (ConnectionException ce) {
	             ce.printStackTrace();
	          }
	       }
}

class AccountResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private String Name;
	private String BillingCity;
	public AccountResult(String Name, String BillingCity) {
		this.Name = Name; this.BillingCity = BillingCity;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getBillingCity() {
		return BillingCity;
	}
	public void setBillingCity(String billingCity) {
		BillingCity = billingCity;
	}
	
	
}
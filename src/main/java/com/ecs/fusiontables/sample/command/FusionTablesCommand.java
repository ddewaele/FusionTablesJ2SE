package com.ecs.fusiontables.sample.command;

import java.io.IOException;

import com.ecs.fusiontables.sample.Constants;
import com.ecs.fusiontables.sample.CsvParser;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin.Response;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;

public abstract class FusionTablesCommand {
	
	protected static final String FUSION_TABLES_API_QUERY = "https://www.google.com/fusiontables/api/query";
	 protected static final String SHOW_TABLES = "SHOW TABLES";
	  
	 private static final HttpTransport transport = new ApacheHttpTransport();
	  
	 public static final HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
	  
	  
	 private String sql;
	 
	 public FusionTablesCommand(String sql) {
	  this.sql = sql;
	 
	 }
	 
	 protected abstract HttpRequest getHttpRequest() throws Exception;
	 
	 public DataList execute() throws Exception {
	  HttpRequest httpRequest = getHttpRequest();
	  httpRequest.url.put("sql", sql);
	  return httpRequest.execute().parseAs(DataList.class);
	 }
	 
	 public static HttpRequestFactory createRequestFactory(
			   final HttpTransport transport) {
			   
			  return transport.createRequestFactory(new HttpRequestInitializer() {
			   public void initialize(HttpRequest request) {
			    GoogleHeaders headers = new GoogleHeaders();
			    headers.setApplicationName("Google-FusionTables/1.0");
			    request.headers=headers;
			    request.addParser(new CsvParser());
			    try {
			     authorizeTransport(request);
			    } catch (Exception e) {
			     e.printStackTrace();
			    }
			   }
			  });
	 }
	 
	   private static void authorizeTransport(HttpRequest request) throws HttpResponseException, IOException {
	    // authenticate with ClientLogin
	    ClientLogin authenticator = new ClientLogin();
	    authenticator.authTokenType = Constants.AUTH_TOKEN_TYPE;
	    authenticator.username = Constants.USERNAME;
	    authenticator.password = Constants.PASSWORD;
	    authenticator.transport = transport;
	    try {
	     Response response = authenticator.authenticate();
	     request.headers.authorization=response.getAuthorizationHeaderValue();
	    } catch (HttpResponseException e) {
	     e.printStackTrace();
	    } catch (IOException e) {
	     e.printStackTrace();
	    }  
	   }

	 
			   
			   
}

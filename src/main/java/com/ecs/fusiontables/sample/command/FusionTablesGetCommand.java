package com.ecs.fusiontables.sample.command;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;

public class FusionTablesGetCommand extends FusionTablesCommand{

	private HttpRequest request; 
	
	public FusionTablesGetCommand(HttpTransport transport,String sql) {
		super(transport,sql);
		this.request = transport.buildGetRequest();
		//this.request.setUrl("https://www.google.com/fusiontables/api/query");
	}
	
	@Override
	protected HttpRequest getHttpRequest() {
		return this.request;
	}

}

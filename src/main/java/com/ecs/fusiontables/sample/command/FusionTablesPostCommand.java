package com.ecs.fusiontables.sample.command;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;

public class FusionTablesPostCommand extends FusionTablesCommand {

	private HttpRequest request;
	
	public FusionTablesPostCommand(HttpTransport transport,String sql) {
		super(transport,sql);
		this.request = transport.buildPostRequest();
		this.request.setUrl(FUSION_TABLES_API_QUERY);
	}
	
	protected HttpRequest getHttpRequest() {
		return this.request;
	}
}

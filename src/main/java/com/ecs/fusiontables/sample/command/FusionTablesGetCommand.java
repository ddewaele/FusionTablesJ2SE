package com.ecs.fusiontables.sample.command;

import com.ecs.fusiontables.sample.FusionTablesSample;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;

public class FusionTablesGetCommand extends FusionTablesCommand {

	private HttpRequest request;

	public FusionTablesGetCommand(HttpTransport transport, String sql)
			throws Exception {
		super(transport, sql);
//		this.request = FusionTablesSample.createRequestFactory(transport)
//				.buildGetRequest(new GenericUrl(FUSION_TABLES_API_QUERY));
		 this.request = transport.buildGetRequest();
		 this.request.setUrl("https://www.google.com/fusiontables/api/query");
	}

	@Override
	protected HttpRequest getHttpRequest() {
		return this.request;
	}

}

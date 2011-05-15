package com.ecs.fusiontables.sample.command;

import com.ecs.fusiontables.sample.FusionTablesSample;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;

public class FusionTablesGetCommand extends FusionTablesCommand{

	public FusionTablesGetCommand(String sql) {
		super(sql);
	}

	@Override
	protected HttpRequest getHttpRequest() throws Exception {
		return FusionTablesSample.httpRequestFactory.buildGetRequest(new GenericUrl(FUSION_TABLES_API_QUERY));
	}

}

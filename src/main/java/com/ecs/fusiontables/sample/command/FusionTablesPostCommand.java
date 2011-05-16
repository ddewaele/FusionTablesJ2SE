package com.ecs.fusiontables.sample.command;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;

public class FusionTablesPostCommand extends FusionTablesCommand {

	public FusionTablesPostCommand(String sql) {
		super(sql);
	}

	@Override
	protected HttpRequest getHttpRequest() throws Exception {
		return httpRequestFactory.buildPostRequest(new GenericUrl(FUSION_TABLES_API_QUERY),null);
	}
}

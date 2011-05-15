package com.ecs.fusiontables.sample.command;

import com.google.api.client.http.HttpRequest;

public abstract class FusionTablesCommand {
	
	protected static final String FUSION_TABLES_API_QUERY = "https://www.google.com/fusiontables/api/query";
	protected static final String SHOW_TABLES="SHOW TABLES";
	private String sql;
	
	public FusionTablesCommand(String sql) {
		this.sql=sql;
		
	}
	
	protected abstract HttpRequest getHttpRequest() throws Exception;
	
	public DataList execute() throws Exception {
		HttpRequest httpRequest = getHttpRequest();	
		httpRequest.url.put("sql", sql);
		return httpRequest.execute().parseAs(DataList.class);
	}
}

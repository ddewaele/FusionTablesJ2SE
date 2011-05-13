package com.ecs.fusiontables.sample.command;

import com.ecs.fusiontables.sample.CsvParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;

public abstract class FusionTablesCommand {
	
	protected static final String FUSION_TABLES_API_QUERY = "https://www.google.com/fusiontables/api/query";
	protected HttpTransport transport;
	private String sql;
	
	protected static final String SHOW_TABLES="SHOW TABLES";
	
	
	public FusionTablesCommand(HttpTransport transport,String sql) {
		this.transport=transport;
		this.sql=sql;
		
	}
	
	protected abstract HttpRequest getHttpRequest();
	
	public <T> T execute(Class<T> type) throws Exception {
		getHttpRequest().setUrl(FUSION_TABLES_API_QUERY);
		getHttpRequest().addParser(new CsvParser());
		getHttpRequest().url.put("sql", this.sql);
		return getHttpRequest().execute().parseAs(type);
	}
}

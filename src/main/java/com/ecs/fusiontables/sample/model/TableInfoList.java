package com.ecs.fusiontables.sample.model;

import java.util.List;

import com.google.api.client.util.Key;

public class TableInfoList {

	@Key
	public List<TableInfo> records;
	
	public static class TableInfo {

		@Key(value="table id")
		public String table_id;
		
		@Key
		public String name;
	}
}

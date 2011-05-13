package com.ecs.fusiontables.sample.model;

import java.util.List;

import com.google.api.client.util.Key;

public class TableList {

	@Key
	public List<Table> records;
	
	public static class Table {

		@Key
		public String tableid;
		
	}
}

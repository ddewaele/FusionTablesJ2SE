package com.ecs.fusiontables.sample.model;

import java.util.List;

import com.google.api.client.util.Key;

public class TableRecords {

	@Key
	public List<TableRecord> records;

	public static class TableRecord {

		@Key
		public String description;
		
		@Key
		public String name;
		
		@Key
		public String accuracy;
		
		@Key
		public String timestamp;
		
		@Key
		public String geometry;
	}
}

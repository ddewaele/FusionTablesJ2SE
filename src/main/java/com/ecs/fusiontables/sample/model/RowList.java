package com.ecs.fusiontables.sample.model;

import java.util.List;

import com.google.api.client.util.Key;

public class RowList {

	@Key
	public List<Row> records;
	
	public static class Row {

		@Key
		public String rowid;
	}
}

package com.ecs.fusiontables.sample.command;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;

public class DataList {

	@Key
	private List<DataObject> records = new ArrayList<DataObject>();
	
	public List<DataObject> getRecords() {
		return records;
	}
	
}

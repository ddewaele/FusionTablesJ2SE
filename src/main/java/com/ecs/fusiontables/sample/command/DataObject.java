package com.ecs.fusiontables.sample.command;

import com.google.api.client.util.Key;

public class DataObject {

	@Key(value="table id")
	public String table_id;
	
	@Key
	public String tableid;
	
	@Key
	public String name;

	@Key
	public String rowid;
	

}

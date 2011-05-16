package com.ecs.fusiontables.sample;

import java.io.IOException;
import java.util.List;

import com.ecs.fusiontables.sample.command.DataList;
import com.ecs.fusiontables.sample.command.DataObject;
import com.ecs.fusiontables.sample.command.FusionTablesCommand;
import com.ecs.fusiontables.sample.command.FusionTablesGetCommand;
import com.ecs.fusiontables.sample.command.FusionTablesPostCommand;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.MethodOverride;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin.Response;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;

public class FusionTablesSample {

	private static final String SQL_SHOW_TABLES = "SHOW TABLES";
	private static final String SQL_CREATE_TABLE = "CREATE TABLE 'TEST_TABLE' (description: STRING,name: STRING,accuracy: NUMBER, timestamp: NUMBER, geometry: LOCATION);";

	
	public static void main(String[] args) throws Exception {
		
		FusionTablesSample sample = new FusionTablesSample();

		try {
			sample.showTables();
			String tableId = sample.createTable();
			sample.showTables();
			sample.selectFromTable(tableId);
			sample.insertIntoTable(tableId);
			sample.selectFromTable(tableId);
			sample.dropTable(tableId);

		} catch (HttpResponseException e) {
			System.err.println(e.response.parseAsString());
			throw e;
		} 
	}
	

	
	public void showTables() throws Exception {
		// Showing all tables.
		System.out.println(" +++ Begin Show Tables");
		FusionTablesCommand showTablesCommand = new FusionTablesGetCommand(SQL_SHOW_TABLES);
		DataList dataList = showTablesCommand.execute();
		List<DataObject> records = dataList.getRecords();
		if (records.size()==0) {
			System.out.println("No tables found.");
		} else {
			for (DataObject dataObject : records) {
				System.out.println("Found table : " + dataObject.name + "(" + dataObject.table_id + ")");
			}
		}
		System.out.println("");
	}
	
	public String createTable() throws Exception {
		System.out.println(" +++ Create Table");
		FusionTablesCommand createTableCommand = new FusionTablesPostCommand(SQL_CREATE_TABLE);
		DataList list = createTableCommand.execute();
		System.out.println("Table with ID = " + list.getRecords().get(0).tableid + " created");
		System.out.println("");
		return list.getRecords().get(0).tableid;
	}
	
	public void insertIntoTable(String tableId) throws Exception {
		// Inserting records in a table.
		System.out.println(" +++ Insert into Tables");
		String point = "<Point><coordinates>3.517819,50.962329,0.0</coordinates></Point>";
		String sql = "INSERT INTO "
				+ tableId
				+ " (description, name,accuracy,timestamp,geometry) VALUES ('the description','the name',30,"
				+ System.currentTimeMillis() + ",'" + point + "');";
		FusionTablesCommand insertTableCommand = new FusionTablesPostCommand( sql);
		DataList dataList = insertTableCommand.execute();
		System.out.println("Record inserted with rowID : " + dataList.getRecords().get(0).rowid);
		System.out.println("");

	}
	
	public void selectFromTable(String tableId) throws Exception {
		System.out.println(" +++ Select from Tables");
		FusionTablesCommand getTableCommand = new FusionTablesGetCommand("SELECT description, name,accuracy,timestamp,geometry FROM "+ tableId);
		DataList dataList = getTableCommand.execute();
		List<DataObject> records = dataList.getRecords();
		if (records.size()==0) {
			System.out.println("No records found in table " + tableId);
		} else {
			for (DataObject dataObject : records) {
				System.out.println("Found record : " + dataObject.name);
			}
		}
		System.out.println("");
	}
	
	public void dropTable(String tableId) throws Exception {
		System.out.println(" +++ Drop Tables");
		 FusionTablesCommand dropTableCommand = new FusionTablesPostCommand("DROP TABLE " + tableId);
		 dropTableCommand.execute();
		 System.out.println("");
	}


}

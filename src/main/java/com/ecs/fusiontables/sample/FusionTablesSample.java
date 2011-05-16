package com.ecs.fusiontables.sample;

import java.util.List;

import com.ecs.fusiontables.sample.command.FusionTablesCommand;
import com.ecs.fusiontables.sample.command.FusionTablesGetCommand;
import com.ecs.fusiontables.sample.command.FusionTablesPostCommand;
import com.ecs.fusiontables.sample.model.RowList;
import com.ecs.fusiontables.sample.model.RowList.Row;
import com.ecs.fusiontables.sample.model.TableInfoList;
import com.ecs.fusiontables.sample.model.TableInfoList.TableInfo;
import com.ecs.fusiontables.sample.model.TableList.Table;
import com.ecs.fusiontables.sample.model.TableRecords;
import com.google.api.client.http.HttpResponseException;

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
			System.err.println("An exception occured : "+ e.response.parseAsString());
			throw e;
		} 
	}
	

	
	public void showTables() throws Exception {
		// Showing all tables.
		System.out.println(" +++ Begin Show Tables\n");
		FusionTablesCommand showTablesCommand = new FusionTablesGetCommand(SQL_SHOW_TABLES);
		TableInfoList tableList = showTablesCommand.execute(TableInfoList.class);
		List<TableInfo> records = tableList.records;
		if (records.size()==0) {
			System.out.println("No tables found.");
		} else {
			for (TableInfo table : records) {
				System.out.println("Found table : " + table.name + "(" + table.table_id + ")");
			}
		}
		System.out.println("");
	}
	
	public String createTable() throws Exception {
		System.out.println(" +++ Create Table\n");
		FusionTablesCommand createTableCommand = new FusionTablesPostCommand(SQL_CREATE_TABLE);
		Table table = createTableCommand.execute(Table.class);
		System.out.println("Table with ID = " + table.tableid + " created");
		System.out.println("");
		return table.tableid;
	}
	
	public void insertIntoTable(String tableId) throws Exception {
		System.out.println(" +++ Insert into Tables\n");
		String point = "<Point><coordinates>3.517819,50.962329,0.0</coordinates></Point>";
		String sql1 = "INSERT INTO "
				+ tableId
				+ " (name,description, accuracy,timestamp,geometry) VALUES ('the name','the description',30,"
				+ System.currentTimeMillis() + ",'" + point + "');";
		String sql2 = "INSERT INTO "
			+ tableId
			+ " (name,¨description, accuracy,timestamp,geometry) VALUES ('the name2','the description2',50,"
			+ System.currentTimeMillis() + ",'" + point + "');";
		
		FusionTablesCommand insertTableCommand = new FusionTablesPostCommand(sql1 + sql2);
		RowList rowList = insertTableCommand.execute(RowList.class);
		if (rowList.records.size()==0) {
			System.out.println("No records inserted.");
		} else {
			for (Row row : rowList.records) {
				System.out.println("record inserted : " + row.rowid);
			}
		}
		System.out.println("");

	}
	
	public void selectFromTable(String tableId) throws Exception {
		System.out.println(" +++ Select from Tables\n");
		FusionTablesCommand getTableCommand = new FusionTablesGetCommand("SELECT description, name,accuracy,timestamp,geometry FROM "+ tableId);
		TableRecords tableRecords = getTableCommand.execute(TableRecords.class);
		if (tableRecords.records.size()==0) {
			System.out.println("No records found in table " + tableId);
		} else {
			for (TableRecords.TableRecord tableRecord : tableRecords.records) {
				System.out.println("Found record : " + tableRecord.name);
			}
		}
		System.out.println("");
	}
	
	public void dropTable(String tableId) throws Exception {
		System.out.println(" +++ Drop Tables");
		 FusionTablesCommand dropTableCommand = new FusionTablesPostCommand("DROP TABLE " + tableId);
		 dropTableCommand.execute(null);
	}

	

}

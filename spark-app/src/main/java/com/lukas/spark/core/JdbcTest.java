package com.lukas.spark.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JdbcTest {
	
	private String dbname = "sparkdb";
	private String hostname = "localhost";
	private String port = "5432";
	
	private String username = "sparkuser";
	private String password = "asd123";		
	
	private Connection connection = null;
	private Statement statement = null;
	
	final private String url;
	
	public JdbcTest() {
		url = createDbUrl();
	}
	
	public void runTest() {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver loaded: OK!");
			connection = 
					DriverManager.getConnection(url, username, password );
			System.out.println("Connected to database: OK!");
			statement = connection.createStatement();
			System.out.println("Creating statement: OK!");
			
			// DROP TABLE
			String commandDropTable = deleteTableCommand();
			statement.execute(commandDropTable);
			
			//CREATE TABLE
			String commandCreate = createTableCommand();
			statement.execute(commandCreate);
			
			//INSERT SOME DATA
			String commandInsert = insertDataCommand("Łukasz", 22);
			statement.execute(commandInsert);
			commandInsert = insertDataCommand("Marta", 17);
			statement.execute(commandInsert);
			commandInsert = insertDataCommand("Grzegorz", 13);
			statement.execute(commandInsert);
			commandInsert = insertDataCommand("Joanna", 51);
			statement.execute(commandInsert);
			commandInsert = insertDataCommand("Marcin", 32);
			statement.execute(commandInsert);
			commandInsert = insertDataCommand("Justyna", 43);
			statement.execute(commandInsert);
			
		} catch (ClassNotFoundException e) {
			System.out.println("Driver loaded: ERROR!");
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Sql components: ERROR!");
			//e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
				System.out.println("Closing components: OK!");
			} catch (SQLException e) {
				System.out.println("Closing components: ERROR!");
			}
		}
		
	}
	
	private String createDbUrl() {
		StringBuilder urlBuilder = new StringBuilder()
				.append("jdbc:postgresql://")
				.append(hostname)
				.append(":")
				.append(port)
				.append("/")
				.append(dbname);
		return urlBuilder.toString();
	}
	
	private String deleteTableCommand() {
		return "DROP TABLE person";
	}
	
	private String createTableCommand() {
		StringBuilder commandBuilder = new StringBuilder()
				.append("CREATE TABLE person")
				.append("(id bigserial primary key,")
				.append("name varchar(50),")
				.append("age integer)");
		return commandBuilder.toString();
	}
	
	private String insertDataCommand(String name, int age) {
		StringBuilder commandBuilder = new StringBuilder()
				.append("INSERT INTO person (name, age) VALUES ")
				.append("('" + name +"', ")
				.append(age)
				.append(")");
		return commandBuilder.toString();
	}

	
	public static void main(String[] args) {
		JdbcTest jdbcTest = new JdbcTest();
		jdbcTest.runTest();		
	}
}

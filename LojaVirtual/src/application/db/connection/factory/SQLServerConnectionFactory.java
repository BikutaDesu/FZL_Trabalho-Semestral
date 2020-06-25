package application.db.connection.factory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class SQLServerConnectionFactory {

	private String user;
	private String pass;
	private String database;
	private String server;
	private String port;
	private String url;

	public SQLServerConnectionFactory() throws IOException, ParseException {
		JSONObject obj;
		obj = parseFile(getFile("./src/application/db/connection/database-properties.json"));
		this.user = (String) obj.get("user");
		this.pass = (String) obj.get("pass");
		this.database = (String) obj.get("database");
		this.server = (String) obj.get("server");
		this.port = (String) obj.get("port");

		this.url = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;", this.server, this.port, this.database);
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, pass);
	}

	private FileReader getFile(String path) throws IOException {
		return new FileReader(path);
	}

	private JSONObject parseFile(FileReader file) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(file);
		return (JSONObject) obj;
	}

}

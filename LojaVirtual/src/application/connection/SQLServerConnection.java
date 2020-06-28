package application.connection;

import java.sql.Connection;

public class SQLServerConnection implements application.connection.Connection{

	private static final String URL = 
			"jdbc:sqlserver://localhost:1433;databaseName=testepoo;";
	private static final String USER = "sa";
	private static final String PASS = "VN@123limao";
	
	@Override
	public void connect() {
//		try {		
//			con = DriverManager.getConnection(URL, USER, PASS);
//			System.out.println("Conexão feita com sucesso!");
//		} catch (SQLException e) {
//			System.out.println("Erro ao conectar com banco");
//			e.printStackTrace();
//		}
	}
	
}

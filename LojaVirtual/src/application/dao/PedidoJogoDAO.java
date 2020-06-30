package application.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.db.connection.factory.SQLServerConnectionFactory;
import application.model.Jogo;
import application.model.Pedido;

public class PedidoJogoDAO {
	
	private Connection con;
	private SQLServerConnectionFactory factory;
	
	public PedidoJogoDAO(){
		try {
			factory = new SQLServerConnectionFactory();
			con = factory.getConnection();
		} catch (SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(Pedido pedido, Jogo jogo) throws SQLException {
		String sql = "INSERT INTO jogoPedido VALUES (?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, pedido.getID());
		ps.setInt(2, jogo.getID());
		
		ps.execute();
		ps.close();
	}
	
	public void delete(Pedido pedido) throws SQLException {
		String sql = "DELETE jogoPedido WHERE pedidoCodigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, pedido.getID());
		
		ps.execute();
		ps.close();
	}
	
	public void delete(Jogo jogo) throws SQLException {
		String sql = "DELETE jogoPedido WHERE jogoCodigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());
		
		ps.execute();
		ps.close();
	}
}

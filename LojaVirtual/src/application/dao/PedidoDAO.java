package application.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.db.connection.factory.SQLServerConnectionFactory;
import application.model.Funcionario;
import application.model.Pedido;
import application.model.Usuario;
import jdk.nashorn.internal.runtime.ListAdapter;

public class PedidoDAO implements IPedidoDAO {

	private Connection con;
	private SQLServerConnectionFactory factory;
	
	public PedidoDAO() throws SQLException, IOException, ParseException {
		factory = new SQLServerConnectionFactory();
		con = factory.getConnection();
	}
	
	@Override
	public void insert(Pedido pedido) throws SQLException {
		String sql = "INSERT INTO pedidos VALUES (?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setDate(1,(Date) pedido.getDataPedido());
		ps.setString(2, pedido.getUsuarioPedido().getCPF());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Pedido pedido) throws SQLException {
		String sql = "DELETE pedidos WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, pedido.getID());
		
		ps.execute();
		ps.close();
	}

	@Override
	public Pedido select(Pedido pedido) throws SQLException {
		String sql = "SELECT * FROM pedidos p "
				+ "INNER JOIN usuarios u "
				+ "ON p.usuarioCPF = u.CPF "
				+ "WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, pedido.getID());

		int i = 0;
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			pedido.setID(rs.getInt("codigo"));
			pedido.setDataPedido(rs.getDate("dataPedido"));
			
			Usuario usuario = new Usuario();
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			usuario.setTipoUsuario(rs.getInt("tipoUsuario"));
			
			pedido.setUsuarioPedido(usuario);
			
			i++;
		}
		
		if (i == 0) {
			pedido = new Pedido();
		}
		
		rs.close();
		ps.close();
		return pedido;
	}

	@Override
	public List<Pedido> selectAll() throws SQLException {
		String sql = "SELECT * FROM pedidos p "
				+ "INNER JOIN usuarios u "
				+ "ON p.usuarioCPF = u.CPF ";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		List<Pedido> listaPedidos = new ArrayList<Pedido>();
		
		while (rs.next()) {
			Pedido pedido = new Pedido();
			pedido.setID(rs.getInt("codigo"));
			pedido.setDataPedido(rs.getDate("dataPedido"));
			
			Usuario usuario = new Usuario();
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			usuario.setTipoUsuario(rs.getInt("tipoUsuario"));
			
			pedido.setUsuarioPedido(usuario);
			
			listaPedidos.add(pedido);
			
		}
		
		return listaPedidos;
	}

}

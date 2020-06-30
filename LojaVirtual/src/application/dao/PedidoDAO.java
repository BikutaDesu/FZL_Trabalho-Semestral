package application.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.db.connection.factory.SQLServerConnectionFactory;
import application.model.Jogo;
import application.model.Pedido;
import application.model.Usuario;

public class PedidoDAO implements IPedidoDAO {

	private Connection con;
	private SQLServerConnectionFactory factory;
	private PedidoJogoDAO pedidoJogoDAO = new PedidoJogoDAO();
	
	public PedidoDAO(){
		try {
			factory = new SQLServerConnectionFactory();
			con = factory.getConnection();
		} catch (SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insert(Pedido pedido) throws SQLException {
		String sql = "INSERT INTO pedidos VALUES (?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setDate(1, java.sql.Date.valueOf(pedido.getDataPedido()));
		ps.setString(2, pedido.getUsuarioPedido().getCPF().replaceAll("\\D", ""));
		ps.execute();
		ps.close();
		
		sql = "SELECT IDENT_CURRENT('pedidos') AS codigo";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		if(rs.next()) {
			pedido.setID(rs.getInt("codigo"));
			rs.close();
			ps.close();
			
			List<Jogo> jogos = new ArrayList<Jogo>();
			jogos.addAll(pedido.getJogos());
			for(Jogo j : jogos) {
				pedidoJogoDAO.insert(pedido, j);
			}
		}		
	}

	@Override
	public void delete(Pedido pedido) throws SQLException {
		PedidoJogoDAO pedidoJogoDAO = new PedidoJogoDAO();
		pedidoJogoDAO.delete(pedido);
		
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
			pedido.setDataPedido(rs.getDate("dataPedido").toLocalDate());
			
			Usuario usuario = new Usuario();
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			
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
	
	public List<Pedido> selectPedidos(Usuario usuario) throws SQLException {
		String sql = "SELECT jp.pedidoCodigo, j.nome, p.dataPedido, j.preco " + 
				"FROM usuarios u INNER JOIN pedidos p " + 
				"ON u.CPF = p.usuarioCPF " + 
				"INNER JOIN jogoPedido jp " + 
				"ON jp.pedidoCodigo = p.codigo " + 
				"INNER JOIN jogos j " + 
				"ON j.codigo = jp.jogoCodigo " + 
				"WHERE u.CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getCPF().replaceAll("\\D", ""));

		ResultSet rs = ps.executeQuery();
		
		List<Pedido> listaPedidos = new ArrayList<Pedido>();
		
		while (rs.next()) {
			Pedido pedido = new Pedido();
			Jogo jogo = new Jogo();
			
			pedido.setID(rs.getInt("pedidoCodigo"));
			pedido.setDataPedido(rs.getDate("dataPedido").toLocalDate());			
			jogo.setNome(rs.getString("nome"));
			jogo.setPreco(rs.getFloat("preco"));
			pedido.addJogo(jogo);
			
			listaPedidos.add(pedido);
		}
		
		ps.close();
		rs.close();
		return listaPedidos;
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
			pedido.setDataPedido(rs.getDate("dataPedido").toLocalDate());
			
			Usuario usuario = new Usuario();
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			
			pedido.setUsuarioPedido(usuario);
			
			listaPedidos.add(pedido);
			
		}
		
		return listaPedidos;
	}

}

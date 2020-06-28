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
import application.model.Categoria;
import application.model.Jogo;

public class JogoCategoriaDAO {
	private SQLServerConnectionFactory factory;
	private Connection con;

	public JogoCategoriaDAO() {
		try {
			factory = new SQLServerConnectionFactory();
			con = factory.getConnection();
		} catch (SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Jogo jogo) throws SQLException {
		String sql = "DELETE jogoCategoria WHERE jogoCodigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());

		ps.execute();
		ps.close();
	}

	public void insert(Jogo jogo, Categoria categoria) throws SQLException {
		String sql = "INSERT INTO jogoCategoria (jogoCodigo, categoriaCodigo) VALUES(?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());
		ps.setInt(2, categoria.getID());
		ps.execute();
		ps.close();
	}

	public void update(Jogo jogo) throws SQLException {
		List<Categoria> categoriaNaoCadastrados = new ArrayList<Categoria>();
		categoriaNaoCadastrados.addAll(jogo.getCategoria());

		for(Categoria c : categoriaNaoCadastrados) {
			if(!select(jogo,c)) {
				insert(jogo,c);
			}
		}
	}

	public boolean select(Jogo jogo, Categoria c) throws SQLException {
		String sql = "SELECT * " + 
				"FROM jogoCategoria " +  
				"WHERE jogoCodigo = ? AND categoriaCodigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());
		ps.setInt(2, c.getID());
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			rs.close();
			ps.close();
			return true;
		}else {
			rs.close();
			ps.close();
			return false;
		}
	}

	public List<Categoria> selectAll(Jogo jogo) throws SQLException {
		String sql = "SELECT c.codigo, c.nome " + 
				"FROM categorias c INNER JOIN jogoCategoria jc " + 
				"ON c.codigo = jc.categoriaCodigo " + 
				"INNER JOIN jogos j " + 
				"ON j.codigo = jc.jogoCodigo " + 
				"WHERE j.codigo like ? ";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setLong(1, jogo.getID());
		ResultSet rs = ps.executeQuery();

		List<Categoria> listaCategoria = new ArrayList<Categoria>();

		while (rs.next()) {
			Categoria categoria = new Categoria();
			categoria.setID(rs.getInt("codigo"));
			categoria.setNome(rs.getString("nome"));

			listaCategoria.add(categoria);
		}
		rs.close();
		ps.close();

		return listaCategoria;
	}
}	


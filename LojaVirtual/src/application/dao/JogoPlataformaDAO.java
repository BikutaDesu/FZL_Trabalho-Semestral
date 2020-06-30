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
import application.model.Plataforma;

public class JogoPlataformaDAO {
	private SQLServerConnectionFactory factory;
	private Connection con;

	public JogoPlataformaDAO() {
		try {
			factory = new SQLServerConnectionFactory();
			con = factory.getConnection();
		} catch (SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Jogo jogo) throws SQLException {
		String sql = "DELETE jogoPlataforma WHERE jogoCodigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());

		ps.execute();
		ps.close();
	}
	
	public void insert(Jogo jogo, Plataforma plataforma) throws SQLException {
		String sql = "INSERT INTO jogoPlataforma (jogoCodigo, plataformaCodigo) VALUES(?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());
		ps.setInt(2, plataforma.getID());
		ps.execute();
		ps.close();
	}
	
	public void update(Jogo jogo) throws SQLException {
		List<Plataforma> plataformaNaoCadastrados = new ArrayList<Plataforma>();
		plataformaNaoCadastrados.addAll(jogo.getPlataforma());
		
		for(Plataforma p : plataformaNaoCadastrados) {
			if(!select(jogo,p)) {
				insert(jogo,p);
			}
		}
	}
	
	public boolean select(Jogo jogo, Plataforma p) throws SQLException {
		String sql = "SELECT * " + 
				"FROM jogoPlataforma " +  
				"WHERE jogoCodigo = ? AND  plataformaCodigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());
		ps.setInt(2, p.getID());
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
	
	public List<Plataforma> selectAll(Jogo jogo) throws SQLException {
		String sql = "SELECT p.codigo, p.nome " + 
				"FROM plataformas p INNER JOIN jogoPlataforma jp " + 
				"ON p.codigo = jp.plataformaCodigo " + 
				"INNER JOIN jogos j " + 
				"ON j.codigo = jp.jogoCodigo " + 
				"WHERE j.codigo = ? ";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setLong(1, jogo.getID());
		ResultSet rs = ps.executeQuery();
		
		List<Plataforma> listaPlataforma = new ArrayList<Plataforma>();
		
		while (rs.next()) {
			Plataforma plataforma = new Plataforma();
			plataforma.setID(rs.getInt("codigo"));
			plataforma.setNome(rs.getString("nome"));
			
			listaPlataforma.add(plataforma);
		}
		rs.close();
		ps.close();
		
		return listaPlataforma;
	}
}

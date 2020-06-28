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
import application.model.Idioma;
import application.model.Jogo;

public class JogoIdiomaDAO {
	private SQLServerConnectionFactory factory;
	private Connection con;

	public JogoIdiomaDAO() {
		try {
			factory = new SQLServerConnectionFactory();
			con = factory.getConnection();
		} catch (SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Jogo jogo) throws SQLException {
		String sql = "DELETE jogoIdioma WHERE jogoCodigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());

		ps.execute();
		ps.close();
	}
	
	public void insert(Jogo jogo, Idioma idioma) throws SQLException {
		String sql = "INSERT INTO jogoIdioma (jogoCodigo, idiomaCodigo) VALUES(?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());
		ps.setInt(2, idioma.getID());
		ps.execute();
		ps.close();
	}
	
	public void update(Jogo jogo) throws SQLException {
		List<Idioma> idiomasNaoCadastrados = new ArrayList<Idioma>();
		idiomasNaoCadastrados.addAll(jogo.getIdiomas());
		
		for(Idioma i : idiomasNaoCadastrados) {
			if(!select(jogo,i)) {
				insert(jogo,i);
			}
		}
	}
	
	public boolean select(Jogo jogo, Idioma i) throws SQLException {
		String sql = "SELECT * " + 
				"FROM jogoIdioma j " +  
				"WHERE jogoCodigo = ? AND  idiomaCodigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, jogo.getID());
		ps.setInt(2, i.getID());
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
	
	public List<Idioma> selectAll(Jogo jogo) throws SQLException {
		String sql = "SELECT i.codigo, i.nomeIdioma " + 
				"FROM idiomas i INNER JOIN jogoIdioma ij " + 
				"ON i.codigo = ij.idiomaCodigo " + 
				"INNER JOIN jogos j " + 
				"ON j.codigo = ij.jogoCodigo " + 
				"WHERE j.codigo like ? ";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setLong(1, jogo.getID());
		ResultSet rs = ps.executeQuery();
		
		List<Idioma> listaIdiomas = new ArrayList<Idioma>();
		
		while (rs.next()) {
			Idioma idioma = new Idioma();
			idioma.setID(rs.getInt("codigo"));
			idioma.setNome(rs.getString("nomeIdioma"));
			
			listaIdiomas.add(idioma);
		}
		rs.close();
		ps.close();
		
		return listaIdiomas;
	}
}

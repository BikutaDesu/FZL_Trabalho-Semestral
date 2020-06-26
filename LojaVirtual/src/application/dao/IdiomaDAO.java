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

public class IdiomaDAO implements IIdiomaDAO {

	private Connection con;
	private SQLServerConnectionFactory factory;

	public IdiomaDAO() throws IOException, ParseException, SQLException {
		factory = new SQLServerConnectionFactory();
		con = factory.getConnection();
	}

	@Override
	public void insert(Idioma idioma) throws SQLException {
		String sql = "INSERT INTO idiomas VALUES (?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, idioma.getNome());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void update(Idioma idioma) throws SQLException {
		String sql = "UPDATE idiomas SET nome = ? WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, idioma.getNome());
		ps.setInt(2, idioma.getID());
	
		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Idioma idioma) throws SQLException {
		String sql = "DELETE FROM idiomas WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idioma.getID());
		
		ps.execute();
		ps.close();
	}

	@Override
	public Idioma select(Idioma idioma) throws SQLException {
		String sql = "SELECT * FROM idiomas WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idioma.getID());
		
		int i = 0;
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			idioma.setID(rs.getInt("codigo"));
			idioma.setNome(rs.getString("nome"));
		}
		
		if (i == 0) {
			idioma = new Idioma();
		}
		
		rs.close();
		ps.close();
		return idioma;
	}

	@Override
	public List<Idioma> selectAll() throws SQLException {
		String sql = "SELECT * FROM idiomas";
		PreparedStatement ps = con.prepareStatement(sql);
		
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

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
import application.model.Plataforma;

public class PlataformaDAO implements IPlataformaDAO {

	private Connection con;
	private SQLServerConnectionFactory factory;
	
	public PlataformaDAO() throws SQLException, IOException, ParseException {
		factory = new SQLServerConnectionFactory();
		con = factory.getConnection();
	}
	
	@Override
	public void insert(Plataforma plataforma) throws SQLException {
		String sql = "INSERT INTO plataformas VALUES(?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, plataforma.getNome());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void update(Plataforma plataforma) throws SQLException {
		String sql = "UPDATE plataformas SET nome = ? WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, plataforma.getNome());
		ps.setInt(2, plataforma.getID());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Plataforma plataforma) throws SQLException {
		String sql = "DELETE plataformas WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, plataforma.getID());
		
		ps.execute();
		ps.close();
	}

	@Override
	public Plataforma select(Plataforma plataforma) throws SQLException {
		String sql = "SELECT * FROM idiomas WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, plataforma.getID());
		
		int i = 0;
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			plataforma.setID(rs.getInt("codigo"));
			plataforma.setNome(rs.getString("nome"));
			i++;
		}
		
		if (i == 0) {
			plataforma = new Plataforma();
		}
		
		rs.close();
		ps.close();
		return plataforma;
	}

	@Override
	public List<Plataforma> selectAll() throws SQLException {
		String sql = "SELECT * FROM plataformas";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<Plataforma> listaPlataformas = new ArrayList<Plataforma>();
		
		while (rs.next()) {
			Plataforma plataforma = new Plataforma();
			plataforma.setID(rs.getInt("codigo"));
			plataforma.setNome(rs.getString("nome"));
			
			listaPlataformas.add(plataforma);
		}
		rs.close();
		ps.close();
		
		return listaPlataformas ;
	}

}

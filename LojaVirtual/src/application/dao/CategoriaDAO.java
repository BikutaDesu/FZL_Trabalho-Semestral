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

public class CategoriaDAO implements ICategoriaDAO{

	private SQLServerConnectionFactory factory;
	private Connection con;
	
	public CategoriaDAO() throws SQLException, IOException, ParseException {
		factory = new SQLServerConnectionFactory();
		con = factory.getConnection();
	}
	
	@Override
	public void insert(Categoria categoria) throws SQLException {
		String sql = "INSERT INTO categorias VALUES(?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, categoria.getNome());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void update(Categoria categoria) throws SQLException {
		String sql = "UPDATE categorias SET nome = ? WHERE codigo = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, categoria.getNome());
		ps.setInt(2, categoria.getID());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Categoria categoria) throws SQLException {
		String sql = "DELETE categorias WHERE codigo = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, categoria.getID());
		
		ps.execute();
		ps.close();
	}

	@Override
	public Categoria select(Categoria categoria) throws SQLException {
		String sql = "SELECT * FROM categorias WHERE codigo=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, categoria.getID());
		
		int i = 0;
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			categoria.setID(rs.getInt("codigo"));
			categoria.setNome(rs.getString("nome"));
			i++;
		}
		
		if (i == 0) {
			categoria = new Categoria();
		}
		
		rs.close();
		ps.close();
		return categoria;
	}

	@Override
	public List<Categoria> selectAll() throws SQLException {
		String sql = "SELECT * FROM categorias";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		List<Categoria> listaCategorias = new ArrayList<Categoria>();
		
		while (rs.next()) {
			Categoria categoria = new Categoria();
			categoria.setID(rs.getInt("codigo"));
			categoria.setNome(rs.getString("nome"));
			
			listaCategorias.add(categoria);
		}
		rs.close();
		ps.close();
		
		return listaCategorias;
	}

}

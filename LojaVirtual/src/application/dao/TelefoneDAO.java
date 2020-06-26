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
import application.model.Telefone;
import application.model.Usuario;

public class TelefoneDAO implements ITelefoneDAO {

	private Connection con;
	private SQLServerConnectionFactory factory;
	
	public TelefoneDAO() throws SQLException, IOException, ParseException {
		factory = new SQLServerConnectionFactory();
		con = factory.getConnection();
	}
	
	@Override
	public void insert(Telefone telefone) throws SQLException {
		String sql = "INSERT INTO telefone VALUES (?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, telefone.getNumero());
		ps.setString(2, telefone.getUsuario().getCPF());

		ps.execute();
		ps.close();
	}

	@Override
	public void update(Telefone telefone) throws SQLException {
		String sql = "UPDATE telefones SET numero = ? WHERE CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, telefone.getNumero());
		ps.setString(2, telefone.getUsuario().getCPF());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Telefone telefone) throws SQLException {
		String sql = "DELETE telefones WHERE numero = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, telefone.getNumero());
		
		ps.execute();
		ps.close();
	}


	@Override
	public List<Telefone> selectAll(Usuario usuario) throws SQLException {
		String sql = "SELECT * FROM telefones WHERE usuarioCPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getCPF());
		
		ResultSet rs = ps.executeQuery();

		List<Telefone> listaTelefones = new ArrayList<Telefone>();
		
		while (rs.next()) {
			Telefone telefone = new Telefone();
			telefone.setNumero(rs.getString("telefone"));
			telefone.setUsuario(usuario);
			listaTelefones.add(telefone);
		}
		rs.close();
		ps.close();
		
		return listaTelefones;
	}

}

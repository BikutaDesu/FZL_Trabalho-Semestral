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
	
	public TelefoneDAO(){
		try {
			factory = new SQLServerConnectionFactory();
			con = factory.getConnection();
		} catch (SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insert(Telefone telefone) throws SQLException {
		if(!select(telefone)) {
			String sql = "INSERT INTO telefones VALUES (?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, telefone.getNumero().replaceAll("\\D", ""));
			ps.setString(2, telefone.getUsuario().getCPF().replaceAll("\\D", ""));

			ps.execute();
			ps.close();
		}
	}

	@Override
	public void update(Telefone telefone) throws SQLException {
		String sql = "UPDATE telefones SET numero = ? WHERE CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, telefone.getNumero().replaceAll("\\D", ""));
		ps.setString(2, telefone.getUsuario().getCPF().replaceAll("\\D", ""));
		
		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Telefone telefone) throws SQLException {
		String sql = "DELETE FROM telefones WHERE telefone = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, telefone.getNumero().replaceAll("\\D", ""));
		
		ps.execute();
		ps.close();
	}
	
	public boolean select(Telefone telefone) throws SQLException {
		String sql = "SELECT telefone " + 
				"FROM telefones " + 
				"WHERE telefone = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, telefone.getNumero().replaceAll("\\D", ""));
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			rs.close();
			ps.close();
			return true;
		}else {
			rs.close();
			ps.close();
			return false;
		}
	}


	@Override
	public List<Telefone> selectAll(Usuario usuario) throws SQLException {
		String sql = "SELECT SUBSTRING(telefone ,1,5)+'-'+SUBSTRING(telefone ,6,9) AS telefone " + 
				"FROM telefones " + 
				"WHERE usuarioCPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getCPF().replaceAll("\\D", ""));
		
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

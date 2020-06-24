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
import application.model.Requisito;
import application.model.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

	private Connection con;
	private SQLServerConnectionFactory factory;
	
	public UsuarioDAO() throws SQLException, IOException, ParseException {
		factory = new SQLServerConnectionFactory();
		con = factory.getConnection();
	}
	
	@Override
	public void insert(Usuario usuario) throws SQLException {
		String sql = "INSERT INTO usuarios VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, usuario.getCPF());
		ps.setString(2, usuario.getNome());
		ps.setString(3, usuario.getEmail());
		ps.setString(4, usuario.getSenha());
		ps.setString(5, usuario.getNomeUsuario());
		ps.setInt(6, usuario.getTipoUsuario());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void update(Usuario usuario) throws SQLException {
		String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, nomeUsuario = ?, tipoUsuario = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getNome());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getSenha());
		ps.setString(4, usuario.getNomeUsuario());
		ps.setInt(5, usuario.getTipoUsuario());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Usuario usuario) throws SQLException {
		String sql = "DELETE usuarios WHERE CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, usuario.getCPF());
		
		ps.execute();
		ps.close();
	}

	@Override
	public Usuario select(Usuario usuario) throws SQLException {
		String sql = "SELECT * FROM usuarios WHERE CPF=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, usuario.getCPF());

		int i = 0;
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			usuario.setCPF(rs.getInt("codigo"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			usuario.setTipoUsuario(rs.getInt("tipoUsuario"));
			i++;
		}
		
		if (i == 0) {
			usuario = new Usuario();
		}
		
		rs.close();
		ps.close();
		return usuario;
	}

	@Override
	public List<Usuario> selectAll() throws SQLException {
		String sql = "SELECT * FROM usuarios";
		PreparedStatement ps = con.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		while (rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setCPF(rs.getInt("codigo"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			usuario.setTipoUsuario(rs.getInt("tipoUsuario"));
			
			listaUsuarios.add(usuario);
		}
		
		return listaUsuarios;
	}

}

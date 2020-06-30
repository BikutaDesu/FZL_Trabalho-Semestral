package application.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.control.TelefoneUsuarioControl;
import application.db.connection.factory.SQLServerConnectionFactory;
import application.model.Telefone;
import application.model.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

	private Connection con;
	private SQLServerConnectionFactory factory;
	private TelefoneUsuarioControl telefoneUsuarioControl = new TelefoneUsuarioControl();
	
	public UsuarioDAO(){
		try {
			factory = new SQLServerConnectionFactory();
			con = factory.getConnection();
		} catch (SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insert(Usuario usuario) throws SQLException {
		String sql = "INSERT INTO usuarios VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getCPF().replaceAll("\\D", ""));
		ps.setString(2, usuario.getNome());
		ps.setString(3, usuario.getEmail());
		ps.setString(4, usuario.getSenha());
		ps.setString(5, usuario.getNomeUsuario());
		
		ps.execute();
		ps.close();
		
		for(Telefone t : usuario.getTelefones()) {
			telefoneUsuarioControl.adicionar(t);
		}
	}

	@Override
	public void update(Usuario usuario) throws SQLException {
		String sql = "UPDATE usuarios SET nome = ?, email = ?, nomeUsuario = ? WHERE CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getNome());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getNomeUsuario());
		ps.setString(4, usuario.getCPF().replaceAll("\\D", ""));
		
		ps.execute();
		ps.close();
		
		for(Telefone t : usuario.getTelefones()) {
			telefoneUsuarioControl.adicionar(t);
		}
		
		if (!usuario.getSenha().isEmpty()) {
			updatePassword(usuario);
		}
	}
	
	public void updatePassword(Usuario usuario) throws SQLException {
		String sql = "UPDATE usuarios SET senha = ? WHERE CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getSenha());
		ps.setString(2, usuario.getCPF().replaceAll("\\D", ""));
		
		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Usuario usuario) throws SQLException {
		for(Telefone t : usuario.getTelefones()) {
			telefoneUsuarioControl.remover(t);
		}
		
		String sql = "DELETE usuarios WHERE CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getCPF().replaceAll("\\D", ""));
		
		ps.execute();
		ps.close();
	}

	@Override
	public Usuario select(Usuario usuario) throws SQLException {
		String sql = "SELECT	SUBSTRING(CPF,1,3)+'.'+SUBSTRING(CPF,4,3)+'.'+SUBSTRING(CPF,7,3)+'-'+SUBSTRING(CPF,10,2) AS CPF, " + 
				"		nome, email, nomeUsuario " + 
				"FROM usuarios " + 
				"WHERE CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getCPF().replaceAll("\\D", ""));

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			rs.close();
			ps.close();
			
			usuario.setTelefones(telefoneUsuarioControl.getLista(usuario));
			
			return usuario;
		}else{
			rs.close();
			ps.close();
			return new Usuario();
		}
	}

	@Override
	public List<Usuario> selectAll(String nome) throws SQLException {
		String sql = "SELECT SUBSTRING(u.CPF,1,3)+'.'+SUBSTRING(u.CPF,4,3)+'.'+SUBSTRING(u.CPF,7,3)+'-'+SUBSTRING(u.CPF,10,2) AS CPF, " + 
				"u.nome, u.email, u.nomeUsuario " + 
				"FROM usuarios u LEFT OUTER JOIN funcionarios f " + 
				"ON u.CPF = f.usuarioCPF " + 
				"WHERE f.usuarioCPF IS NULL AND nome like ? ";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%"+nome+"%");

		ResultSet rs = ps.executeQuery();
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		while (rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			
			usuario.setTelefones(telefoneUsuarioControl.getLista(usuario));
			
			listaUsuarios.add(usuario);
		}
		
		return listaUsuarios;
	}
	
	public List<Usuario> selectAllVenda() throws SQLException {
		String sql = "SELECT SUBSTRING(u.CPF,1,3)+'.'+SUBSTRING(u.CPF,4,3)+'.'+SUBSTRING(u.CPF,7,3)+'-'+SUBSTRING(u.CPF,10,2) AS CPF, " + 
				"u.nome, u.email, u.nomeUsuario " + 
				"FROM usuarios u LEFT OUTER JOIN funcionarios f " + 
				"ON u.CPF = f.usuarioCPF " + 
				"LEFT OUTER JOIN pedidos p " + 
				"ON u.CPF = p.usuarioCPF " + 
				"WHERE f.usuarioCPF IS NULL " + 
				"AND p.usuarioCPF IS NULL ";
		PreparedStatement ps = con.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		while (rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			
			usuario.setTelefones(telefoneUsuarioControl.getLista(usuario));
			
			listaUsuarios.add(usuario);
		}
		
		return listaUsuarios;
	}

	@Override
	public Usuario login(Usuario usuario) throws SQLException {
		String sql = "SELECT u.CPF, u.nome, u.email, u.nomeUsuario " + 
				"FROM usuarios u  " + 
				"LEFT OUTER JOIN funcionarios f " + 
				"ON u.CPF = f.usuarioCPF " + 
				"WHERE email=? AND senha=? " + 
				"AND f.usuarioCPF IS NULL";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getEmail());
		ps.setString(2, usuario.getSenha());

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			rs.close();
			ps.close();
			return usuario;
		}
		
		rs.close();
		ps.close();
		return new Usuario();
	}
	
}

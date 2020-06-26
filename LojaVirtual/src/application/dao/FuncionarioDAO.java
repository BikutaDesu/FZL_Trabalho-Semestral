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
import application.model.Funcionario;
import application.model.Usuario;

public class FuncionarioDAO implements IFuncionarioDAO {

	private Connection con;
	private SQLServerConnectionFactory factory;
	
	public FuncionarioDAO() throws SQLException, IOException, ParseException {
		factory = new SQLServerConnectionFactory();
		con = factory.getConnection();
	}
	
	@Override
	public void insert(Funcionario funcionario) throws SQLException {
		String sql = "INSERT INTO funcionarios VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, funcionario.getUsuario().getCPF());
		ps.setString(2, funcionario.getLogradouro());
		ps.setString(3, funcionario.getCEP());
		ps.setString(4, funcionario.getNumPorta());
		ps.setFloat(5, funcionario.getSalario());

		ps.execute();
		ps.close();
	}

	@Override
	public void update(Funcionario funcionario) throws SQLException {
		String sql = "UPDATE funcionarios SET logradouro=?, CEP=?, numPorta=?, salario=? WHERE CPF=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, funcionario.getLogradouro());
		ps.setString(2, funcionario.getCEP());
		ps.setString(3, funcionario.getNumPorta());
		ps.setFloat(4, funcionario.getSalario());
		ps.setString(5, funcionario.getUsuario().getCPF());

		ps.execute();
		ps.close();
	}

	@Override
	public void delete(Funcionario funcionario) throws SQLException {
		String sql = "DELETE funcionarios WHERE CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, funcionario.getUsuario().getCPF());

		ps.execute();
		ps.close();
	}

	@Override
	public Funcionario select(Funcionario funcionario) throws SQLException {
		String sql = "SELECT u.CPF, u.nome, u.email, u.senha, u.nomeUsuario, u.tipoUsuario, "
				+ "f.logradouro, f.numPorta, f.CEP, f.salario FROM funcionarios f " + 
				"INNER JOIN usuarios u " + 
				"ON u.CPF = f.usuarioCPF " + 
				"WHERE u.CPF = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, funcionario.getUsuario().getCPF());

		int i = 0;
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			usuario.setTipoUsuario(rs.getInt("tipoUsuario"));
			
			funcionario.setUsuario(usuario);
			funcionario.setLogradouro(rs.getString("logradouro"));
			funcionario.setNumPorta(rs.getString("numPorta"));
			funcionario.setCEP(rs.getString("CEP"));
			funcionario.setSalario(rs.getFloat("salario"));
			i++;
		}
		
		if (i == 0) {
			funcionario = new Funcionario();
		}
		
		rs.close();
		ps.close();
		return funcionario;
	}

	@Override
	public List<Funcionario> selectAll() throws SQLException {
		String sql = "SELECT u.CPF, u.nome, u.email, u.senha, u.nomeUsuario, u.tipoUsuario, "
				+ "f.logradouro, f.numPorta, f.CEP, f.salario FROM funcionarios f " + 
				"INNER JOIN usuarios u " + 
				"ON u.CPF = f.usuarioCPF";
		PreparedStatement ps = con.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
		
		while (rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setCPF(rs.getString("CPF"));
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			usuario.setTipoUsuario(rs.getInt("tipoUsuario"));
			
			Funcionario funcionario = new Funcionario();
			funcionario.setUsuario(usuario);
			funcionario.setLogradouro(rs.getString("logradouro"));
			funcionario.setNumPorta(rs.getString("numPorta"));
			funcionario.setCEP(rs.getString("CEP"));
			funcionario.setSalario(rs.getFloat("salario"));
			
			listaFuncionarios.add(funcionario);
		}
		return listaFuncionarios;
	}

}

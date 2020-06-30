package application.control;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.dao.FuncionarioDAO;
import application.dao.UsuarioDAO;
import application.model.Funcionario;
import application.model.Usuario;

public class LoginControl {
	
	public Funcionario login(Funcionario funcionario) throws SQLException, IOException, ParseException {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		return funcionarioDAO.login(funcionario);
	}
	
	public Usuario login(Usuario usuario) throws SQLException, IOException, ParseException {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO.login(usuario);
	}
	
}

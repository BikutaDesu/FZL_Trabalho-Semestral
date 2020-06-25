package application.control;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.dao.UsuarioDAO;
import application.model.Usuario;

public class LoginControl {
	public Usuario login(Usuario usuario) throws SQLException, IOException, ParseException {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO.login(usuario);
	}
}

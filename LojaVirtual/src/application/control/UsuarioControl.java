package application.control;

import java.sql.SQLException;

import application.dao.UsuarioDAO;
import application.model.Usuario;

public class UsuarioControl {
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	public void adicionar(Usuario usuario) throws SQLException {
		usuarioDAO.insert(usuario);
	}
	
	public void atualizar(Usuario usuario) throws SQLException {
		usuarioDAO.update(usuario);
	}
	
	public void remover(Usuario usuario) throws SQLException {
		usuarioDAO.delete(usuario);
	}
	
	public Usuario buscar(Usuario usuario) throws SQLException{
		return usuarioDAO.select(usuario);
	}
}

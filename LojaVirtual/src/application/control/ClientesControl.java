package application.control;

import java.sql.SQLException;


import application.dao.UsuarioDAO;
import application.model.Usuario;

public class ClientesControl {
	private UsuarioDAO usuarioDao = new UsuarioDAO();
	
	public void adicionar(Usuario usuario) throws SQLException {
		usuarioDao.insert(usuario);
	}
	
	public void remover(Usuario usuario) throws SQLException {
		usuarioDao.delete(usuario);
	}
	
	public void atualizar(Usuario usuario) throws SQLException {
		usuarioDao.update(usuario);
	}
}

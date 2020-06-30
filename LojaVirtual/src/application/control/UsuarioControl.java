package application.control;

import java.sql.SQLException;

import application.dao.UsuarioDAO;
import application.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuarioControl {
	private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	public ObservableList<Usuario> getList() {
		return usuarios;
	}
	public void adicionar(Usuario usuario) throws SQLException {
		usuarioDAO.insert(usuario);
		buscar("");
	}
	
	public void atualizar(Usuario usuario) throws SQLException {
		usuarioDAO.update(usuario);
		buscar("");
	}
	
	public void remover(Usuario usuario) throws SQLException {
		usuarioDAO.delete(usuario);
		buscar("");
	}
	
	public Usuario buscar(Usuario usuario) throws SQLException{
		return usuarioDAO.select(usuario);
	}
	
	public void buscar(String nome) throws SQLException{
		usuarios.clear();
		usuarios.addAll(usuarioDAO.selectAll(nome));
	}
	
	public void buscarVenda() throws SQLException{
		usuarios.clear();
		usuarios.addAll(usuarioDAO.selectAllVenda());
	}
}

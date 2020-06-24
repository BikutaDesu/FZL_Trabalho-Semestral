package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.Usuario;

public interface IUsuarioDAO {

	public void insert(Usuario usuario) throws SQLException;
	public void update(Usuario usuario) throws SQLException;
	public void delete(Usuario usuario) throws SQLException;
	public Usuario select(Usuario usuario) throws SQLException;
	public Usuario login(Usuario usuario) throws SQLException;
	public List<Usuario> selectAll() throws SQLException;
	
}

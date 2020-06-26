package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.Telefone;
import application.model.Usuario;

public interface ITelefoneDAO {

	public void insert(Telefone telefone) throws SQLException;
	public void update(Telefone telefone) throws SQLException;
	public void delete(Telefone telefone) throws SQLException;
	public List<Telefone> selectAll(Usuario usuario) throws SQLException;
	
}

package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.Categoria;

public interface ICategoriaDAO {

	public void insert(Categoria categoria) throws SQLException;
	public void update(Categoria categoria) throws SQLException;
	public void delete(Categoria categoria) throws SQLException;
	public Categoria select(Categoria categoria) throws SQLException;
	public List<Categoria> selectAll() throws SQLException;
	
}

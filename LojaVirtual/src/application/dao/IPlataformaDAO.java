package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.Plataforma;

public interface IPlataformaDAO {

	public void insert(Plataforma plataforma) throws SQLException;
	public void update(Plataforma plataforma) throws SQLException;
	public void delete(Plataforma plataforma) throws SQLException;
	public Plataforma select(Plataforma plataforma) throws SQLException;
	public List<Plataforma> selectAll() throws SQLException;
	
}

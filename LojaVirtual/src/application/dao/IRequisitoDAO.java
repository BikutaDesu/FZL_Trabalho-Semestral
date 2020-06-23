package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.Requisito;

public interface IRequisitoDAO {

	public void insert(Requisito requisito) throws SQLException;
	public void update(Requisito requisito) throws SQLException;
	public void delete(Requisito requisito) throws SQLException;
	public Requisito select(Requisito requisito) throws SQLException;
	public List<Requisito> selectAll() throws SQLException;
	
}

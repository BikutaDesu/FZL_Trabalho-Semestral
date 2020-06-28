package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.Jogo;
import application.model.Requisito;

public interface IRequisitoDAO {

	public void insert(Jogo jogo) throws SQLException;
	public void update(Jogo jogo) throws SQLException;
	public void delete(Jogo jogo) throws SQLException;
	public Requisito select(Jogo jogo) throws SQLException;
	public List<Requisito> selectAll() throws SQLException;
	
}

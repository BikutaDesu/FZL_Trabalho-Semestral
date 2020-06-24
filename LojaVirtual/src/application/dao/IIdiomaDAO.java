package application.dao;

import java.sql.SQLException;
import java.util.List;

import application.model.Idioma;

public interface IIdiomaDAO {

	public void insert(Idioma idioma) throws SQLException;
	public void update(Idioma idioma) throws SQLException;
	public void delete(Idioma idioma) throws SQLException;
	public Idioma select(Idioma idioma) throws SQLException;
	public List<Idioma> selectAll() throws SQLException;

}

package application.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.model.Jogo;

public interface IJogoDAO {

	public void insert(Jogo jogo) throws SQLException, IOException, ParseException;
	public void update(Jogo jogo) throws SQLException, IOException, ParseException;
	public void delete(Jogo jogo) throws SQLException, IOException, ParseException;
	public Jogo select(Jogo jogo) throws SQLException, IOException, ParseException;
	public List<Jogo> selectAll() throws SQLException, IOException, ParseException;
	public List<Jogo> pesquisarPorNome(String nome) throws SQLException, IOException, ParseException;
	
}

package application.model;

import java.sql.SQLException;
import java.util.List;

public interface IJogoDAO {

	public void insert(Jogo jogo) throws SQLException;
	public void update(Jogo jogo) throws SQLException;
	public void delete(Jogo jogo) throws SQLException;
	public Jogo select(Jogo jogo) throws SQLException;
	public List<Jogo> selectAll() throws SQLException;
	
}

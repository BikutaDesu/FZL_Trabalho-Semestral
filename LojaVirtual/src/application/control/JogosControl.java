package application.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.dao.IJogoDAO;
import application.dao.JogoDAO;
import application.model.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JogosControl {
	private ObservableList<Jogo> jogos = FXCollections.observableArrayList();
	private IJogoDAO jogoDAO = new JogoDAO();
	
	public ObservableList<Jogo> getList() {
		return jogos;
	}
	
	public void pesquisar(String nome) throws SQLException, IOException, ParseException {
		jogos.clear();
		List<Jogo> listaTemp = jogoDAO.pesquisarPorNome(nome);
		jogos.addAll(listaTemp);
	}
	
	public void adiconar(Jogo jogo) throws SQLException, IOException, ParseException {
		jogoDAO.insert(jogo);
		pesquisar("");
	}
	
	public void atualizar(int id, Jogo novo) throws SQLException, IOException, ParseException {
		novo.setID(id);
		jogoDAO.update(novo);
		pesquisar("");
	}
	
	public void remover(Jogo jogo) throws SQLException, IOException, ParseException {
		jogoDAO.delete(jogo);
		pesquisar("");
	}
}

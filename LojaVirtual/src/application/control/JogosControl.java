package application.control;

import application.model.Jogo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JogosControl {
	private ObservableList<Jogo> lista = FXCollections.observableArrayList();
	
	public ObservableList<Jogo> getLista() {
		return lista;
	}
}

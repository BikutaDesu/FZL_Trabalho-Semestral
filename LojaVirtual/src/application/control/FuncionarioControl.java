package application.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.dao.FuncionarioDAO;
import application.model.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FuncionarioControl {
	private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	
	public ObservableList<Funcionario> getLista() {
		return funcionarios;
	}
	
	public void pesquisar(String nome) throws SQLException, IOException, ParseException {
		funcionarios.clear();
		List<Funcionario> listaTemp = funcionarioDAO.pesquisarPorNome(nome);
		funcionarios.addAll(listaTemp);
	}
}

package application.view;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.model.Funcionario;
import application.model.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class AdmBoundary implements BoundaryContent, EventHandler<ActionEvent>{
	private Funcionario funcionario = new Funcionario();
	private Button btnSair = new Button("Sair");
	private Button btnCadJogos = new Button("Cadastrar Jogos");
	private Button btnListJogos = new Button("Listar Jogos");
	private Button btnCadFunc = new Button("Cadastrar Funcionário");
	private Button btnListFunc = new Button("Listar Funcionário");
	private Pane tela = new Pane();
	
	public AdmBoundary(Usuario u) {
		
		//MUDAR QUANDO TIVER O DAO DO FUNCIONARIO
		this.funcionario.setUsuario(u);
		
		GridPane panCampos = new GridPane();
		
		Label lblNome = new Label("Usuário: "+funcionario.getUsuario().getNome());
		//MUDAR QUANDO TIVER O DAO DO FUNCIONARIO
		
		lblNome.setFont(new Font(15));
		panCampos.add(lblNome, 250, 1);
		
		btnSair.setOnAction(this);
		panCampos.add(btnSair, 500, 1);
				
		Label lblTitulo = new Label("Painel de Adiministração da Loja");
		lblTitulo.setFont(new Font(30));
		panCampos.add(lblTitulo, 250, 30);
				
		panCampos.add(btnCadJogos,250,50);
		btnCadJogos.setOnAction(this);
		panCampos.add(btnListJogos,250,55);
		panCampos.add(btnCadFunc,250,60);
		panCampos.add(btnListFunc,250,65);
		
		tela.getChildren().add(panCampos);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnSair) {
			LoginBoundary loginBoundary = new LoginBoundary();
			tela.getChildren().clear();
			tela.getChildren().add(loginBoundary.generateForm());
		}
		if (event.getTarget() == btnCadJogos) {
			try {
				JogoBoundary jogoBoundary = new JogoBoundary(funcionario);
				tela.getChildren().clear();
				tela.getChildren().add(jogoBoundary.generateForm());
			} catch (SQLException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public Pane generateForm() {
		return tela;
	}
}

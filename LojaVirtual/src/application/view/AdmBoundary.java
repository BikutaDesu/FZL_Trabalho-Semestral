package application.view;

import application.model.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

public class AdmBoundary implements BoundaryContent, EventHandler<ActionEvent>{
	Usuario usuario = new Usuario();
	private Button btnSair = new Button("Sair");
	private Button btnCadJogos = new Button("Cadastrar Jogos");
	private Button btnListJogos = new Button("Listar Jogos");
	private Button btnCadFunc = new Button("Cadastrar Funcionário");
	private Button btnListFunc = new Button("Listar Funcionário");
	private Pane tela = new Pane();
	
	public AdmBoundary(Usuario u) {
		this.usuario = u;
		
		GridPane panCampos = new GridPane();
		
		Label lblNome = new Label("Usuário: "+usuario.getNome());
		lblNome.setFont(new Font(15));
		panCampos.add(lblNome, 250, 1);
		
		btnSair.setOnAction(this);
		panCampos.add(btnSair, 500, 1);
				
		Label lblTitulo = new Label("Painel de Adiministração da Loja");
		lblTitulo.setFont(new Font(30));
		panCampos.add(lblTitulo, 250, 30);
				
		panCampos.add(btnCadJogos,250,50);
		panCampos.add(btnListJogos,250,55);
		panCampos.add(btnCadFunc,250,60);
		panCampos.add(btnListFunc,250,65);
		
		panCampos.setAlignment(Pos.TOP_CENTER);
		
		tela.getChildren().add(panCampos);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnSair) {
			LoginBoundary login = new LoginBoundary();
			tela.getChildren().clear();
			tela.getChildren().add(login.generateForm());
		}
		
	}
	
	@Override
	public Pane generateForm() {
		return tela;
	}
}

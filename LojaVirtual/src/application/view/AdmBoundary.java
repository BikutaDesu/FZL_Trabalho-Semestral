package application.view;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.model.Funcionario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class AdmBoundary implements BoundaryContent, EventHandler<ActionEvent>{
	private Funcionario funcionario = new Funcionario();
	private Button btnSair = new Button("Sair");
	private Button btnCadJogos = new Button("Manutanção de Jogos");
	private Button btnCadFunc = new Button("Manutanção Funcionário");
	private Button btnCadCli = new Button("Manutanção Clientes");
	private BorderPane tela = new BorderPane();
	
	public AdmBoundary(Funcionario funcionario) {
		this.funcionario = funcionario;
		
		GridPane panCampos = new GridPane();
		
		panCampos.setPadding(new Insets(20, 20, 10, 10));
		panCampos.setHgap(10);
		panCampos.setVgap(10);
		
		Label lblNome = new Label("Usuário: "+funcionario.getUsuario().getNome());
		
		lblNome.setFont(new Font(15));
		panCampos.add(lblNome, 0, 0);
		
		btnSair.setOnAction(this);
		panCampos.add(btnSair, 1, 0);
				
		Label lblTitulo = new Label("Painel de Adiministração da Loja");
		lblTitulo.setFont(new Font(30));
		panCampos.add(lblTitulo, 0, 1);
				
		btnCadJogos.setOnAction(this);
		panCampos.add(btnCadJogos,0,2);
		btnCadFunc.setOnAction(this);
		panCampos.add(btnCadFunc,0,3);
		btnCadCli.setOnAction(this);
		panCampos.add(btnCadCli,0,4);
		
		tela.setCenter(panCampos);
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
		if (event.getTarget() == btnCadFunc) {
			FuncionarioBoundary funcionarioBoundary = new FuncionarioBoundary(funcionario);
			tela.getChildren().clear();
			tela.getChildren().add(funcionarioBoundary.generateForm());
		}
		if (event.getTarget() == btnCadCli) {
			try {
				AdmClienteBoundary admClienteBoundary = new AdmClienteBoundary(funcionario);
				tela.getChildren().clear();
				tela.getChildren().add(admClienteBoundary.generateForm());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Pane generateForm() {
		return tela;
	}
}

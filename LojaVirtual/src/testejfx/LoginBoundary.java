package testejfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginBoundary extends Application implements EventHandler<ActionEvent> {
	private TextField txtEmail = new TextField();
	private TextField txtSenha = new TextField();
	private Button btnCad = new Button("Cadastro");
	private Button btnLogin = new Button("Login");
	
	@Override
	public void start(Stage stage) throws Exception {
		Pane p = new Pane();
		Scene scn = new Scene(p, 800, 600);
		
		Label lblCabecalho = new Label("Raizer Games");
		lblCabecalho.setFont(new Font(30));
		lblCabecalho.relocate(250, 20);
		
		Label lblEmail = new Label("Email:");
		lblEmail.setFont(new Font(20));
		lblEmail.relocate(250, 100);
		txtEmail.relocate(350, 100);
		
		Label lblSenha = new Label("Senha:");
		lblSenha.setFont(new Font(20));
		lblSenha.relocate(250, 150);
		txtSenha.relocate(350, 150);
		
		btnCad.setOnAction(this);
		btnCad.relocate(250, 200);
		
		btnLogin.relocate(350, 200);		
		btnLogin.setOnAction(this);
		
		p.getChildren().addAll(lblCabecalho ,lblEmail, lblSenha, txtEmail, txtSenha, btnCad, btnLogin);
		stage.setScene(scn);
		stage.show();
		stage.setTitle("Janela principal");
	}
	
	public static void main(String[] args) {
		Application.launch(LoginBoundary.class, args);
	}

	public void handle(ActionEvent event) {
		if (event.getTarget() == btnCad) { 

		} else if (event.getTarget() == btnLogin) { 

		}
	}
}

package application.view;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.control.LoginControl;
import application.model.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class LoginBoundary implements BoundaryContent, EventHandler<ActionEvent> {
	
	private TextField txtEmail = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private Button btnCad = new Button("Cadastro");
	private Button btnLogin = new Button("Login");
	private Label erro = new Label();
	private Pane tela = new Pane();
	
	public LoginBoundary() {		
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
		
		erro.setFont(new Font(15));
		erro.relocate(250, 250);
		
		tela.getChildren().addAll(lblCabecalho ,lblEmail, lblSenha, txtEmail, txtSenha, btnCad, btnLogin, erro);
	}

	public void handle(ActionEvent event){
		erro.setText("");
		if (event.getTarget() == btnCad) { 
			ClientesBoundary cliente = new ClientesBoundary();
			tela.getChildren().clear();
			tela.getChildren().add(cliente.generateForm());
		} else if (event.getTarget() == btnLogin) { 
			Usuario u = new Usuario();
			
			if(!txtEmail.getText().isEmpty()) {
				u.setEmail(txtEmail.getText());
			}else{
				erro.setText("email inválido!\n");
			}
			
			if(!txtSenha.getText().isEmpty()) {
				u.setSenha(txtSenha.getText());
			}else{
				erro.setText(erro.getText()+"senha inválida!\n");
			}
			
			if(erro.getText().isEmpty()) {
				LoginControl usuario = new LoginControl();
				try {
					u = usuario.login(u);
					if(u.getTipoUsuario()!=null) {
						if(u.getTipoUsuario()==0) {
							//vai pra tela adm
						}
						if(u.getTipoUsuario()==1) {
							//vai pra tela venda
						}
					}else {
						erro.setText("Usuario não encontrado");
					}
				} catch (SQLException | IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Pane generateForm() {
		return tela;
	}
}

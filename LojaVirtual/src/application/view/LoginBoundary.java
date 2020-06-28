package application.view;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.control.LoginControl;
import application.model.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
		GridPane panCampos = new GridPane();
		
		panCampos.setPadding(new Insets(20, 20, 10, 10));
		panCampos.setHgap(10);
		panCampos.setVgap(10);
		
		Label lblCabecalho = new Label("Raizer Games");
		lblCabecalho.setFont(new Font(30));
		panCampos.add(lblCabecalho, 0, 0, 2, 1);
		
		panCampos.add(new Label("Email: "), 0, 1);
		panCampos.add(txtEmail, 1, 1);
		
		panCampos.add(new Label("Senha: "), 0, 2);
		panCampos.add(txtSenha, 1, 2);
		
		GridPane panbtn = new GridPane();
		btnCad.setOnAction(this);
		panbtn.add(btnCad, 0, 0);
		btnLogin.setOnAction(this);
		panbtn.add(btnLogin, 1, 0);
		
		panCampos.add(panbtn, 1, 3);
		
		panCampos.add(erro, 3, 0);
		
		tela.getChildren().addAll(panCampos);
	}

	public void handle(ActionEvent event){
		erro.setText("");
		if (event.getTarget() == btnCad) { 
			ClientesBoundary cliente = new ClientesBoundary();
			tela.getChildren().clear();
			tela.getChildren().addAll(cliente.generateForm());
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
				LoginControl loginControl = new LoginControl();
				try {
					u = loginControl.login(u);
					if(u.getTipoUsuario()!=null) {
						if(u.getTipoUsuario()==1) {
							AdmBoundary admBoundary = new AdmBoundary(u);
							tela.getChildren().clear();
							tela.getChildren().addAll(admBoundary.generateForm());
						}
						if(u.getTipoUsuario()==0) {
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

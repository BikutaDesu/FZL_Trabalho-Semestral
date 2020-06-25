package application.view;



import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.control.ClientesControl;
import application.model.Telefone;
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

public class ClientesBoundary implements BoundaryContent, EventHandler<ActionEvent> {

	private TextField txtCpf = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private TextField txtNomeUsuario = new TextField();
	private TextField txtTelefone1 = new TextField();
	private TextField txtTelefone2 = new TextField();
	private BorderPane tela = new BorderPane();
	
	private Button btnSalvar = new Button("Salvar");
	private Button btnVoltar = new Button("Voltar");
	
	private Label lblErro = new Label();
	
	public ClientesBoundary() {		
		GridPane panCampos = new GridPane();
		
		Label lblCabecalho = new Label("Cadastro de Clientes ");
		lblCabecalho.setFont(new Font(30));
		panCampos.add(lblCabecalho, 0, 0, 2, 2);
		
		panCampos.add(new Label("CPF: "), 0, 2);
		panCampos.add(txtCpf, 1, 2);
		
		panCampos.add(new Label("Nome: "), 0, 3);
		panCampos.add(txtNome, 1, 3);
		
		panCampos.add(new Label("E-Mail: "), 0, 4);
		panCampos.add(txtEmail, 1, 4);
		
		panCampos.add(new Label("Senha: "), 0, 5);
		panCampos.add(txtSenha, 1, 5);
		
		panCampos.add(new Label("Nome de Usuario: "), 0, 6);
		panCampos.add(txtNomeUsuario, 1, 6);
		
		panCampos.add(new Label("Telefone 1: "), 0, 7);
		panCampos.add(txtTelefone1, 1, 7);
		
		panCampos.add(new Label("Telefone 2: "), 0, 8);
		panCampos.add(txtTelefone2, 1, 8);
		
		panCampos.add(btnVoltar, 0, 9);
		
		panCampos.add(btnSalvar, 1, 9);
		
		panCampos.add(lblErro, 0, 10);
		
		panCampos.setPadding(new Insets(20, 20, 10, 10));
		
		btnSalvar.setOnAction(this);
		btnVoltar.setOnAction(this);
		
		tela.setCenter(panCampos);	
	}
	
	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnSalvar) { 
			String erro = "";
			Usuario u = new Usuario();
			
			try {
				u.setCPF(Integer.parseInt(txtCpf.getText()));
			}catch (Exception e) {
				txtCpf.setText("");
				erro+="CPF inválido!\n";
			}
			
			if(!txtNome.getText().isEmpty()) {
				u.setNome(txtNome.getText());
			}else {
				txtNome.setText("");
				erro+="Nome inválido!\n";
			}
			
			if(!txtEmail.getText().isEmpty() && txtEmail.getText().contains("@") && txtEmail.getText().contains(".com")) {
				u.setEmail(txtEmail.getText());
			}else {
				txtEmail.setText("");
				erro+="Email inválido!\n";
			}
			
			if(!txtSenha.getText().isEmpty()) {
				u.setSenha(txtSenha.getText());
			}else {
				txtSenha.setText("");
				erro+="Senha inválida!\n";
			}
			
			if(!txtNomeUsuario.getText().isEmpty()) {
				u.setNomeUsuario(txtNomeUsuario.getText());
			}else {
				txtNomeUsuario.setText("");
				erro+="Nome de Usuario inválido!\n";
			}

			try {
				Telefone t = new Telefone(Integer.parseInt(txtTelefone1.getText()), u);
				u.addTelefone(t);
			}catch (Exception e){
				txtTelefone1.setText("");
				erro+="Telefone inválido!\n";
			}
			
			if(!txtTelefone2.getText().isEmpty()) {
				try {
					Telefone t = new Telefone(Integer.parseInt(txtTelefone2.getText()), u);
					u.addTelefone(t);
				}catch (Exception e){
					txtTelefone2.setText("");
					erro+="Telefone inválido!\n";
				}
			}
			
			if(erro.isEmpty()) {
				ClientesControl c = new ClientesControl();
				try {
					u.setTipoUsuario(1);
					String resultado = c.add(u);
					lblErro.setText(resultado);
				} catch (SQLException | IOException | ParseException e) {
					e.printStackTrace();
				}

			}else{
				lblErro.setText(erro);
			}
		}
		if (event.getTarget() == btnVoltar) {
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

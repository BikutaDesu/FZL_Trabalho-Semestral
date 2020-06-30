package application.view;


import application.control.LoginControl;
import application.control.PedidoControl;
import application.model.Funcionario;
import application.model.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
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

		Label lblLogin = new Label("Login");
		lblLogin.setFont(new Font(20));
		panCampos.add(lblLogin, 0, 1, 2, 1);
		
		panCampos.add(new Label("Email: "), 0, 2);
		panCampos.add(txtEmail, 1, 2);

		panCampos.add(new Label("Senha: "), 0, 3);
		panCampos.add(txtSenha, 1, 3);

		GridPane panbtn = new GridPane();
		btnCad.setOnAction(this);
		panbtn.add(btnCad, 0, 0);
		btnLogin.setOnAction(this);
		panbtn.add(btnLogin, 1, 0);

		panCampos.add(panbtn, 1, 4);

		panCampos.add(erro, 3, 0);
		
		FlowPane pane = new FlowPane();
		pane.setAlignment(Pos.TOP_CENTER);
		pane.getChildren().add(panCampos);
		
		tela.getChildren().addAll(pane);
	}
	
	private Funcionario boundaryToEntity() {
		Funcionario funcionario = new Funcionario();
		Usuario usuario = new Usuario();
		
		if (!txtEmail.getText().isEmpty()) usuario.setEmail(txtEmail.getText());
		else erro.setText("email inválido!\n");
		if (!txtSenha.getText().isEmpty()) usuario.setSenha(txtSenha.getText());
		else erro.setText(erro.getText() + "senha inválida!\n");
		
		funcionario.setUsuario(usuario);
		
		return funcionario;
	}

	public void handle(ActionEvent event) {
		erro.setText("");
		if (event.getTarget() == btnCad) { 
			UsuarioBoundary cliente = new UsuarioBoundary();
			tela.getChildren().clear();
			tela.getChildren().addAll(cliente.generateForm());
		} else if (event.getTarget() == btnLogin) {
			Funcionario funcionario = new Funcionario();
			funcionario = boundaryToEntity();
			Usuario usuario = new Usuario();
			usuario = funcionario.getUsuario();
			
			if (erro.getText().isEmpty()) {
				LoginControl loginControl = new LoginControl();
				try {
					funcionario = loginControl.login(funcionario);
					if(!funcionario.getUsuario().equals(null)) {
						AdmBoundary admBoundary = new AdmBoundary(funcionario);
						tela.getChildren().clear();
						tela.getChildren().addAll(admBoundary.generateForm());
					}
				}catch (Exception e) {
					try {
						usuario = loginControl.login(usuario);
						PedidoControl pedido = new PedidoControl(usuario);
						ClienteBoundary clienteBoundary = new ClienteBoundary(usuario, 0, pedido);
						tela.getChildren().clear();
						tela.getChildren().addAll(clienteBoundary.generateForm());
					}catch (Exception e1) {
						erro.setText("Usuario não encontrado");
					}
				}
			}
		}
	}

	@Override
	public Pane generateForm() {
		return tela;
	}
}
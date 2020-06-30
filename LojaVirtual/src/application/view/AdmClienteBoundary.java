package application.view;

import java.sql.SQLException;


import application.control.UsuarioControl;
import application.model.Funcionario;
import application.model.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class AdmClienteBoundary implements BoundaryContent, EventHandler<ActionEvent> {
	private Button btnVoltar = new Button("Voltar");
	private Button btnSair = new Button("Sair");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnRemoverUser = new Button("Remover Cliente");
	private Button btnPesq = new Button("Pesquisar");
	private Button btnPesqVenda = new Button("Pesquisar Clientes sem Pedidos");
	
	private TextField txtCpf = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private TextField txtNomeUsuario = new TextField();
	
	private TableView<Usuario> tableClientes = new TableView<>();
	
	private UsuarioControl usuarioControl = new UsuarioControl();
	
	private Pane tela = new Pane();
	
	private Funcionario funcionario;

	public AdmClienteBoundary(Funcionario funcionario) throws SQLException {
		this.funcionario = funcionario;
		
		generateTable();
		
		GridPane panCampos = new GridPane();
		panCampos.setPadding(new Insets(20, 20, 10, 10));
		panCampos.setHgap(10);
		panCampos.setVgap(10);

		Label lblNome = new Label("Usuário: "+funcionario.getUsuario().getNome());
		lblNome.setFont(new Font(15));
		panCampos.add(lblNome, 1, 0);
				
		btnVoltar.setOnAction(this);
		panCampos.add(btnVoltar, 0, 0);
		
		btnSair.setOnAction(this);
		panCampos.add(btnSair, 3, 0);
		
		panCampos.add(new Label("CPF: "), 0, 2);
		panCampos.add(txtCpf, 1, 2);
		txtCpf.setDisable(true);
		
		panCampos.add(new Label("Nome: "), 0, 3);
		panCampos.add(txtNome, 1, 3);
		
		panCampos.add(new Label("E-Mail: "), 0, 4);
		panCampos.add(txtEmail, 1, 4);
		
		panCampos.add(new Label("Senha: "), 0, 5);
		panCampos.add(txtSenha, 1, 5);
		
		panCampos.add(new Label("Nome de Usuario: "), 0, 6);
		panCampos.add(txtNomeUsuario, 1, 6);
		
		panCampos.add(btnAtualizar, 0, 7);
		btnAtualizar.setOnAction(this);
		
		panCampos.add(btnRemoverUser, 1, 7);
		
		btnPesq.setOnAction(this);
		panCampos.add(btnPesq, 4, 1);
		
		btnPesqVenda.setOnAction(this);
		panCampos.add(btnPesqVenda, 5, 1);
		
		tableClientes.setMinWidth(300);
		panCampos.add(tableClientes, 4, 2, 2, 9);
		
		tela.getChildren().add(panCampos);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnSair) {
			LoginBoundary login = new LoginBoundary();
			tela.getChildren().clear();
			tela.getChildren().add(login.generateForm());
		}
		if (event.getTarget() == btnVoltar) {
			AdmBoundary admBoundary = new AdmBoundary(funcionario);
			tela.getChildren().clear();
			tela.getChildren().add(admBoundary.generateForm());
		}
		if (event.getTarget() == btnPesq) {
			try {
				usuarioControl.buscar(txtNome.getText());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (event.getTarget() == btnPesqVenda) {
			try {
				usuarioControl.buscarVenda();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void entityToBoundary(Usuario usuario) {
		try {
			txtCpf.setText(usuario.getCPF());
			txtNome.setText(usuario.getNome());
			txtEmail.setText(usuario.getEmail());
			txtNomeUsuario.setText(usuario.getNomeUsuario());
		}catch (Exception e){
		}
	}
	
	private Usuario boundaryToEntity() {
		Usuario usuario = new Usuario();
		try {
			usuario.setCPF(txtCpf.getText());
			usuario.setNome(txtNome.getText());
			usuario.setEmail(txtEmail.getText());
			usuario.setSenha(txtSenha.getText());
			usuario.setNomeUsuario(txtNomeUsuario.getText());
		}catch (Exception e) {
		}
		return usuario;
	}	
	
	@SuppressWarnings("unchecked")
	public void generateTable() throws SQLException { 		
		TableColumn<Usuario, String> colCpf = new TableColumn<>("CPF");
		colCpf.setCellValueFactory(new PropertyValueFactory<Usuario, String>("CPF"));

		TableColumn<Usuario, String> colNome = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));

		TableColumn<Usuario, String> colEmail = new TableColumn<>("E-Mail");
		colEmail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email"));

		TableColumn<Usuario, String> colUsername = new TableColumn<>("Nome de Usuario");
		colUsername.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nomeUsuario"));

		tableClientes.getColumns().addAll(colCpf, colNome, colEmail, colUsername);
		
		tableClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>() {

			@Override
			public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
				entityToBoundary(newValue);
				
				btnAtualizar.setOnAction(e -> {
					try {
						Usuario usuario = new Usuario();
						usuario = boundaryToEntity();
						usuarioControl.atualizar(usuario);
						alertaMensagem(AlertType.INFORMATION, "Sucesso!", 
								null, String.format("O cadastro do cliente foi atualizado com sucesso!"));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
				btnRemoverUser.setOnAction(e -> {
					try {
						usuarioControl.remover(newValue);
						alertaMensagem(AlertType.INFORMATION, "Sucesso!", 
								null, String.format("O cadastro do cliente foi removido com sucesso!"));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
				
			}
		});;
		
		tableClientes.setItems(usuarioControl.getList());
	}
	
	public void alertaMensagem(AlertType tipo, String titulo, String header, String msg) { 
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	@Override
	public Pane generateForm() {
		return tela;
	}

}


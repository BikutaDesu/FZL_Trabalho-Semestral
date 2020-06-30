package application.view;



import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.control.ClientesControl;
import application.control.PedidoControl;
import application.control.TelefoneUsuarioControl;
import application.model.Telefone;
import application.model.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class UsuarioBoundary implements BoundaryContent, EventHandler<ActionEvent> {

	private TextField txtCpf = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private TextField txtNomeUsuario = new TextField();
	private TextField txtTelefone = new TextField();
	
	private TelefoneUsuarioControl telefoneUsuarioControl = new TelefoneUsuarioControl();
	private ObservableList<Telefone> telefones = FXCollections.observableArrayList();
	private TableView<Telefone> tableTelefone = new TableView<>();
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnRemover = new Button("Remover");
	
	private Pane tela = new Pane();
	
	private Button btnSalvar = new Button("Salvar");
	private Button btnVoltar = new Button("Voltar");
	
	public void generateTable() { 	
		TableColumn<Telefone, String> colTelefone  = new TableColumn<>("Numero");
		colTelefone.setCellValueFactory(new PropertyValueFactory<Telefone, String>("numero"));
		
		tableTelefone.getColumns().add(colTelefone);
		tableTelefone.setItems(telefones);
		tableTelefone.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<Telefone>() {
			@Override
			public void changed(ObservableValue<? extends Telefone> observable, Telefone oldValue,
					Telefone newValue) {
					btnRemover.setOnAction(e -> {
					try {
						telefoneUsuarioControl.remover(newValue);
						telefones.remove(newValue);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
			}
		});
	}
	
	public UsuarioBoundary() {		
		GridPane panCampos = new GridPane();
		
		generateTable();
		
		panCampos.setPadding(new Insets(20, 20, 10, 10));
		panCampos.setHgap(10);
		panCampos.setVgap(10);
		
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
		
		panCampos.add(new Label("Telefone: "), 0, 7);
		panCampos.add(txtTelefone, 1, 7);
		panCampos.add(btnAdicionar, 1, 8);
		btnAdicionar.setOnAction(e -> {
			Telefone t = new Telefone();
			t.setNumero(txtTelefone.getText());
			telefones.add(t);
		});
		
		panCampos.add(btnVoltar, 0, 9);
		
		panCampos.add(btnSalvar, 1, 9);
		
		tableTelefone.setMaxWidth(80);
		tableTelefone.setMaxHeight(200);
		panCampos.add(tableTelefone, 3, 2, 1, 6);
		panCampos.add(btnRemover, 3, 8);
		
		btnSalvar.setOnAction(this);
		btnVoltar.setOnAction(this);
		
		tela.getChildren().add(panCampos);	
	}
	
	
	
	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnSalvar) { 
			ClientesControl clientesControl = new ClientesControl();
			Usuario usuario = boundaryToEntity();
			if(!usuario.equals(new Usuario())) {
				try {
					clientesControl.adicionar(usuario);
					alertaMensagem(AlertType.INFORMATION, "Sucesso!", 
							null, String.format("Seu cadastro foi efetuado com sucesso!"));
					PedidoControl pedido = new PedidoControl(usuario);
					try {
						ClienteBoundary clienteBoundary = new ClienteBoundary(usuario, 0, pedido);
						tela.getChildren().clear();
						tela.getChildren().addAll(clienteBoundary.generateForm());
					} catch (IOException | ParseException e) {
						e.printStackTrace();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if (event.getTarget() == btnVoltar) {
			LoginBoundary login = new LoginBoundary();
			tela.getChildren().clear();
			tela.getChildren().add(login.generateForm());
		}
	}

	private Usuario boundaryToEntity() {
		Usuario u = new Usuario();
		try {
		u.setCPF(txtCpf.getText());
		u.setNome(txtNome.getText());
		u.setEmail(txtEmail.getText());
		u.setSenha(txtSenha.getText());
		u.setNomeUsuario(txtNomeUsuario.getText());
		u.setTelefones(telefones);
		
		}catch (Exception e) {
		}
		
		return u;
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

package application.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import org.json.simple.parser.ParseException;

import application.control.PedidoControl;
import application.control.TelefoneUsuarioControl;
import application.control.UsuarioControl;
import application.model.Pedido;
import application.model.Telefone;
import application.model.Usuario;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
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

public class ClienteConfigBoundary implements BoundaryContent, EventHandler<ActionEvent>{
	private Button btnVoltar = new Button("Voltar");
	private Button btnSair = new Button("Sair");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnRemover = new Button("Remover");
	
	private TextField txtCpf = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private TextField txtNomeUsuario = new TextField();
	private TextField txtTelefone = new TextField();
	
	private TelefoneUsuarioControl telefoneUsuarioControl = new TelefoneUsuarioControl();
	private TableView<Pedido> tablePedidos = new TableView<>();
	private ObservableList<Telefone> telefones = FXCollections.observableArrayList();
	private TableView<Telefone> tableTelefone = new TableView<>();
	
	private UsuarioControl usuarioControl = new UsuarioControl();
	
	private Pane tela = new Pane();
	
	private Usuario usuario;
	private PedidoControl pedido;

	public ClienteConfigBoundary(Usuario usuario, PedidoControl pedido) throws SQLException {
		this.usuario= usuarioControl.buscar(usuario);
		this.pedido=pedido;
		
		
		generateTablePedido();
		
		telefones.addAll(usuario.getTelefones());
		
		GridPane panCampos = new GridPane();
		panCampos.setPadding(new Insets(20, 20, 10, 10));
		panCampos.setHgap(10);
		panCampos.setVgap(10);

		Label lblNome = new Label("Usuário: "+usuario.getNome());
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
		
		panCampos.add(new Label("Telefone: "), 0, 7);
		panCampos.add(txtTelefone, 1, 7);
		panCampos.add(btnAdicionar, 0, 8);
		btnAdicionar.setOnAction(e -> {
			Telefone t = new Telefone();
			t.setNumero(txtTelefone.getText());
			telefones.add(t);
		});
		
		panCampos.add(btnAtualizar, 1, 8);
		btnAtualizar.setOnAction(this);
		
		tableTelefone.setMaxWidth(80);
		tableTelefone.setMaxHeight(200);
		panCampos.add(tableTelefone, 3, 2, 1, 6);
		panCampos.add(btnRemover, 3, 8);
		
		tablePedidos.setMinWidth(300);
		panCampos.add(tablePedidos, 4, 2, 1, 9);
		
		entityToBoundary();
		
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
			try {
				ClienteBoundary clienteBoundary = new ClienteBoundary(usuario, 0, pedido);
				tela.getChildren().clear();
				tela.getChildren().add(clienteBoundary.generateForm());
			} catch (SQLException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		if (event.getTarget() == btnAtualizar) {
			try {
				boundaryToEntity();
				usuarioControl.atualizar(usuario);
				alertaMensagem(AlertType.INFORMATION, "Sucesso!", 
						null, String.format("Seu cadastro foi atualizado com sucesso!"));
			}catch (Exception e) {
			}
		}
	}
	
	private void entityToBoundary() {
		try {
			txtCpf.setText(usuario.getCPF());
			txtNome.setText(usuario.getNome());
			txtEmail.setText(usuario.getEmail());
			txtNomeUsuario.setText(usuario.getNomeUsuario());
			telefones.addAll(usuario.getTelefones());
		}catch (Exception e){
		}
	}
	
	private void boundaryToEntity() {
		try {
		usuario.setCPF(txtCpf.getText());
		usuario.setNome(txtNome.getText());
		usuario.setEmail(txtEmail.getText());
		usuario.setSenha(txtSenha.getText());
		usuario.setNomeUsuario(txtNomeUsuario.getText());
		usuario.setTipoUsuario(2);
		
		for(Telefone t : telefones) {
			t.setUsuario(usuario);
		}
		
		usuario.setTelefones(telefones);
		
		}catch (Exception e) {
		}
	}	
	
	@SuppressWarnings("unchecked")
	public void generateTablePedido() throws SQLException { 		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		TableColumn<Pedido, String> colNomeJogo  = new TableColumn<>("Nome");
		colNomeJogo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJogo().getNome()));
		
		TableColumn<Pedido, String> colPrecojogo  = new TableColumn<>("Preço");
		colPrecojogo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJogo().getPreco().toString()));
		
		TableColumn<Pedido, String> colData  = new TableColumn<>("Data");
		colData.setCellValueFactory(itemData -> { 
			return new ReadOnlyStringWrapper(
					dtf.format(itemData.getValue().getDataPedido()));
		});

		tablePedidos.getColumns().addAll(colNomeJogo, colPrecojogo, colData);
		
		try {
			ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
			pedidos.addAll(pedido.buscarPedidos(usuario));
			tablePedidos.setItems(pedidos);
		}catch (Exception e) {
		}

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

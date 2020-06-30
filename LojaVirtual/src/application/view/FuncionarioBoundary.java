package application.view;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.control.FuncionarioControl;
import application.control.TelefoneUsuarioControl;
import application.model.Funcionario;
import application.model.Telefone;
import application.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class FuncionarioBoundary implements BoundaryContent, EventHandler<ActionEvent>{
	
	private Button btnVoltar = new Button("Voltar");
	private Button btnSalvar = new Button("Salvar");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnExcluir = new Button("Excluir");
	private Button btnSair = new Button("Sair");
	private Button btnPesq = new Button("Pesquisar");
	
	private TextField txtCpf = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private PasswordField txtSenha = new PasswordField();
	private TextField txtNomeUsuario = new TextField();
	private TextField txtTelefone = new TextField();
	
	private TextField txtLogradouro = new TextField();
	private TextField txtCep = new TextField();
	private TextField txtNumPorta = new TextField();
	private TextField txtSalario = new TextField();
	
	private TelefoneUsuarioControl telefoneUsuarioControl = new TelefoneUsuarioControl();
	private ObservableList<Telefone> telefones = FXCollections.observableArrayList();
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnRemover = new Button("Remover");
	
	private TableView<Funcionario> tableFuncionario = new TableView<>();
	private TableView<Telefone> tableTelefone = new TableView<>();
	private Funcionario funcionario = new Funcionario();
	private FuncionarioControl control = new FuncionarioControl();
	
	private Pane tela = new Pane();
	
	@SuppressWarnings("unchecked")
	public void generateTable() { 
		TableColumn<Funcionario, String> colCpf  = new TableColumn<>("CPF");
		colCpf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getCPF()));
		
		TableColumn<Funcionario, String> colNome  = new TableColumn<>("Nome");
		colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNome()));
		
		TableColumn<Funcionario, String> colEmail  = new TableColumn<>("E-Mail");
		colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getEmail()));
		
		TableColumn<Funcionario, Float> colSalario  = new TableColumn<>("Salario");
		colSalario.setCellValueFactory(new PropertyValueFactory<Funcionario, Float>("salario"));
		
		tableFuncionario.getColumns().addAll(colCpf, colNome, colEmail, colSalario);
		tableFuncionario.setItems(control.getLista());
		
		tableFuncionario.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<Funcionario>() {

			@Override
			public void changed(ObservableValue<? extends Funcionario> observable, Funcionario oldValue,
					Funcionario newValue) {
				txtSenha.setText("");
				txtTelefone.setText("");
				entityToBoundary(newValue);
				
				btnAtualizar.setOnAction(e -> {
					try {
						control.atualizar(boundaryToEntity());
					} catch (SQLException | IOException | ParseException e1) {
						e1.printStackTrace();
					}
				});
				btnExcluir.setOnAction(e -> {
					try {
						control.remover(newValue);
					} catch (SQLException | IOException | ParseException e1) {
						e1.printStackTrace();
					}
				});
					
			}
			
		});
		
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
						try {
							control.pesquisar("");
						} catch (IOException | ParseException e1) {
							e1.printStackTrace();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
			}
		});
	}
	
	public FuncionarioBoundary(Funcionario funcionario) {
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
		panCampos.add(btnSair, 2, 0);
		
		Label lblTitulo = new Label("Manutenção de Funcionarios");
		lblTitulo.setFont(new Font(30));
		panCampos.add(lblTitulo, 0, 1, 3, 1);
		
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
		
		panCampos.add(new Label("Logradouro: "), 0, 9);
		panCampos.add(txtLogradouro, 1, 9);
		
		panCampos.add(new Label("CEP: "), 0, 10);
		panCampos.add(txtCep, 1, 10);
		
		panCampos.add(new Label("Numero da Porta: "), 0, 11);
		panCampos.add(txtNumPorta, 1, 11);
		
		panCampos.add(new Label("Salario: "), 0, 12);
		panCampos.add(txtSalario, 1, 12);
		
		btnSalvar.setOnAction(this);
		panCampos.add(btnSalvar, 2, 13);
		btnExcluir.setOnAction(this);
		panCampos.add(btnExcluir, 0, 13);
		btnAtualizar.setOnAction(this);
		panCampos.add(btnAtualizar, 1, 13);
		
		tableTelefone.setMaxWidth(80);
		tableTelefone.setMaxHeight(200);
		panCampos.add(tableTelefone, 3, 2, 1, 6);
		panCampos.add(btnRemover, 3, 8);
		
		btnPesq.setOnAction(this);
		panCampos.add(btnPesq, 4, 2);
		panCampos.add(tableFuncionario, 4, 3, 1, 11);
		
		tela.getChildren().addAll(panCampos);
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
				control.pesquisar(txtNome.getText());
			} catch (SQLException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		if (event.getTarget() == btnSalvar) {
			Funcionario funcionario = boundaryToEntity();
			try {
				control.adicionar(funcionario);
			} catch (SQLException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Funcionario boundaryToEntity() {
		Usuario usuario = new Usuario();
		Funcionario funcionario = new Funcionario();
	
		try {
			usuario.setCPF(txtCpf.getText().replaceAll("\\D", ""));
			usuario.setNome(txtNome.getText());
			usuario.setEmail(txtEmail.getText());
			usuario.setSenha(txtSenha.getText());
			usuario.setNomeUsuario(txtNomeUsuario.getText());
			for(Telefone t : telefones) {
				t.setUsuario(usuario);
			}			
			usuario.setTelefones(telefones);
			funcionario.setUsuario(usuario);
			funcionario.setCEP(txtCep.getText().replaceAll("\\D", ""));
			funcionario.setLogradouro(txtLogradouro.getText());
			funcionario.setNumPorta(txtNumPorta.getText());
			funcionario.setSalario(Float.parseFloat(txtSalario.getText()));
		}catch (Exception e){
			
		}
		return funcionario;
	}
	
	private void entityToBoundary(Funcionario funcionario) {
		try {
			Usuario usuario = funcionario.getUsuario();
			txtCpf.setText(usuario.getCPF());
			txtNome.setText(usuario.getNome());
			txtEmail.setText(usuario.getEmail());
			txtNomeUsuario.setText(usuario.getNomeUsuario());
			
			telefones.clear();
			telefones.addAll(usuario.getTelefones());
			
			txtCep.setText(funcionario.getCEP());
			txtLogradouro.setText(funcionario.getLogradouro());
			txtNumPorta.setText(funcionario.getNumPorta());
			txtSalario.setText(funcionario.getSalario().toString());
		}catch (Exception e){
			
		}
		
	}

	@Override
	public Pane generateForm() {
		return tela;
	}
	
}

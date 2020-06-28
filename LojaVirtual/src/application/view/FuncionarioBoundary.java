package application.view;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.control.FuncionarioControl;
import application.model.Categoria;
import application.model.Funcionario;
import application.model.Idioma;
import application.model.Jogo;
import application.model.Plataforma;
import application.model.Usuario;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
	private TextField txtTelefone1 = new TextField();
	private TextField txtTelefone2 = new TextField();
	
	private TextField txtLogradouro = new TextField();
	private TextField txtCep = new TextField();
	private TextField txtNumPorta = new TextField();
	private TextField txtSalario = new TextField();
	
	private TableView<Funcionario> tableFuncionario = new TableView<>();
	private Funcionario funcionario = new Funcionario();
	private FuncionarioControl control = new FuncionarioControl();
	
	private Pane tela = new Pane();
	
	@SuppressWarnings("unchecked")
	public void generateTable() { 
		TableColumn<Funcionario, String> colCpf  = new TableColumn<>("CPF");
		colCpf.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("usuario.CPF"));
		
		TableColumn<Funcionario, String> colNome  = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("usuario.nome"));
		
		TableColumn<Funcionario, String> colEmail  = new TableColumn<>("E-Mail");
		colEmail.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("usuario.email"));
		
		TableColumn<Funcionario, Float> colSalario  = new TableColumn<>("Salario");
		colSalario.setCellValueFactory(new PropertyValueFactory<Funcionario, Float>("salario"));
		
		tableFuncionario.getColumns().addAll(colCpf, colNome, colEmail, colSalario);
		tableFuncionario.setItems(control.getLista());
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
		
		panCampos.add(new Label("Telefone 1: "), 0, 7);
		panCampos.add(txtTelefone1, 1, 7);
		
		panCampos.add(new Label("Telefone 2: "), 0, 8);
		panCampos.add(txtTelefone2, 1, 8);
		
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
		
		panCampos.add(btnPesq, 3, 2);
		panCampos.add(tableFuncionario, 3, 3, 1, 13);
		
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
			AdmBoundary admBoundary = new AdmBoundary(funcionario.getUsuario());
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
	}

	@Override
	public Pane generateForm() {
		return tela;
	}
	
}

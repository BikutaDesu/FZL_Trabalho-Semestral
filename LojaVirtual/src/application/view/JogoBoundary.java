package application.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;


import org.json.simple.parser.ParseException;

import application.control.CategoriaControl;
import application.control.IdiomaControl;
import application.control.JogosControl;
import application.control.PlataformaControl;
import application.model.Categoria;
import application.model.Funcionario;
import application.model.Idioma;
import application.model.Jogo;
import application.model.Plataforma;
import application.model.Requisito;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

public class JogoBoundary implements BoundaryContent, EventHandler<ActionEvent>{
	
	private Button btnSalvar = new Button("Salvar");
	private Button btnVoltar = new Button("Voltar");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnExcluir = new Button("Excluir");
	private Button btnSair = new Button("Sair");
	
	private FileChooser fileChooser = new FileChooser();
	private File url;
	private ImageView imgViewJogo = new ImageView();
	private Button btnBuscar = new Button("Buscar");
	
	private TextField txtNome = new TextField();
	private TextField txtPreco = new TextField();
	private TextField txtQtdJogo = new TextField();
	private TextField txtDataLancamento = new TextField();
	private TextField txtDesenvolvedora = new TextField();
	private TextField txtDistribuidora = new TextField();
	private TextField txtIimagem = new TextField();
	private TextField txtDescricao = new TextField();
	
	private Funcionario funcionario = new Funcionario();
	
	private TextField txtSO = new TextField();
	private TextField txtArmazenamento = new TextField();
	private TextField txtProcessador = new TextField();
	private TextField txtMemoria = new TextField();
	private TextField txtPlacaVideo = new TextField();
	private TextField txtDirectX = new TextField();
	
	private ComboBox<Idioma> boxIdioma = new ComboBox<Idioma>();
	private ObservableList<Idioma> listIdioma = FXCollections.observableArrayList();
	private Button btnAddIdioma = new Button("Adiconar");
	
	private ComboBox<Plataforma> boxPlataforma = new ComboBox<Plataforma>();
	private ObservableList<Plataforma> listPlataforma = FXCollections.observableArrayList();
	private Button btnAddPlataforma = new Button("Adiconar");
	
	private ComboBox<Categoria> boxCategoria = new ComboBox<Categoria>();
	private ObservableList<Categoria> listCategoria = FXCollections.observableArrayList();
	private Button btnAddCategoria = new Button("Adiconar");
	
	private Pane tela = new Pane();
	private TableView<Jogo> tableJogo = new TableView<>();
	private TableView<Idioma> tableIdiomas = new TableView<>();
	private TableView<Categoria> tableCategorias = new TableView<>();
	private TableView<Plataforma> tablePlataformas = new TableView<>();
	
	
	private JogosControl control = new JogosControl();
	
	public void generateTable() { 
		TableColumn<Jogo, String> colNome  = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<Jogo, String>("nome"));
		
		TableColumn<Jogo, Float> colPreco  = new TableColumn<>("Preço");
		colPreco.setCellValueFactory(new PropertyValueFactory<Jogo, Float>("preco"));
		
		TableColumn<Jogo, Integer> colQtdJogo  = new TableColumn<>("Estoque");
		colQtdJogo.setCellValueFactory(new PropertyValueFactory<Jogo, Integer>("qtdJogo"));
		
		TableColumn<Jogo, Date> colDataLancamento  = new TableColumn<>("Lançamento");
		colDataLancamento.setCellValueFactory(new PropertyValueFactory<Jogo, Date>("dataLancamento"));
		
		tableJogo.getColumns().addAll(colNome, colPreco, colQtdJogo, colDataLancamento);
		tableJogo.setItems(control.getLista());
		
		TableColumn<Idioma, String> colNomeIdioma  = new TableColumn<>("Idiomas");
		colNomeIdioma.setCellValueFactory(new PropertyValueFactory<Idioma, String>("nome"));
		
		tableIdiomas.getColumns().addAll(colNomeIdioma);
		tableIdiomas.setItems(listIdioma);
		
		TableColumn<Categoria, String> colNomeCategoria  = new TableColumn<>("Categorias");
		colNomeCategoria.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nome"));
		
		tableCategorias.getColumns().addAll(colNomeCategoria);
		tableCategorias.setItems((ObservableList<Categoria>) listCategoria);
		
		TableColumn<Plataforma, String> colNomePlataforma  = new TableColumn<>("Plataformas");
		colNomePlataforma.setCellValueFactory(new PropertyValueFactory<Plataforma, String>("nome"));
		
		tablePlataformas.getColumns().addAll(colNomePlataforma);
		tablePlataformas.setItems((ObservableList<Plataforma>) listPlataforma);
	}
	
	public JogoBoundary(Funcionario funcionario) throws SQLException, IOException, ParseException {
		this.funcionario = funcionario;

		generateTable();
		
		GridPane panCampos = new GridPane();
		
		btnVoltar.setOnAction(this);
		panCampos.add(btnVoltar, 0, 0);
		
		Label lblNome = new Label("Usuário: "+funcionario.getUsuario().getNome());
		lblNome.setFont(new Font(15));
		panCampos.add(lblNome, 1, 0);
		
		btnSair.setOnAction(this);
		panCampos.add(btnSair, 2, 0);
		
		Label lblTitulo = new Label("Manutenção de Jogos");
		lblTitulo.setFont(new Font(30));
		panCampos.add(lblTitulo, 0, 1, 2, 1);
		
		carregaIdioma();
		carregaPlataforma();
		carregaCategoria();
		
		panCampos.add(new Label("Nome: "), 0, 2);
		panCampos.add(txtNome, 1, 2);
		
		panCampos.add(new Label("Preço: "), 0, 3);
		panCampos.add(txtPreco, 1, 3);
		
		panCampos.add(new Label("Quantidade em Estoque: "), 0, 4);
		panCampos.add(txtQtdJogo, 1, 4);
		
		panCampos.add(new Label("Data de Lançamento: "), 0, 5);
		panCampos.add(txtDataLancamento, 1, 5);
		
		panCampos.add(new Label("Desenvolvedora: "), 0, 6);
		panCampos.add(txtDesenvolvedora, 1, 6);
		
		panCampos.add(new Label("Distribuidora: "), 0, 7);
		panCampos.add(txtDistribuidora, 1, 7);
		
		imgViewJogo.setImage(new Image("/img/image.jpg",240,240, false, false));
		panCampos.add(imgViewJogo, 5, 2, 3, 11);
		
		panCampos.add(new Label("Imagem: "), 0, 8);
		panCampos.add(txtIimagem, 1, 8);
		btnBuscar.setOnAction(e -> {
			url = fileChooser.showOpenDialog(null);
			txtIimagem.setText(url.getPath());
			Image imgJogo = new Image("file:///"+url, 240, 240, false, false);
			imgViewJogo.setImage(imgJogo);
		});
		panCampos.add(btnBuscar, 2, 8);
		
		panCampos.add(new Label("Descrição: "), 0, 9);
		panCampos.add(txtDescricao, 1, 9);
		
		lblTitulo = new Label("Requisitos");
		lblTitulo.setFont(new Font(15));
		panCampos.add(lblTitulo, 0, 10);
		
		panCampos.add(new Label("SO: "), 0, 11);
		panCampos.add(txtSO, 1, 11);
		
		panCampos.add(new Label("Armazenamento: "), 0, 12);
		panCampos.add(txtArmazenamento, 1, 12);
		
		panCampos.add(new Label("Processador: "), 0, 13);
		panCampos.add(txtProcessador, 1, 13);
		
		panCampos.add(new Label("Memória: "), 0, 14);
		panCampos.add(txtMemoria, 1, 14);
		
		panCampos.add(new Label("Placa de Video: "), 0, 15);
		panCampos.add(txtPlacaVideo, 1, 15);
		
		panCampos.add(new Label("DirectX: "), 0, 16);
		panCampos.add(txtDirectX, 1, 16);
		
		panCampos.add(new Label("Idioma: "), 0, 17);
		panCampos.add(boxIdioma, 1, 17);
		btnAddIdioma.setOnAction(e -> {
			if(!listIdioma.contains(boxIdioma.getValue())) {
				listIdioma.add(boxIdioma.getValue());
			}
		});
		panCampos.add(btnAddIdioma, 2, 17);
		
		panCampos.add(new Label("Plataforma: "), 0, 18);
		panCampos.add(boxPlataforma, 1, 18);
		btnAddPlataforma.setOnAction(e -> {
			if(!listPlataforma.contains(boxPlataforma.getValue())) {
				listPlataforma.add(boxPlataforma.getValue());
			}
		});
		panCampos.add(btnAddPlataforma, 2, 18);
		
		panCampos.add(new Label("Categoria: "), 0, 19);
		panCampos.add(boxCategoria, 1, 19);
		btnAddCategoria.setOnAction(e -> {
			if(!listCategoria.contains(boxCategoria.getValue())) {
				listCategoria.add(boxCategoria.getValue());
			}
		});
		panCampos.add(btnAddCategoria, 2, 19);
		
		tableIdiomas.setMaxWidth(80);
		tableIdiomas.setMaxHeight(200);
		panCampos.add(tableIdiomas, 5, 13, 1, 7);
		
		tablePlataformas.setMaxWidth(80);
		tablePlataformas.setMaxHeight(200);
		panCampos.add(tablePlataformas, 6, 13, 1, 7);
		
		tableCategorias.setMaxWidth(80);
		tableCategorias.setMaxHeight(200);
		panCampos.add(tableCategorias, 7, 13, 1, 7);
		
		btnExcluir.setOnAction(this);
		panCampos.add(btnExcluir, 0, 20);
		btnAtualizar.setDisable(true);
		btnAtualizar.setOnAction(this);
		panCampos.add(btnAtualizar, 1, 20);
		btnSalvar.setOnAction(this);
		panCampos.add(btnSalvar, 2, 20);
		
		panCampos.add(tableJogo, 8, 2, 1, 18);
		
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
		if (event.getTarget() == btnSalvar) {
			Jogo j = boundaryToEntity();
		}
	}
	
	private Jogo boundaryToEntity() {
		Jogo jogo = new Jogo();
		Requisito requisito = new Requisito();
				
		try {
			jogo.setNome(txtNome.getText());
			jogo.setPreco(Float.parseFloat(txtPreco.getText()));
			jogo.setQtdJogo(Integer.parseInt(txtQtdJogo.getText()));
			jogo.setDataLancamento(Date.valueOf(txtDataLancamento.getText()));
			jogo.setDesenvolvedora(txtDesenvolvedora.getText());
			jogo.setDistribuidora(txtDistribuidora.getText());
			jogo.setDescricao(txtDescricao.getText());
			jogo.setIdiomas(listIdioma);
			jogo.setPlataforma(listPlataforma);
			jogo.setCategoria(listCategoria);
			
			File currentDirFile = new File("");
			File copyImg = new File(currentDirFile.getAbsolutePath()+"/src/img/"+url.getName());

			Files.copy(url.toPath(), copyImg.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			jogo.setNomeImg(copyImg.toPath().toString());
			
			requisito.setSO(txtSO.getText());
			requisito.setArmazenamento(txtArmazenamento.getText());
			requisito.setProcessador(txtProcessador.getText());
			requisito.setMemoria(txtMemoria.getText());
			requisito.setPlacaVideo(txtPlacaVideo.getText());
			requisito.setDirectX(txtDirectX.getText());
			
			jogo.setRequisito(requisito);	
			
			System.out.println("Jogo Salvo");
		}catch (Exception e){
			System.out.println(e);
		}
		return null;
	}

	@Override
	public Pane generateForm() {
		return tela;
	}
	
	private void carregaIdioma() throws SQLException, IOException, ParseException {
		IdiomaControl idiomaControl = new IdiomaControl();
		boxIdioma.getItems().addAll(idiomaControl.getLista());
	}
	
	private void carregaPlataforma() throws SQLException, IOException, ParseException {
		PlataformaControl plataformaControl = new PlataformaControl();
		boxPlataforma.getItems().addAll(plataformaControl.getLista());
	}
	
	private void carregaCategoria() throws SQLException, IOException, ParseException{
		CategoriaControl categoriaControl = new CategoriaControl();
		boxCategoria.getItems().addAll(categoriaControl.getLista());
	}
}

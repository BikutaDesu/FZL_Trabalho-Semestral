package application.view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.control.JogosControl;
import application.control.PedidoControl;
import application.model.Categoria;
import application.model.Idioma;
import application.model.Jogo;
import application.model.Plataforma;
import application.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class JogoInfoBoundary implements BoundaryContent, EventHandler<ActionEvent> {
	private Button btnSair = new Button("Sair");
	private Button btnCarrinho = new Button("Carrinho");
	private Button btnVoltar = new Button("Voltar");
	private Button btnComprar = new Button("Comprar");
	private Button btnConfig = new Button("Configurar");
	
	private int index;
	private Usuario usuario;
	private PedidoControl pedido;
	
	private ObservableList<Idioma> listIdioma = FXCollections.observableArrayList();
	private ObservableList<Plataforma> listPlataforma = FXCollections.observableArrayList();
	private ObservableList<Categoria> listCategoria = FXCollections.observableArrayList();
	private TableView<Idioma> tableIdiomas = new TableView<>();
	private TableView<Categoria> tableCategorias = new TableView<>();
	private TableView<Plataforma> tablePlataformas = new TableView<>();
	
	private Pane tela = new Pane();
	
	private JogosControl jogosControl = new JogosControl();
	
	@SuppressWarnings("unchecked")
	public void generateTable() { 
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
	
	public JogoInfoBoundary(Usuario usuario, int index, PedidoControl pedido, Jogo j) throws SQLException, IOException, ParseException {
		this.usuario=usuario;
		this.index=index;
		this.pedido=pedido;
		
		generateTable();
		
		Jogo jogo = jogosControl.buscarJogo(j);
		
		GridPane panCampos = new GridPane();
		panCampos.setPadding(new Insets(20, 20, 10, 10));
		panCampos.setHgap(10);
		panCampos.setVgap(10);

		btnVoltar.setOnAction(this);
		panCampos.add(btnVoltar, 0, 0);
		
		Label lblNome = new Label("Usuário: "+usuario.getNome());
		lblNome.setFont(new Font(15));
		panCampos.add(lblNome, 1, 0);
		
		btnConfig.setOnAction(this);
		panCampos.add(btnConfig, 2, 0);
				
		btnSair.setOnAction(this);
		panCampos.add(btnSair, 3, 0);
		btnCarrinho.setOnAction(this);
		panCampos.add(btnCarrinho, 4, 0);
		
		panCampos.add(new Label("Nome: " + jogo.getNome()), 0, 2, 3 ,1);
		panCampos.add(new Label("Preço: " + jogo.getPreco()), 0, 3, 3 ,1);
		panCampos.add(new Label("Quantidade em Estoque: " + jogo.getQtdJogo()), 0, 4, 3 ,1);
		panCampos.add(new Label("Data de Lançamento: " + jogo.getDataLancamento()), 0, 5, 3 ,1);
		panCampos.add(new Label("Desenvolvedora: " + jogo.getDesenvolvedora()), 0, 6, 3 ,1);
		panCampos.add(new Label("Distribuidora: " + jogo.getDistribuidora()), 0, 7, 3 ,1);
		ImageView imgViewJogo = new ImageView();
		try {
			File currentDirFile = new File("");
			imgViewJogo.setImage(new Image("file:///"+currentDirFile.getAbsolutePath()+"/src/img/"+jogo.getNomeImg(), 250, 250, false, false));
			panCampos.add(imgViewJogo, 5, 2, 3, 11);
		}catch (Exception e) {
		}
		panCampos.add(new Label("Descrição: " + jogo.getDescricao()), 0, 9, 3 ,1);
		Label lblTitulo = new Label("Requisitos");
		lblTitulo.setFont(new Font(15));
		panCampos.add(lblTitulo, 0, 10, 3 ,1);
		panCampos.add(new Label("SO: " + jogo.getRequisito().getSO()), 0, 11, 3 ,1);
		panCampos.add(new Label("Armazenamento: " + jogo.getRequisito().getArmazenamento()), 0, 12, 3 ,1);
		panCampos.add(new Label("Processador: " + jogo.getRequisito().getProcessador()), 0, 13, 3 ,1);
		panCampos.add(new Label("Memória: " + jogo.getRequisito().getMemoria()), 0, 14, 3 ,1);
		panCampos.add(new Label("Placa de Video: " + jogo.getRequisito().getPlacaVideo()), 0, 15, 3 ,1);
		panCampos.add(new Label("DirectX: " + jogo.getRequisito().getDirectX()), 0, 16, 3 ,1);
		
		listCategoria.addAll(jogo.getCategoria());
		listIdioma.addAll(jogo.getIdiomas());
		listPlataforma.addAll(jogo.getPlataforma());

		tableIdiomas.setMaxWidth(110);
		tableIdiomas.setMaxHeight(200);
		panCampos.add(tableIdiomas, 0, 17);

		tablePlataformas.setMaxWidth(84);
		tablePlataformas.setMaxHeight(200);
		panCampos.add(tablePlataformas, 1, 17);

		tableCategorias.setMaxWidth(80);
		tableCategorias.setMaxHeight(200);
		panCampos.add(tableCategorias, 2, 17);
		
		btnComprar.setOnAction(e -> {
			pedido.adicionar(jogo);
		});
		panCampos.add(btnComprar, 5, 13);
		
		tela.getChildren().add(panCampos);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnSair) {
			LoginBoundary login = new LoginBoundary();
			tela.getChildren().clear();
			tela.getChildren().add(login.generateForm());
		}
		if (event.getTarget() == btnCarrinho) {
			PedidoBoundary pedidoBoundary = new PedidoBoundary(usuario, index, pedido);
			tela.getChildren().clear();
			tela.getChildren().add(pedidoBoundary.generateForm());
		}
		if (event.getTarget() == btnVoltar) {
			try {
				ClienteBoundary clienteBoundary = new ClienteBoundary(usuario, index, pedido);
				tela.getChildren().clear();
				tela.getChildren().add(clienteBoundary.generateForm());
			} catch (SQLException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		if (event.getTarget() == btnConfig) {
			try {
				ClienteConfigBoundary clienteConfigBoundary = new ClienteConfigBoundary(usuario, pedido);
				tela.getChildren().clear();
				tela.getChildren().add(clienteConfigBoundary.generateForm());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Pane generateForm() {
		return tela;
	}
}

package application.view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import application.control.PedidoControl;
import application.control.JogosControl;
import application.model.Jogo;
import application.model.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class ClienteBoundary implements BoundaryContent, EventHandler<ActionEvent>{
	private Button btnSair = new Button("Sair");
	private Button btnProximo = new Button("Proximo");
	private Button btnAnterior = new Button("Anterior");
	private Button btnCarrinho = new Button("Carrinho");
	private Button btnConfig = new Button("Configurar");
	
	private JogosControl jogosControl = new JogosControl();
	private List<Jogo> jogos = new ArrayList<Jogo>();
	private int index;
	private Usuario usuario;
	private PedidoControl pedido;
	
	private Pane tela = new Pane();
	
	public ClienteBoundary(Usuario usuario, int index, PedidoControl pedido) throws SQLException, IOException, ParseException {
		this.usuario=usuario;
		this.index=index;
		this.pedido=pedido;
		
		if(index<=0) btnAnterior.setDisable(true);
		
		jogos.addAll(jogosControl.getLista());
		
		GridPane panCampos = new GridPane();
		panCampos.setPadding(new Insets(20, 20, 10, 10));
		panCampos.setHgap(10);
		panCampos.setVgap(10);

		Label lblNome = new Label("Usuário: "+usuario.getNome());
		lblNome.setFont(new Font(15));
		panCampos.add(lblNome, 1, 0);
				
		btnConfig.setOnAction(this);
		panCampos.add(btnConfig, 2, 0);
		
		btnSair.setOnAction(this);
		panCampos.add(btnSair, 3, 0);
		btnCarrinho.setOnAction(this);
		panCampos.add(btnCarrinho, 4, 0);
		
		Label lblTitulo = new Label("Catálogo de Jogos");
		lblTitulo.setFont(new Font(30));
		panCampos.add(lblTitulo, 1, 1, 3, 1);
		
		FlowPane flowPane = new FlowPane();
		
		for(int i=index; i<index+16 ; i++) {
			try {
				Jogo jogo = jogos.get(i);
				GridPane panJogos = new GridPane();
				panJogos.setPadding(new Insets(20, 20, 10, 10));
				panJogos.setHgap(20);
				panJogos.setVgap(20);
				File currentDirFile = new File("");
				Image imgJogo = new Image("file:///"+currentDirFile.getAbsolutePath()+"/src/img/"+jogo.getNomeImg(), 150, 150, false, false);
				ImageView imgViewJogo = new ImageView();
				imgViewJogo.setImage(imgJogo);
				panJogos.add(imgViewJogo, 0, 0);
				
				
				Label txtNomeJogo = new Label(jogo.getNome());
				txtNomeJogo.setMaxWidth(150);
				panJogos.add(txtNomeJogo,0,1);
				panJogos.add(new Label(jogo.getPreco().toString()),0,2);
				
				Button btnComprar = new Button("Comprar");
				
				if(jogo.getQtdJogo()<1) {
					btnComprar.setText("Indisponivel");
					btnComprar.setDisable(true);					
				}else {
					btnComprar.setOnAction(e -> {
						try {
							JogoInfoBoundary jogoInfoBoundary = new JogoInfoBoundary(usuario, index, pedido, jogo);
							tela.getChildren().clear();
							tela.getChildren().add(jogoInfoBoundary.generateForm());
						} catch (SQLException | IOException | ParseException e1) {
							e1.printStackTrace();
						}
					});
				}
				panJogos.add(btnComprar,0,3);
				
				flowPane.getChildren().add(panJogos);
			}catch (Exception e) {
				btnProximo.setDisable(true);
				break;
			}
		}		
		panCampos.add(flowPane, 0, 2, 120, 100);
		
		panCampos.add(btnAnterior, 1, 77);
		btnAnterior.setOnAction(this);
		
		panCampos.add(btnProximo, 129, 77);
		btnProximo.setOnAction(this);
		
		tela.getChildren().add(panCampos);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnSair) {
			LoginBoundary login = new LoginBoundary();
			tela.getChildren().clear();
			tela.getChildren().add(login.generateForm());
		}
		if (event.getTarget() == btnProximo) {
			try {
				ClienteBoundary clienteBoundary = new ClienteBoundary(usuario, index+12, pedido);
				btnAnterior.setDisable(false);
				tela.getChildren().clear();
				tela.getChildren().add(clienteBoundary.generateForm());
			} catch (SQLException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		if (event.getTarget() == btnAnterior) {
			try {
				ClienteBoundary clienteBoundary = new ClienteBoundary(usuario, index-12, pedido);
				btnProximo.setDisable(false);
				tela.getChildren().clear();
				tela.getChildren().add(clienteBoundary.generateForm());
			} catch (SQLException | IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		if (event.getTarget() == btnCarrinho) {
			PedidoBoundary pedidoBoundary = new PedidoBoundary(usuario, index, pedido);
			tela.getChildren().clear();
			tela.getChildren().add(pedidoBoundary.generateForm());
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

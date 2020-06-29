package application.view;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.parser.ParseException;

import application.control.PedidoControl;
import application.model.Jogo;
import application.model.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class PedidoBoundary implements BoundaryContent, EventHandler<ActionEvent> {
	private Button btnVoltar = new Button("Voltar");
	private Button btnFinalizar = new Button("Finalizar Compra");
	private Button btnConfig = new Button("Configurar");
	private Pane tela = new Pane();
	private PedidoControl pedido;
	private Usuario usuario;
	private int index;
	
	public PedidoBoundary(Usuario usuario, int index, PedidoControl pedido) {
		this.usuario=usuario;
		this.index=index;
		this.pedido=pedido;
		
		GridPane panCampos = new GridPane();
		panCampos.setPadding(new Insets(20, 20, 10, 10));
		panCampos.setHgap(10);
		panCampos.setVgap(10);

		Label lblNome = new Label("Usuário: "+usuario.getNome());
		lblNome.setFont(new Font(15));
		panCampos.add(lblNome, 2, 0);
		
		btnConfig.setOnAction(this);
		panCampos.add(btnConfig, 3, 0);
				
		btnVoltar.setOnAction(this);
		panCampos.add(btnVoltar, 1, 0);
		
		FlowPane flowPane = new FlowPane();
		float total = 0;
		for(Jogo j : pedido.getList()) {
			GridPane panJogos = new GridPane();
			Button btnRemover = new Button("Remover");
			Label jogo = new Label();
			jogo.setText(jogo.getText()+j.getNome()+"\n"+j.getPreco());
			jogo.setMinWidth(300);
			panJogos.add(jogo, 0, 0);
			panJogos.add(btnRemover, 2, 0, 1, 2);
			btnRemover.setOnAction(e -> {
				pedido.remover(j);
				PedidoBoundary pedidoBoundary = new PedidoBoundary(usuario, index, pedido);
				tela.getChildren().clear();
				tela.getChildren().add(pedidoBoundary.generateForm());
			});
			total+=j.getPreco();		
			flowPane.getChildren().add(panJogos);
		}
		
		flowPane.setMaxWidth(400);
		panCampos.add(flowPane, 1, 3, 5, 100);
		
		panCampos.add(new Label("Total: "+total), 90, 1);
		panCampos.add(btnFinalizar, 90, 2);
		btnFinalizar.setOnAction(this);
		
		tela.getChildren().add(panCampos);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnFinalizar) {
			try {
				pedido.finalizarCompra();
				alertaMensagem(AlertType.INFORMATION, "Compra Finalizada!", 
						null, String.format("A compra finalizada com sucesso"));
				try {
					ClienteBoundary clienteBoundary = new ClienteBoundary(usuario, index, new PedidoControl(usuario));
					tela.getChildren().clear();
					tela.getChildren().add(clienteBoundary.generateForm());
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		try {
			ClienteConfigBoundary clienteConfigBoundary = new ClienteConfigBoundary(usuario, pedido);
			tela.getChildren().clear();
			tela.getChildren().add(clienteConfigBoundary.generateForm());
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

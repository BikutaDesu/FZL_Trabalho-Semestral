package application.control;

import java.sql.SQLException;
import java.util.List;

import application.dao.PedidoDAO;
import application.model.Jogo;
import application.model.Pedido;
import application.model.Usuario;

public class PedidoControl {
	private Pedido pedido;
	private PedidoDAO pedidoDAO = new PedidoDAO();
	
	public PedidoControl(Usuario usuario) {
		this.pedido = new Pedido();
		this.pedido.setUsuarioPedido(usuario);
	}
	
	public void adicionar(Jogo jogo) {
		if(!pedido.getJogos().contains(jogo)) {
			pedido.addJogo(jogo);
		}
	}
	
	public void remover(Jogo jogo) {
		pedido.remJogo(jogo);
	}
	
	public void finalizarCompra() throws SQLException {
		pedidoDAO.insert(pedido);
	}
	
	public List<Jogo> getList(){
		return pedido.getJogos();
	}
	
	public List<Pedido> buscarPedidos(Usuario usuario) throws SQLException{
		return pedidoDAO.selectPedidos(usuario);
	}

}

package application.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {

	private Integer ID;
	private Date dataPedido;
	private Usuario usuarioPedido;
	private List<Jogo> jogos = new ArrayList<Jogo>();

	public Pedido(Integer ID, Date dataPedido, Usuario usuarioPedido, List<Jogo> jogos) {
		super();
		this.ID = ID;
		this.dataPedido = dataPedido;
		this.usuarioPedido = usuarioPedido;
		this.jogos = jogos;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Usuario getUsuarioPedido() {
		return usuarioPedido;
	}

	public void setUsuarioPedido(Usuario usuarioPedido) {
		this.usuarioPedido = usuarioPedido;
	}

	public List<Jogo> getJogos() {
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}

}

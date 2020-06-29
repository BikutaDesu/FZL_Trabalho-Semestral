package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

	private Integer ID;
	private LocalDate dataPedido = LocalDate.now();
	private Usuario usuarioPedido;
	private List<Jogo> jogos = new ArrayList<Jogo>();

	public Pedido() {
		
	}
	
	public Pedido(Integer ID, LocalDate dataPedido, Usuario usuarioPedido, List<Jogo> jogos) {
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

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
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

	public void addJogo(Jogo jogo) {
		this.jogos.add(jogo);
	}
	
	public void remJogo(Jogo jogo) {
		this.jogos.remove(jogo);
	}
	
	public Jogo getJogo() {
		return jogos.get(0);
	}
}

package application.domain;

public class Telefone {

	private Integer numero;
	private Usuario usuario;

	public Telefone(Integer numero, Usuario usuario) {
		super();
		this.numero = numero;
		this.usuario = usuario;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

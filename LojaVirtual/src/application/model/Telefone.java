package application.model;

public class Telefone {

	private String numero;
	private Usuario usuario;

	public Telefone() {
		
	}
	
	public Telefone(String numero, Usuario usuario) {
		super();
		this.numero = numero;
		this.usuario = usuario;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

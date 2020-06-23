package application.model;

public class TipoUsuario {
	
	private Integer ID;
	private String nome;
	
	public TipoUsuario(Integer ID, String nome) {
		super();
		this.ID = ID;
		this.nome = nome;
	}
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer ID) {
		this.ID = ID;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

}

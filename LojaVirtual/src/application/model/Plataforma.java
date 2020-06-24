package application.model;

public class Plataforma {

	private Integer ID;
	private String nome;

	public Plataforma() {
	}
	
	public Plataforma(Integer ID, String nome) {
		super();
		this.ID = ID;
		this.nome = nome;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}

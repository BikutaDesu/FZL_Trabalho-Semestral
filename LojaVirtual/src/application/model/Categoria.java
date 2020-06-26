package application.model;

public class Categoria {

	private Integer ID;
	private String nome;

	public Categoria() {
		
	}
	
	public Categoria(Integer ID, String nome) {
		super();
		this.ID = ID;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}

}

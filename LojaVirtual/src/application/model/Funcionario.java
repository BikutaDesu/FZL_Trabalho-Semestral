package application.model;

public class Funcionario {
	
	private Usuario usuario;
	private String logradouro;
	private String CEP;
	private String numPorta;
	private Float salario;
	
	public Funcionario() {
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String CEP) {
		this.CEP = CEP;
	}

	public String getNumPorta() {
		return numPorta;
	}

	public void setNumPorta(String numPorta) {
		this.numPorta = numPorta;
	}

	public Float getSalario() {
		return salario;
	}

	public void setSalario(Float salario) {
		this.salario = salario;
	}
	
	
}

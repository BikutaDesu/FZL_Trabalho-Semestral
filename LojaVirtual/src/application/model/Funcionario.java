package application.model;

public class Funcionario {
	
	private Usuario usuario;
	private String logradouro;
	private String CEP;
	private String numPorta;
	private Float salario;
	
	public Funcionario() {
<<<<<<< HEAD
		
	}
 	
	public Funcionario(Usuario usuario, String logradouro, String CEP, String numPorta, Float salario) {
		super();
		this.usuario = usuario;
		this.logradouro = logradouro;
		this.CEP = CEP;
		this.numPorta = numPorta;
		this.salario = salario;
=======
>>>>>>> 95819a4709b3fa9bda4aac007a6f42df3824c728
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

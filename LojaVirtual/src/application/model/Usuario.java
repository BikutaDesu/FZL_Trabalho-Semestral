package application.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

	private Integer ID;
	private String nome;
	private String email;
	private String senha;
	private String nomeUsuario;
	private TipoUsuario tipoUsuario;
	private List<Telefone> telefones = new ArrayList<Telefone>();

	public Usuario(Integer ID, String nome, String email, String senha, String nomeUsuario, TipoUsuario tipoUsuario,
			List<Telefone> telefones) {
		super();
		this.ID = ID;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.nomeUsuario = nomeUsuario;
		this.tipoUsuario = tipoUsuario;
		this.telefones = telefones;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

}

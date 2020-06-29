package application.dao;

import application.model.Categoria;
import application.model.Idioma;
import application.model.Plataforma;

public class ModeloPesquisa {
	private String nome;
	private Idioma idioma = new Idioma();
	private Categoria categoria = new Categoria();
	private Plataforma plataforma = new Plataforma();
	
	public ModeloPesquisa() {
		this.nome="";
		this.idioma.setID(0);
		this.categoria.setID(0);
		this.plataforma.setID(0);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Plataforma getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(Plataforma plataforma) {
		this.plataforma = plataforma;
	}
}

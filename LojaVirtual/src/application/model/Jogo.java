package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Jogo {

	private Integer ID;
	private String nome;
	private Float preco;
	private Integer qtdJogo;
	private LocalDate dataLancamento = LocalDate.now();
	private String desenvolvedora;
	private String distribuidora;
	private String nomeImg;
	private String descricao;
	private Requisito requisito;
	
	private Funcionario funcionario;
	private List<Idioma> idiomas = new ArrayList<Idioma>();
	private List<Plataforma> plataforma = new ArrayList<Plataforma>();
	private List<Categoria> categoria = new ArrayList<Categoria>();
	
	public Jogo(Integer ID, String nome, Float preco, Integer qtdJogo, LocalDate dataLancamento, String desenvolvedora,
			String distribuidora, String nomeImg, String descricao, Requisito requisito) {
		super();
		this.ID = ID;
		this.nome = nome;
		this.preco = preco;
		this.qtdJogo = qtdJogo;
		this.dataLancamento = dataLancamento;
		this.desenvolvedora = desenvolvedora;
		this.distribuidora = distribuidora;
		this.nomeImg = nomeImg;
		this.descricao = descricao;
		this.requisito = requisito;
	}
	
	public Jogo() {
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

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public Integer getQtdJogo() {
		return qtdJogo;
	}

	public void setQtdJogo(Integer qtdJogo) {
		this.qtdJogo = qtdJogo;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getDesenvolvedora() {
		return desenvolvedora;
	}

	public void setDesenvolvedora(String desenvolvedora) {
		this.desenvolvedora = desenvolvedora;
	}

	public String getDistribuidora() {
		return distribuidora;
	}

	public void setDistribuidora(String distribuidora) {
		this.distribuidora = distribuidora;
	}

	public String getNomeImg() {
		return nomeImg;
	}

	public void setNomeImg(String nomeImg) {
		this.nomeImg = nomeImg;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Requisito getRequisito() {
		return requisito;
	}

	public void setRequisito(Requisito requisito) {
		this.requisito = requisito;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Idioma> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<Idioma> idiomas) {
		this.idiomas = idiomas;
	}

	public List<Plataforma> getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(List<Plataforma> plataforma) {
		this.plataforma = plataforma;
	}

	public List<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}	
}

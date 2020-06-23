package application.model;

public class Requisito {

	private Integer ID;
	private String SO;
	private String armazenamento;
	private String processador;
	private String memoria;
	private String placaVideo;
	private String directX;

	public Requisito() {
		super();
	}

	public Requisito(Integer ID, String SO, String armazenamento, String processador, String memoria, String placaVideo,
			String directX) {
		super();
		this.ID = ID;
		this.setSO(SO);
		this.armazenamento = armazenamento;
		this.processador = processador;
		this.memoria = memoria;
		this.placaVideo = placaVideo;
		this.directX = directX;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public String getArmazenamento() {
		return armazenamento;
	}

	public void setArmazenamento(String armazenamento) {
		this.armazenamento = armazenamento;
	}

	public String getProcessador() {
		return processador;
	}

	public void setProcessador(String processador) {
		this.processador = processador;
	}

	public String getMemoria() {
		return memoria;
	}

	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}

	public String getPlacaVideo() {
		return placaVideo;
	}

	public void setPlacaVideo(String placaVideo) {
		this.placaVideo = placaVideo;
	}

	public String getDirectX() {
		return directX;
	}

	public void setDirectX(String directX) {
		this.directX = directX;
	}

	public String getSO() {
		return SO;
	}

	public void setSO(String SO) {
		this.SO = SO;
	}

	@Override
	public String toString() {
		return "[ID=" + ID + ", SO=" + SO + ", armazenamento=" + armazenamento + ", processador="
				+ processador + ", memoria=" + memoria + ", placaVideo=" + placaVideo + ", directX=" + directX + "]";
	}
	
	

}

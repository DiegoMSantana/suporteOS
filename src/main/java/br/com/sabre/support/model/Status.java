package br.com.sabre.support.model;

public enum Status {

	ABERTO("Aberto"), FECHADO("Fechado"), ANALISE("Em Análise");

	private String status;

	private Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}

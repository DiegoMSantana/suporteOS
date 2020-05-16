package br.com.sabre.support.model;

public enum Situacao {

	ATIVO("Ativo"), INATIVO("Inativo");

	private String situacao;

	private Situacao(String situacao) {
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao;
	}

}

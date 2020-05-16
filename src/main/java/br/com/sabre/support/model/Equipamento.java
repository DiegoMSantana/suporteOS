package br.com.sabre.support.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Equipamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private String marca;
	private String modelo;
	private String serie;
	private String condicoes;
	private String defeitos;
	private String acessorios;
	private String solucao;
	private String laudoTecnico;
	private String termoGarantia;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getCondicoes() {
		return condicoes;
	}

	public void setCondicoes(String condicoes) {
		this.condicoes = condicoes;
	}

	public String getDefeitos() {
		return defeitos;
	}

	public void setDefeitos(String defeitos) {
		this.defeitos = defeitos;
	}

	public String getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(String acessorios) {
		this.acessorios = acessorios;
	}

	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	public String getLaudoTecnico() {
		return laudoTecnico;
	}

	public void setLaudoTecnico(String laudoTecnico) {
		this.laudoTecnico = laudoTecnico;
	}

	public String getTermoGarantia() {
		return termoGarantia;
	}

	public void setTermoGarantia(String termoGarantia) {
		this.termoGarantia = termoGarantia;
	}

}

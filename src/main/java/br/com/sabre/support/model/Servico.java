package br.com.sabre.support.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_servico")
public class Servico extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String descricao;

	@NotNull
	private String detalhes;

	@NotNull
	private Integer quantidade;

	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario = BigDecimal.ZERO;

	// @Column(name = "valor_total")
	// private BigDecimal valorTotal = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "codigo_ordem_servico")
	private OrdemServico ordemServico;

	// Getters and Setters

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getValorTotal() {
		return valorUnitario.multiply(new BigDecimal(quantidade));
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

}

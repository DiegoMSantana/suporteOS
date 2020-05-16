package br.com.sabre.support.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_item_servico")
public class ItemServico extends GenericDomain {

	private static final long serialVersionUID = 1L;

	private Integer quantidade;

	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;

	@Column(name = "codigo_servico")
	private Servico servico;

	@ManyToOne
	@JoinColumn(name = "codigo_ordem_servico")
	private OrdemServico ordemServico;

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

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	// Métodos auxiliares da classe

	public BigDecimal getValorTotalItemServico() {
		return valorUnitario.multiply(new BigDecimal(quantidade));
	}

}

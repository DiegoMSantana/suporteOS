package br.com.sabre.support.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_ordem_servico")
public class OrdemServico extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "codigo_cliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;

	@Enumerated(EnumType.STRING)
	private Status situacao;

	@Column(name = "data_criacao")
	private LocalDate dataCriacao;

	@Column(name = "previsao_entrega")
	private LocalDate previsaoEntrega;

	@Column(name = "canal_venda")
	private String canalVenda;

	@Embedded
	private Equipamento equipamento;

	// @Column(name = "data_ordem_servico")
	// private LocalDate dataOrdemServico = LocalDate.now();

	@Column(name = "total")
	private BigDecimal valorTotalOrdem = BigDecimal.ZERO;

	@Column(name = "observacoes")
	private String observacoes;

	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
	private List<Servico> itensServico = new ArrayList<>();

	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
	private List<ItemProduto> itensProduto = new ArrayList<>();

	@Transient
	private String uuid;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDate getPrevisaoEntrega() {
		return previsaoEntrega;
	}

	public void setPrevisaoEntrega(LocalDate previsaoEntrega) {
		this.previsaoEntrega = previsaoEntrega;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Status getSituacao() {
		return situacao;
	}

	public void setSituacao(Status situacao) {
		this.situacao = situacao;
	}

	public String getCanalVenda() {
		return canalVenda;
	}

	public void setCanalVenda(String canalVenda) {
		this.canalVenda = canalVenda;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public BigDecimal getValorTotalOrdem() {
		return valorTotalOrdem;
	}

	public void setValorTotalOrdem(BigDecimal valorTotalOrdem) {
		this.valorTotalOrdem = valorTotalOrdem;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public List<Servico> getItensServico() {
		return itensServico;
	}

	public void setItensServico(List<Servico> itensServico) {
		this.itensServico = itensServico;
	}

	public List<ItemProduto> getItensProduto() {
		return itensProduto;
	}

	public void setItensProduto(List<ItemProduto> itensProduto) {
		this.itensProduto = itensProduto;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	// Métodos auxiliares relacionados ao modelo acima

	public boolean isNova() {
		return getId() == null;
	}

	public void adicionarItensProduto(List<ItemProduto> itens) {
		this.itensProduto = itens;
		this.itensProduto.forEach(i -> i.setOrdemServico(this));
	}

	public void adicionarItensServico(List<Servico> itens) {
		this.itensServico = itens;
		this.itensServico.forEach(i -> i.setOrdemServico(this));
	}

	public BigDecimal getValorTotalItensProdutos() {
		return getItensProduto().stream().map(ItemProduto::getValorTotalItemProduto).reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}

	public BigDecimal getValorTotalItensServicos() {
		return getItensServico().stream().map(Servico::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	public void calcularValorTotal() {
		this.valorTotalOrdem = calcularValorTotal(getValorTotalItensProdutos(), getValorTotalItensServicos());
	}

	private BigDecimal calcularValorTotal(BigDecimal valorTotalItensProdutos, BigDecimal valorTotalItensServicos) {
		BigDecimal valorTotal = valorTotalItensProdutos
				.add(Optional.ofNullable(valorTotalItensServicos).orElse(BigDecimal.ZERO));
		return valorTotal;
	}

}

package br.com.sabre.support.session;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.sabre.support.model.ItemProduto;
import br.com.sabre.support.model.Produto;
import br.com.sabre.support.model.Servico;

@Component
@SessionScope
public class TabelaItensSession {

	// Tabela de itens de produtos
	Set<TabelaItensProduto> tabelaProduto = new HashSet<>();

	// Tabela de itens de servico
	Set<TabelaItensServico> tabelaServico = new HashSet<>();

	// Metodos da tabela de produtos

	public void adicionarItemProduto(String uuid, Produto produto, Integer quantidade) {
		TabelaItensProduto tabela = buscarTabelaProdutoPorId(uuid);
		tabela.AdicionarItem(produto, quantidade);
		tabelaProduto.add(tabela);
	}

	public void alterarQuantidadeProduto(String uuid, Produto produto, Integer quantidade) {
		TabelaItensProduto tabela = buscarTabelaProdutoPorId(uuid);
		tabela.alterarQuantidadeCerveja(produto, quantidade);
	}

	public void exluirItemProduto(String uuid, Produto produto) {
		TabelaItensProduto tabela = buscarTabelaProdutoPorId(uuid);
		tabela.excluirItem(produto);
	}

	public List<ItemProduto> getItensProduto(String uuid) {
		return buscarTabelaProdutoPorId(uuid).getItens();
	}

	public BigDecimal getValorTotalProdutos(String uuid) {
		return buscarTabelaProdutoPorId(uuid).getValorTotalProdutos();
	}

	// Metodos da tabela de serviço

	public void adicionarItemServico(String uuid, Servico service, Integer quantidade) {
		TabelaItensServico tabela = buscarTabelaServicoPorId(uuid);
		tabela.adicionarItem(service, quantidade);
		tabelaServico.add(tabela);
	}

	public void alterarQuantidadeServico(String uuid, Servico service, Integer quantidade) {
		TabelaItensServico tabela = buscarTabelaServicoPorId(uuid);
		tabela.alterarQuantidade(service, quantidade);
	}

	public void removerItemServico(String uuid, String descricao) {
		TabelaItensServico tabela = buscarTabelaServicoPorId(uuid);
		tabela.removerItem(descricao);
	}

	public List<Servico> getItensServico(String uuid) {
		return buscarTabelaServicoPorId(uuid).getItens();
	}

	public BigDecimal getValorTotalServicos(String uuid) {
		return buscarTabelaServicoPorId(uuid).getValorTotalServicos();
	}

	// Private methods

	private TabelaItensProduto buscarTabelaProdutoPorId(String uuid) {
		TabelaItensProduto tabela = tabelaProduto.stream().filter(t -> t.getUuid().equals(uuid)).findAny()
				.orElse(new TabelaItensProduto(uuid));
		return tabela;
	}

	private TabelaItensServico buscarTabelaServicoPorId(String uuid) {
		TabelaItensServico tabela = tabelaServico.stream().filter(t -> t.getUuid().equals(uuid)).findAny()
				.orElse(new TabelaItensServico(uuid));
		return tabela;
	}

}

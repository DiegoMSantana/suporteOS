package br.com.sabre.support.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import br.com.sabre.support.model.ItemProduto;
import br.com.sabre.support.model.Produto;

public class TabelaItensProduto {

	private String uuid;
	private List<ItemProduto> itensProduto = new ArrayList<ItemProduto>();

	public TabelaItensProduto(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getValorTotalProdutos() {
		return itensProduto.stream().map(ItemProduto::getValorTotalItemProduto).reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}

	public void AdicionarItem(Produto produto, Integer quantidade) {

		Optional<ItemProduto> itemVendaOptional = buscarItemPorProduto(produto);

		ItemProduto item = null;

		if (itemVendaOptional.isPresent()) {
			item = itemVendaOptional.get();
			item.setQuantidade(item.getQuantidade() + quantidade);

		} else {

			item = new ItemProduto();
			item.setProduto(produto);
			item.setQuantidade(quantidade);
			item.setValorUnitario(produto.getPrecoUnitario());

			itensProduto.add(0, item);
		}
	}

	public void alterarQuantidadeCerveja(Produto produto, Integer quantidade) {
		ItemProduto venda = buscarItemPorProduto(produto).get();
		venda.setQuantidade(quantidade);
	}

	public void excluirItem(Produto produto) {
		int indice = IntStream.range(0, itensProduto.size())
				.filter(i -> itensProduto.get(i).getProduto().equals(produto)).findAny().getAsInt();
		itensProduto.remove(indice);
	}

	public int total() {
		return itensProduto.size();
	}

	public List<ItemProduto> getItens() {
		return itensProduto;
	}

	public String getUuid() {
		return uuid;
	}

	// Private métodos

	private Optional<ItemProduto> buscarItemPorProduto(Produto produto) {
		return itensProduto.stream().filter(i -> i.getProduto().equals(produto)).findAny();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabelaItensProduto other = (TabelaItensProduto) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}

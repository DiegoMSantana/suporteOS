package br.com.sabre.support.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import br.com.sabre.support.model.Servico;

public class TabelaItensServico {

	private String uuid;
	private List<Servico> itensServico = new ArrayList<>();

	public TabelaItensServico(String uuid) {
		this.uuid = uuid;
	}

	// retorna valor total
	public BigDecimal getValorTotalServicos() {
		return itensServico.stream().map(Servico::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	// Adiciona serviço ou incrementa quantidade de serviço existente
	public void adicionarItem(Servico service, Integer quantidade) {

		Optional<Servico> optionalServico = buscarItemPorServico(service);

		Servico itemServico = null;
		if (optionalServico.isPresent()) {

			itemServico = optionalServico.get();
			// System.out.println(itemServico.getQuantidade() + quantidade);
			itemServico.setQuantidade(itemServico.getQuantidade() + quantidade);
			itemServico.setValorUnitario(service.getValorUnitario());

		} else {

			itemServico = new Servico();
			itemServico.setDescricao(service.getDescricao());
			itemServico.setDetalhes(service.getDetalhes());
			itemServico.setQuantidade(quantidade);
			itemServico.setValorUnitario(service.getValorUnitario());

			itensServico.add(0, itemServico);

		}
	}

	// Altera a quantidade
	public void alterarQuantidade(Servico service, Integer quantidade) {
		Servico servico = buscarItemPorServico(service).get();
		servico.setQuantidade(quantidade);

	}

	// remove um item do array
	public void removerItem(String descricao) {
		int indice = IntStream.range(0, itensServico.size())
				.filter(i -> itensServico.get(i).getDescricao().equals(descricao)).findAny().getAsInt();
		itensServico.remove(indice);

	}

	public int total() {
		return itensServico.size();
	}

	public List<Servico> getItens() {
		return itensServico;
	}

	public String getUuid() {
		return uuid;
	}

	// Private Methods
	private Optional<Servico> buscarItemPorServico(Servico service) {
		return itensServico.stream().filter(i -> i.getDescricao().equals(service.getDescricao())).findAny();
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
		TabelaItensServico other = (TabelaItensServico) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}

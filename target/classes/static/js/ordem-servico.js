Suporte.OrdemServico = (function() {

	function OrdemServico(tabelaItensServico, tabelaItensProduto) {

		this.tabelaItensServico = tabelaItensServico;
		this.tabelaItensProduto = tabelaItensProduto;

		this.valorTotalBox = $('.js-valor-total-box');
		this.valorTotalItens = this.tabelaItensServico.valorTotal();

		this.valorTotalItensProduto = this.tabelaItensProduto
				.valorTotalProduto();
	}

	OrdemServico.prototype.iniciar = function() {

		this.tabelaItensServico.on('tabela-itens-atualizada',
				onTabelaItensAtualizada.bind(this));

		this.tabelaItensServico.on('tabela-itens-atualizada',
				onValoresAlterados.bind(this));

		this.tabelaItensProduto.on('tabela-itens-produto-atualizada',
				onTabelaProdutosAtualizada.bind(this));

		this.tabelaItensProduto.on('tabela-itens-produto-atualizada',
				onValoresAlterados.bind(this));

		onValoresAlterados.call(this);
	}

	// Serviço
	function onTabelaItensAtualizada(evento, valorTotalItens) {
		this.valorTotalItens = valorTotalItens == null ? 0 : valorTotalItens;
	}

	// Produtos
	function onTabelaProdutosAtualizada(evento, valorTotalProdutos) {
		this.valorTotalItensProduto = valorTotalProdutos == null ? 0
				: valorTotalProdutos;
	}

	function onValoresAlterados() {

		// var valorTotal = numeral(this.valorTotalItens) +
		// numeral(this.valorFrete) - numeral(this.valorDesconto);
		var valorTotal = numeral(this.valorTotalItens)
				+ numeral(this.valorTotalItensProduto);
		this.valorTotalBox.html(Suporte.formatarMoeda(valorTotal));

		// this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);

	}

	return OrdemServico;

}());
$(function() {

	var produtoAutocomplete = new Suporte.AutoCompleteProduto();
	produtoAutocomplete.iniciar();

	var tabelaItensServico = new Suporte.TabelaItensServico();
	tabelaItensServico.iniciar();

	var tabelaItensProduto = new Suporte.TabelaItensProduto(produtoAutocomplete);
	tabelaItensProduto.iniciar();

	var ordemServico = new Suporte.OrdemServico(tabelaItensServico,
			tabelaItensProduto);
	ordemServico.iniciar();

});

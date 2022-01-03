Suporte.TabelaItensProduto = (function() {

	
	function TabelaItensProduto(produtoAutocomplete) {
		this.produtoAutocomplete = produtoAutocomplete;	
	}

	TabelaItensProduto.prototype.iniciar = function() {
		
		this.produtoAutocomplete.on('item-selecionado', onItemSelecionado.bind(this));
		
		this.modalProduto = $('#modal-produto');
		this.botaoAtualizar = this.modalProduto.find('.js-modal-inserir-produto-btn');
		this.uuid = $('#uuid').val();
		
		this.valorTotalItensProduto = $('.js-valor-total-itens-produto');
		
		// Usado para renderizar o array de itens de produto em html
		this.tabelaProdutosContainer = $('.js-tabela-produto-container');
	
		// Emissor de eventos customizado
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
		
		modificaQuantidade.call(this);
		
	}
	
	TabelaItensProduto.prototype.valorTotalProduto = function() {
		return this.valorTotalItensProduto.data('valor-total-produto');
	}
	
	function onItemSelecionado(evento, item){

		var resposta = $.ajax({
			url: 'ordem/produto/item',
			method: 'POST',
			data: {
				codigoProduto: item.id,
				uuid: this.uuid
			}
		});
		
		resposta.done(onItemAtualizadoNoServidor.bind(this));
	}
	
	function onItemAtualizadoNoServidor(html){
		
		this.tabelaProdutosContainer.html(html);
		
		modificaQuantidade.call(this);
		var valorItensAdicionados = $('.js-valor-produto').data(
		'valor-total-itens-produto');
		this.emitter.trigger('tabela-itens-produto-atualizada', valorItensAdicionados);
		
	}
	
	function modificaQuantidade(){
		var quantidadeItemInput = $('.js-tabela-produto-quantidade-item');
		quantidadeItemInput.on('change', onQuantidadeAlterado.bind(this));
		quantidadeItemInput.maskMoney({precision: 0, thousands: ''});
	}
	
	function onQuantidadeAlterado(evento){
		
		var input = $(evento.target);
		var quantidade = input.val();
		var codigoProduto = input.data('codigo-produto');
		
		if(quantidade <= 0) {
			input.val(1);
			quantidade = 1;
		}
		
		var resposta = $.ajax({
			url: 'ordem/produto/item/' + codigoProduto,
			method: 'PUT',
			data: {
				quantidade: quantidade,
				uuid: this.uuid
				
			}
		});
		
		resposta.done(onItemAtualizadoNoServidor.bind(this));
		
	}

	return TabelaItensProduto;

}());

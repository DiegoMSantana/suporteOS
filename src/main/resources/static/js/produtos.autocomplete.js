Suporte = Suporte || {};

Suporte.AutoCompleteProduto = (function() {
	
	function AutoCompleteProduto() {
		
		this.nomeProduto = $('.js-produto-nome-input');
		this.tabelaConfig = $('#produtoConfiguracoes');
		this.btnFecharModal = $('.js-btn-fechar');
		
		var htmlTemplateAutocomplete = $('#template-autocomplete-produto').html();
		this.template = Handlebars.compile(htmlTemplateAutocomplete);
		
		this.botaoEnviarProduto = $('#modal-inserir-produto-btn');
		
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	AutoCompleteProduto.prototype.iniciar = function() {
		
		var options = {
			url: function(nomeProduto) {
				return this.nomeProduto.data('url') + '?nomeProduto=' + nomeProduto;
			}.bind(this),
			getValue: 'nomeProduto',
			minCharNumber: 3,
			requestDelay: 300,
			ajaxSettings: {
				contentType: 'application/json'
			},
			template: {
				type: 'custom',
				method: template.bind(this)
			},
			
			list: {
				onChooseEvent: onItemSelecionado.bind(this)
			}
		};
		
		this.nomeProduto.easyAutocomplete(options);
		this.btnFecharModal.on('click', onFecharModal.bind(this));
		this.botaoEnviarProduto.on('click', enviarProdutoSelecionado.bind(this));
	}
	
	function onItemSelecionado() {
		
		var produtos = this.nomeProduto.getSelectedItemData();
		
		this.tabelaConfig.slideDown("slow", function(){
			
			$('#precoUnitario').val(Suporte.formatarMoeda(produtos.precoUnitario));
			$('#codigo-interno').val(produtos.codigoInterno);
			$('#detalhesProduto').val(produtos.detalhes);
			$('#quantidade-produto').val(1);
			
		});
		
		this.nomeProduto.focus();
	}
	
	function enviarProdutoSelecionado(){
		this.emitter.trigger('item-selecionado',this.nomeProduto.getSelectedItemData());
		onFecharModal.call(this);
	}
	
	function template(nome, produto) {
		produto.valorFormatado = Suporte.formatarMoeda(produto.precoUnitario);
		return this.template(produto);
	}
	
	function onFecharModal(){
		
		$('#modal-produto').modal('hide');
		$('#produto').val('');
		$('#precoUnitario').val('');
		$('#codigo-interno').val('');
		
		this.tabelaConfig.hide();
	}
	
	return AutoCompleteProduto;
	
}());




Suporte.TabelaItensServico = (function() {

	function TabelaItensServico() {

		this.modalServico = $('#modal-default');
		this.botaoAtualizar = this.modalServico.find('.js-modal-inserir-servico-btn');
		this.uuid = $('#uuid').val();
		
		this.valorTotalItensServico = $('.js-valor-total-itens-servico');

		// Usado para renderizar o array de itens de serviço em html
		this.tabelaServicosContainer = $('.js-tabela-servicos-container');

		// Campos do modal de serviços
		this.descricao = $('#descricao');
		this.quantidade = $('#quantidadeServico');
		this.valorUnitario = $('#valorUnitario');
		this.detalhes = $('#detalhes');

		// Emissor de eventos customizado
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);

	}

	TabelaItensServico.prototype.iniciar = function() {
		

		this.botaoAtualizar.on('click', function() {
			//event.preventDefault();
			onInserirServico.call(this);
		}.bind(this));

		onRemoverServico.call(this);

	}

	TabelaItensServico.prototype.valorTotal = function() {
		return this.valorTotalItensServico.data('valor-total-servico');
	}

	function onInserirServico() {

		var resposta = $.ajax({
			url : 'ordem/item',
			method : 'POST',
			beforeSend : function() {
				console.log("Carregando...");
			},
			data : {
				descricao : this.descricao.val(),
				quantidade : this.quantidade.val(),
				valorUnitario : this.valorUnitario.val(),
				detalhes : this.detalhes.val(),
				uuid: this.uuid
			}
		});

		onExecutaAcoesExtras.call(this);
		resposta.done(onItemServicoAtualizadoServidor.bind(this));
	}

	function onItemServicoAtualizadoServidor(html) {

		this.tabelaServicosContainer.html(html);
		onRemoverServico.call(this);

		var valorItensAdicionados = $('.js-valor-servico').data(
				'valor-total-itens-servico');
		this.emitter.trigger('tabela-itens-atualizada', valorItensAdicionados);
		
		$('.js-valor-itens-servico').html(valorItensAdicionados);

	}

	function onRemoverServico() {

		$('.js-remover-servico-btn').on('click', function(evento) {

			var descricaoServico = $(evento.target).data('descricao-servico');
			var resposta = $.ajax({
				url : 'ordem/item/' +  this.uuid + '/' + descricaoServico,
				method : 'DELETE'
			});
			resposta.done(onItemServicoAtualizadoServidor.bind(this));
		}.bind(this));

	}

	function onExecutaAcoesExtras() {

		// Comportamentos relacionados a serviços
		
		this.modalServico.modal('hide');
		$('.bw-tabela-cervejas__vazio').remove();
		$('.js-valor-servico').remove();

		this.descricao.val('');
		this.quantidade.val('');
		this.valorUnitario.val('');
		this.detalhes.val('');
	}

	return TabelaItensServico;

}());

Suporte = Suporte || {};

Suporte.Autocomplete = (function() {
	
	function Autocomplete() {
		this.nomeInput = $('.js-nome-cliente-input');
		//var htmlTemplateAutocomplete = $('#template-autocomplete-cerveja').html();
		//this.template = Handlebars.compile(htmlTemplateAutocomplete);
		//this.emitter = $({});
		//this.on = this.emitter.on.bind(this.emitter);
	}
	
	Autocomplete.prototype.iniciar = function() {
		var options = {
			url: function(nomeInput) {
				return this.nomeInput.data('url') + '?nomeCliente=' + nomeInput;
			}.bind(this),
			getValue: 'nome',
			minCharNumber: 3,
			requestDelay: 300,
			ajaxSettings: {
				contentType: 'application/json'
			},
			
			/*template: {
				type: 'custom',
				method: template.bind(this)
			},*/
			
			list: {
				onChooseEvent: onItemSelecionado.bind(this)
			}
			
		};
		
		this.nomeInput.easyAutocomplete(options);
	}
	
	function onItemSelecionado() {
		//this.emitter.trigger('item-selecionado',this.skuOuNomeInput.getSelectedItemData());
		//this.skuOuNomeInput.val('');
		//this.skuOuNomeInput.focus();
			
		var clientes = this.nomeInput.getSelectedItemData();
		$('#codigoCliente').val(clientes.id);
	}
	
	/*
	function template(nome, cerveja) {
		cerveja.valorFormatado = Brewer.formatarMoeda(cerveja.valor);
		return this.template(cerveja);
		
	}
	*/
	
	return Autocomplete;
	
}());

$(function() {
	var auto = new Suporte.Autocomplete();
	auto.iniciar();
});



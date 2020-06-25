var Support = Support || {};

Support.Datatable = (function() {

	function Datatable() {
		
		// Id da tabela do datatable
		this.tabela = $("#example2");
		
		//Campos da janela modal Id´s
		this.idCliente = $('#idCliente')
		this.nome = $('#nome');
		this.cpfCnpj = $('#cpfOuCnpj');
		this.email = $('#email');
		this.razaoSocial = $('#razaoSocial');
		this.checkboxIsento = $('#checkboxIsento');
		this.situacaoCliente = $('#situacao');
		this.telefoneComercial = $('#telefone');
		this.celular = $('#celular');
		this.observacoes = $('#observacoes');
		
		//Classes
		this.inputInscricaoEstadual = $('.js-input-inscEstadual');
		
		// Seletores relacionados ao evento de atualização
		this.modal = $('#modal-default');
		this.botaoAtualizar = this.modal.find('.js-modal-atualizar-btn');
		
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		
		
	}

	Datatable.prototype.iniciar = function() {
		
		this.tabela.on('click','a.btn-informacao', function(e){
			var idSelecionado = $(e.currentTarget);
			buscarClienteNoServidor.call(this, $(idSelecionado));
		}.bind(this));
		
		this.botaoAtualizar.on('click', function(){
			event.preventDefault();
			onAtualizarCliente.call(this);
		}.bind(this));
		
	}
	
	function buscarClienteNoServidor(idSelecionado) {
		
		var identificador = idSelecionado.data('id');
		
		var resposta = $.ajax({
			url : '/suporte/cadastros/cliente/buscar/' + identificador,
			method : 'GET',
			//contentType : 'application/json'
			/*data : JSON.stringify({
				id : identificador
			}),*/
		});
		
		resposta.done(onRetornoSucesso.bind(this));
	}
	
	function onRetornoSucesso(resp){
		
		this.idCliente.val(resp.id);
		this.nome.val(resp.nome);
		this.cpfCnpj.val(resp.cpfOuCnpj);
		this.email.val(resp.email);
		this.razaoSocial.val(resp.razaoSocial);
		this.telefoneComercial.val(resp.telefone);
		this.celular.val(resp.celular);
		this.observacoes.val(resp.observacoes);
		
		
		var inscricaoEstadual = resp.insc_estadual;
		if(inscricaoEstadual === "Isento"){
			
			this.checkboxIsento.prop('checked', true);
			this.checkboxIsento.attr("disabled", true);
			this.inputInscricaoEstadual.val('');
			this.inputInscricaoEstadual.attr("disabled", true);
			
			this.cpfCnpj.attr("disabled", "disabled");
		}else {
			this.checkboxIsento.prop('checked', false);
			this.checkboxIsento.attr("disabled", true);
			this.inputInscricaoEstadual.attr("disabled", true);
			this.inputInscricaoEstadual.val(inscricaoEstadual);
			this.cpfCnpj.attr("disabled", true);
		}
		
		var comboSituacao = resp.situacao;
		if(comboSituacao === "ATIVO"){
			this.situacaoCliente.val("ATIVO");
		}else{
			this.situacaoCliente.val("INATIVO");
		}
	}
	
	function onErrorProcessar(resposta){
		console.log("Erro ao processar!");
	}
	
	// Funções relacionadas a atualização de cliente
	
	function onAtualizarCliente(idSelecionado){
		
		//var data = {} - Cria objeto para passar na função, funciona mas é desnecessário uma vez que se pode passar no padrão que está configurado.
		//data["nome"] = $("#nome").val();
		
		//console.log(this.idCliente.val());
		
		var resposta = $.ajax({
			url : this.url + "/" + this.idCliente.val(),
			method : 'PUT',
			contentType : 'application/json',
			//dataType: 'json', não funciona aqui, verificar depois a função desse parâmetro.
			data : JSON.stringify({
				situacao: this.situacaoCliente.val(),
				razaoSocial: this.razaoSocial.val(), 
				nome: this.nome.val(),
				email:	this.email.val()
			})
		});
		
		resposta.done(onRetornoClienteAtualizado.bind(this));
		resposta.fail(onRetornoClienteErro.bind(this));
	}
	
	function onRetornoClienteAtualizado(resposta){
		// Fecha a tela modal
		this.modal.modal('hide');
		// Recarrega datatable mantendo na mesma página
		this.tabela.DataTable().ajax.reload(null, false);
	}
	
	function onRetornoClienteErro(objError){
		console.log(arguments);
	}

	return Datatable;

}());

$(function() {
	var datatable = new Support.Datatable();
	datatable.iniciar();
});
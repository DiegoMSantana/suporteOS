Suporte = Suporte || {};

Suporte.DialogoExcluir = (function(){
	
	
	function DialogoExcluir(){
		this.tabela = $("#example2");
		//this.btnExclusao = $('.js-exclusao-btn');	
	}
	
	DialogoExcluir.prototype.iniciar = function(){
		this.tabela.on('click', 'a.js-exclusao-btn', onExcluirClicado.bind(this));
		
		if(window.location.search.indexOf('excluido') > -1){
			swal('Pronto!', 'Excluido com sucesso', 'success');
		}	
	}
	
	function onExcluirClicado(evento){
		evento.preventDefault();
		
		var botaoClicado = $(evento.currentTarget);
		var codigo = botaoClicado.data('codigo');
		
		swal({
			title: 'Tem Certeza?',
			text: 'Você não poderá recuperar depois.',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, exclua agora.',
			closeOnConfirm: false
			
		}, onExcluirConfirmado.bind(this, codigo));
	}
	
	function onExcluirConfirmado(codigo){
		
		$.ajax({
			url: 'cliente/' + codigo,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: onErrorExcluir.bind(this)
		});
	}
	
	function onExcluidoSucesso(){
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;
	}
	
	function onErrorExcluir(e){
		swal('Oops!', e.responseText, 'error');
	}
	
	return DialogoExcluir;
	
	
}());


$(function() {
	var dialogoExcluir = new Suporte.DialogoExcluir();
	dialogoExcluir.iniciar();
});
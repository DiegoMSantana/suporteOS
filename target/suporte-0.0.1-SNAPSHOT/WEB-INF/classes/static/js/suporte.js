var Suporte = Suporte || {};

Suporte.MaskMoney = (function() {
	
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
		this.plain.maskMoney({ precision: 0, thousands: '.' });
	}
	
	return MaskMoney;
	
}());

Suporte.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

numeral.language('pt-br');

Suporte.formatarMoeda = function(valor) {
	return numeral(valor).format('0,0.00');
}

Suporte.recuperarValor = function(valorFormatado) {
	return numeral().unformat(valorFormatado);
}


Suporte.Menu = (function() {
	
	function Menu() {
		this.cadastros = $('.js-cadastros');
		this.ordem = $('.js-ordem');
		this.agenda = $('.js-agenda');
	}
	
	Menu.prototype.iniciar = function() {
		
		// Classe para menu cadastro
		this.cadastros.on("click", function(){
			$('.js-cadastros').addClass("active");
			$('.js-ordem').removeClass("active");
			$('.js-agenda').removeClass("active");
		}.bind(this));
		
		// Classe para menu ordem serviço
		this.ordem.on("click", function(){
			$('.js-ordem').addClass("active");
			$('.js-cadastros').removeClass("active");
			$('.js-agenda').removeClass("active");
		}.bind(this));
		
		this.agenda.on("click", function(){
			$('.js-agenda').addClass("active");
			$('.js-cadastros').removeClass("active");
			$('.js-ordem').removeClass("active");
		}.bind(this));
		
	}
		
	return Menu;
	
}());

Suporte.GerarCodigo = (function() {
	
	function GerarCodigo() {
		this.btnGerarCodigo = $('.js-codigo-interno-btn');
		this.inputCodigoInterno = $('.js-codigo-interno-input');
	}
	
	GerarCodigo.prototype.iniciar = function() {
		
		this.btnGerarCodigo.on('click', onGerarCodigoInterno.bind(this));
	}
	
function onGerarCodigoInterno() {
		
		var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		var text = "";
		var result="";
		
		for( var i=0; i < 5; i++ ){
	      text += possible.charAt(Math.floor(Math.random() * possible.length));
	     }
		
		for(var i=0; i<9; i++){
		result += Math.floor(Math.random()*8).toString(8).toUpperCase();
		}
		this.inputCodigoInterno.val(text + result);
	}
		
	return GerarCodigo;
	
}());


$(function() {
	var security = new Suporte.Security();
	security.enable();
	
	var maskMoney = new Suporte.MaskMoney();
	maskMoney.enable();
	
	var menu = new Suporte.Menu();
	menu.iniciar();
	
	var geradorCodigo = new Suporte.GerarCodigo();
	geradorCodigo.iniciar();
});
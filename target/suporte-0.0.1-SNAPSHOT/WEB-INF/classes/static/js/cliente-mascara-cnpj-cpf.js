var Support = Support || {};

Support.MascaraCpfCnpj = (function() {

	function MascaraCpfCnpj() {

		this.selectTipoPessoa = $('.js-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpfOuCnpj]');
		this.labelNomeFantasia = $('[for=nomeFantasia]');
		this.labelRgOuRazaoSocial = $('[for=rgOuRazasocial]');
		this.inputCpfCnpj = $('#cpfOuCnpj');
		this.inputRazaoSocial = $('.js-razao-social');
		

		this.checkboxIsento = $('.js-checkbox-isento');
		this.inputInscricaoEstadual = $('.js-input-inscEstadual');

	}

	MascaraCpfCnpj.prototype.iniciar = function() {
		

		this.selectTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));

		var tipoPessoaSelecionada = this.selectTipoPessoa.filter(':checked')[0];
		var inscricaoChecado = this.checkboxIsento.is(':checked');

		if (tipoPessoaSelecionada) {
			aplicarMascara.call(this, $(tipoPessoaSelecionada));
		}

		this.checkboxIsento.on('change', onAtivaDesativaCheckbox.bind(this));

	}

	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionada = $(evento.currentTarget);

		aplicarMascara.call(this, tipoPessoaSelecionada);

		this.inputCpfCnpj.val('');
		this.inputRazaoSocial.val('');

	}

	function aplicarMascara(tipoPessoaSelecionada) {

		if (tipoPessoaSelecionada.val() === "FISICA") {

			this.labelCpfCnpj.text(tipoPessoaSelecionada.data('documento'));
			this.labelRgOuRazaoSocial.text(tipoPessoaSelecionada.data('razao'));
			this.labelNomeFantasia.html('Nome');
			this.inputCpfCnpj.removeAttr('disabled');
			this.inputCpfCnpj.inputmask({
				"mask" : tipoPessoaSelecionada.data('mascara')
			});
			this.checkboxIsento.prop('checked', false);
			this.checkboxIsento.attr("disabled", true);
			this.inputInscricaoEstadual.val('');
			this.inputInscricaoEstadual.attr("disabled", true);

		} else {

			this.labelCpfCnpj.text(tipoPessoaSelecionada.data('documento'));
			this.labelRgOuRazaoSocial.text(tipoPessoaSelecionada.data('razao'));
			this.labelNomeFantasia.html('Nome Fantasia');
			this.inputCpfCnpj.removeAttr('disabled');
			this.inputCpfCnpj.inputmask({
				"mask" : tipoPessoaSelecionada.data('mascara')
			});
			this.checkboxIsento.removeAttr('disabled');
			this.inputInscricaoEstadual.removeAttr("disabled");

		}
	}

	function onAtivaDesativaCheckbox() {

		var checkboxMarcado = this.checkboxIsento.is(':checked');

		if (checkboxMarcado) {
			this.inputInscricaoEstadual.val('');
			this.inputInscricaoEstadual.attr('disabled', true);
		} else {
			this.inputInscricaoEstadual.removeAttr("disabled");
		}
	}

	return MascaraCpfCnpj;

}());

$(function() {
	var mascaraCpfCnpj = new Support.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
});
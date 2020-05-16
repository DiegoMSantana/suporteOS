package br.com.sabre.support.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.sabre.support.model.Servico;

public class ServicoConverter implements Converter<String, Servico> {

	@Override
	public Servico convert(String codigo) {

		if (!StringUtils.isEmpty(codigo)) {

			Servico servico = new Servico();
			servico.setId(Long.valueOf(codigo));
			return servico;
		}

		return null;
	}

}

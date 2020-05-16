package br.com.sabre.support.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.sabre.support.model.Cliente;

public class ClienteConverter implements Converter<String, Cliente> {

	@Override
	public Cliente convert(String codigo) {

		if (!StringUtils.isEmpty(codigo)) {

			Cliente cliente = new Cliente();
			cliente.setId(Long.valueOf(codigo));
			return cliente;
		}

		return null;

	}

}

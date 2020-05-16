package br.com.sabre.support.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import br.com.sabre.support.model.Cliente;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente> {

	@Override
	public List<Class<?>> getValidationGroups(Cliente cliente) {

		List<Class<?>> grupo = new ArrayList<>();

		grupo.add(Cliente.class);

		if (isPessoaSelecionada(cliente)) {
			grupo.add(cliente.getTipoPessoa().getGrupo());
		}

		return grupo;
	}

	private boolean isPessoaSelecionada(Cliente cliente) {
		return cliente != null && cliente.getTipoPessoa() != null;
	}

}

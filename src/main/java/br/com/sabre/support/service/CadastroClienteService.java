package br.com.sabre.support.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sabre.support.model.Cliente;
import br.com.sabre.support.repository.Clientes;
import br.com.sabre.support.service.exception.CpfCnpjClienteJaCadastradoException;
import br.com.sabre.support.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;

	@Transactional
	public void Salvar(Cliente cliente) {

		Optional<Cliente> clienteExistente = clientes.findByCpfOuCnpj(cliente.getCpfOuCnpj());

		if (clienteExistente.isPresent()) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}

		clientes.save(cliente);
	}

	public List<Cliente> getAllEmployees() {

		/*
		 * Pageable paging = new PageRequest(pageNo, pageSize); Page<Cliente> pageResult
		 * = clientes.findAll(paging);
		 * 
		 * if (pageResult.getContent() != null) { return new ArrayList<Cliente>(); }
		 * else { return null; }
		 */

		return clientes.findAll();
	}

	@Transactional
	public void atualizar(Cliente cliente, Long id) {

		Cliente customer = clientes.findOne(id);

		customer.setSituacao(cliente.getSituacao());
		customer.setNome(cliente.getNome());
		customer.setRazaoSocial(cliente.getRazaoSocial());
		customer.setEmail(cliente.getEmail());

		clientes.saveAndFlush(customer);
	}

	@Transactional
	public void excluir(Cliente cliente) {

		try {
			clientes.delete(cliente);
			clientes.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("\"Impossível apagar cliente. Já foi usado em alguma O.S\"");
		}

	}

}

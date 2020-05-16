package br.com.sabre.support.repository.helper.clientes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.sabre.support.dto.ClienteDTO;
import br.com.sabre.support.model.Cliente;

public interface ClientesQueries {

	public Page<Cliente> filtrar(Pageable pageable, String search);

	public List<ClienteDTO> porNome(String nomeCliente);

}

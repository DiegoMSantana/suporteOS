package br.com.sabre.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sabre.support.model.Cliente;
import br.com.sabre.support.repository.helper.clientes.ClientesQueries;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {

}

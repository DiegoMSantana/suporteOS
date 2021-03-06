package br.com.sabre.support.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sabre.support.model.Cidade;

public interface Cidades extends JpaRepository<Cidade, Long> {

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);

}

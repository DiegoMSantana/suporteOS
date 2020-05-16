package br.com.sabre.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sabre.support.model.OrdemServico;

public interface OrdemServicos extends JpaRepository<OrdemServico, Long> {

}

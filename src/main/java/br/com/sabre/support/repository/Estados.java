package br.com.sabre.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sabre.support.model.Estado;

public interface Estados extends JpaRepository<Estado, Long> {

}

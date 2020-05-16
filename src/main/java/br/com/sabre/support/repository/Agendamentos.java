package br.com.sabre.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sabre.support.model.Agenda;
import br.com.sabre.support.repository.helper.agendamentos.AgendamentosQueries;

public interface Agendamentos extends JpaRepository<Agenda, Long>, AgendamentosQueries {

}

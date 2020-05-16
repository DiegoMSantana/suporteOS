package br.com.sabre.support.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sabre.support.model.Agenda;
import br.com.sabre.support.repository.Agendamentos;
import br.com.sabre.support.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroAgendaService {

	@Autowired
	private Agendamentos agendamentos;

	@Transactional
	public void salvar(Agenda agenda) {

		agenda.setStart(converte(agenda.getDataHoraInicio()));
		agenda.setEnd(converte(agenda.getDataHoraFim()));

		agendamentos.saveAndFlush(agenda);
	}

	@Transactional
	public void atualizar(Agenda agenda, Long id) {
		Agenda agenda2 = agendamentos.findOne(id);

		agenda2.setTitle(agenda.getTitle());
		agenda2.setStart(converte(agenda.getDataHoraInicio()));
		agenda2.setEnd(converte(agenda.getDataHoraFim()));
		agenda2.setColor(agenda.getColor());
		agenda2.setObservacoes(agenda.getObservacoes());

		agendamentos.saveAndFlush(agenda2);
	}

	private LocalDateTime converte(String valor) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(valor, formatter);
	}

	@Transactional
	public void deletarEvento(Agenda agenda) {
		try {
			agendamentos.delete(agenda);
			agendamentos.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("\"Não foi possível deletar o evento\"");
		}

	}

}

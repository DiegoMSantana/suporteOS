package br.com.sabre.support.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sabre.support.model.OrdemServico;
import br.com.sabre.support.repository.OrdemServicos;

@Service
public class CadastroOrdemServico {

	@Autowired
	private OrdemServicos ordem;

	@Transactional
	public void salvarOrdemServico(OrdemServico ordemServico) {

		if (ordemServico.isNova()) {
			ordemServico.setDataCriacao(LocalDate.now());
		}

		ordem.saveAndFlush(ordemServico);
	}

}

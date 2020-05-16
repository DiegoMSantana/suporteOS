package br.com.sabre.support.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sabre.support.model.Produto;
import br.com.sabre.support.repository.Produtos;
import br.com.sabre.support.service.event.ProdutoSalvoEvent;

@Service
public class CadastroProdutoService {

	@Autowired
	private Produtos produtos;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void salvar(Produto produto) {
		produtos.save(produto);

		publisher.publishEvent(new ProdutoSalvoEvent(produto));
	}

}

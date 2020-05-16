package br.com.sabre.support.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.sabre.support.storage.FotoStorage;

@Component
public class ProdutoListener {

	@Autowired
	private FotoStorage fotoStorage;

	@EventListener(condition = "#evento.temFoto()")
	public void produtoSalvar(ProdutoSalvoEvent evento) {
		fotoStorage.salvar(evento.getProduto().getFoto());

	}
}

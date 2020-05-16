package br.com.sabre.support.repository.helper.produtos;

import java.util.List;

import br.com.sabre.support.dto.ProdutoDTO;

public interface ProdutosQueries {

	public List<ProdutoDTO> porNomeProduto(String nomeProduto);

}

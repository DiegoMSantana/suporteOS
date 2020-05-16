package br.com.sabre.support.repository.helper.produtos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.sabre.support.dto.ProdutoDTO;

public class ProdutosImpl implements ProdutosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<ProdutoDTO> porNomeProduto(String nomeProduto) {

		String jpql = "select new br.com.sabre.support.dto.ProdutoDTO(id, nomeProduto, codigoInterno, precoUnitario, detalhes, foto) "
				+ "from Produto where lower(nomeProduto) like lower(:nomeProduto)";

		List<ProdutoDTO> produtosFiltrados = manager.createQuery(jpql, ProdutoDTO.class)
				.setParameter("nomeProduto", "%" + nomeProduto + "%").getResultList();
		return produtosFiltrados;
	}

}

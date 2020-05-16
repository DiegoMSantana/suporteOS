package br.com.sabre.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sabre.support.model.Produto;
import br.com.sabre.support.repository.helper.produtos.ProdutosQueries;

public interface Produtos extends JpaRepository<Produto, Long>, ProdutosQueries {

}

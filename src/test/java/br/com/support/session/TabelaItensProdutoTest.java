package br.com.support.session;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.com.sabre.support.model.Produto;
import br.com.sabre.support.session.TabelaItensProduto;

public class TabelaItensProdutoTest {

	private TabelaItensProduto itensProduto;

	@Before
	public void setUp() {
		this.itensProduto = new TabelaItensProduto("1");
	}

	@Test
	public void deveCalcularTabelaProdutosSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO, this.itensProduto.getValorTotalProdutos());
	}

	@Test
	public void deveCalcularValorTotalComUmItem() throws Exception {

		Produto p1 = new Produto();
		p1.setNomeProduto("Notebook");
		BigDecimal valor = new BigDecimal("1200.00");
		p1.setPrecoUnitario(valor);

		this.itensProduto.AdicionarItem(p1, 1);
		assertEquals(valor, this.itensProduto.getValorTotalProdutos());

	}

	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {

		Produto p1 = new Produto();
		p1.setId(1L);
		p1.setNomeProduto("Notebook2");
		BigDecimal valor = new BigDecimal("1000.00");
		p1.setPrecoUnitario(valor);

		Produto p2 = new Produto();
		p2.setId(2L);
		p2.setNomeProduto("Mochila");
		BigDecimal valor2 = new BigDecimal("250.00");
		p2.setPrecoUnitario(valor2);

		Produto p3 = new Produto();
		p3.setId(3L);
		p3.setNomeProduto("cabo de rede");
		BigDecimal valor3 = new BigDecimal("10.00");
		p3.setPrecoUnitario(valor3);

		this.itensProduto.AdicionarItem(p1, 1);
		this.itensProduto.AdicionarItem(p2, 1);
		this.itensProduto.AdicionarItem(p3, 1);

		assertEquals(new BigDecimal("1260.00"), this.itensProduto.getValorTotalProdutos());

	}

	@Test
	public void deveManterTamanhoDaListaParaMesmosItens() throws Exception {

		Produto p1 = new Produto();
		p1.setId(1L);
		p1.setNomeProduto("Notebook2");
		BigDecimal valor = new BigDecimal("1000.00");
		p1.setPrecoUnitario(valor);

		this.itensProduto.AdicionarItem(p1, 1);
		this.itensProduto.AdicionarItem(p1, 1);

		assertEquals(1, this.itensProduto.total());

	}

	@Test
	public void deveAlterarQuantidadeDoItem() throws Exception {

		Produto p1 = new Produto();
		p1.setId(1L);
		p1.setNomeProduto("Notebook2");
		BigDecimal valor = new BigDecimal("1000.00");
		p1.setPrecoUnitario(valor);

		this.itensProduto.AdicionarItem(p1, 1);
		this.itensProduto.alterarQuantidadeCerveja(p1, 3);

		assertEquals(new BigDecimal("3000.00"), this.itensProduto.getValorTotalProdutos());

	}

	@Test
	public void deveRemoverUmItem() throws Exception {

		Produto p1 = new Produto();
		p1.setId(1L);
		p1.setNomeProduto("Notebook2");
		BigDecimal valor = new BigDecimal("1000.00");
		p1.setPrecoUnitario(valor);

		Produto p2 = new Produto();
		p2.setId(2L);
		p2.setNomeProduto("Mochila");
		BigDecimal valor2 = new BigDecimal("250.00");
		p2.setPrecoUnitario(valor2);

		this.itensProduto.AdicionarItem(p1, 3);
		this.itensProduto.AdicionarItem(p2, 1);
		this.itensProduto.excluirItem(p2);

		assertEquals(1, this.itensProduto.total());
		assertEquals(new BigDecimal("3000.00"), this.itensProduto.getValorTotalProdutos());
	}

}

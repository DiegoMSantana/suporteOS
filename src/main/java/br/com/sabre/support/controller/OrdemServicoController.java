package br.com.sabre.support.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sabre.support.dto.ClienteDTO;
import br.com.sabre.support.model.OrdemServico;
import br.com.sabre.support.model.Produto;
import br.com.sabre.support.model.Servico;
import br.com.sabre.support.model.Status;
import br.com.sabre.support.repository.Clientes;
import br.com.sabre.support.repository.Produtos;
import br.com.sabre.support.security.UsuarioSistema;
import br.com.sabre.support.service.CadastroOrdemServico;
import br.com.sabre.support.session.TabelaItensSession;

@Controller
@RequestMapping("/ordem")
public class OrdemServicoController {

	@Autowired
	private Clientes clientes;

	@Autowired
	private Produtos produtos;

	@Autowired
	private TabelaItensSession tabelaItemSession;

	@Autowired
	private CadastroOrdemServico cadastroOrdemServico;

	/* Método chamado para montar a view de ordem de serviço */

	@GetMapping
	public ModelAndView novo(OrdemServico ordemServico) {

		ModelAndView mv = new ModelAndView("/cadastros/ordem/OrdemServico");

		if (StringUtils.isEmpty(ordemServico.getUuid())) {
			ordemServico.setUuid((UUID.randomUUID().toString()));
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		mv.addObject("status", Status.values());
		mv.addObject("data", LocalDate.now().format(formatter));

		// Variáveis referente a tabela de serviços
		mv.addObject("itensServico", ordemServico.getItensServico());
		mv.addObject("valorTotal", ordemServico.getValorTotalItensServicos());
		mv.addObject("valorTotalItensServico", ordemServico.getValorTotalItensServicos());

		// variáveis referentes a tabela de produtos
		mv.addObject("itensProduto", ordemServico.getItensProduto());
		mv.addObject("valorTotalItensProduto", ordemServico.getValorTotalItensProdutos());

		return mv;
	}

	/* Método utilizado para salvar uma ordem de serviço */

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(OrdemServico ordemServico, BindingResult result, RedirectAttributes attr,
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {

		ordemServico.setUsuario(usuarioSistema.getUsuario());

		validarVenda(ordemServico);
		cadastroOrdemServico.salvarOrdemServico(ordemServico);
		return new ModelAndView("redirect:/ordem");
	}

	/* faz a busca do cliente para o componente do autocomplete */

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ClienteDTO> pesquisar(String nomeCliente) {
		return clientes.porNome(nomeCliente);
	}

	/* Insere um item no array de serviços */

	@PostMapping("/item")
	public ModelAndView adicionarItem(String uuid, Servico servico, Integer quantidade) {

		tabelaItemSession.adicionarItemServico(uuid, servico, quantidade);
		return mvTabelaItensServico(uuid);
	}

	/* Exclui um item do array de serviços */

	@DeleteMapping("/item/{uuid}/{descricaoServico}")
	public ModelAndView excluirItem(@PathVariable("descricaoServico") String descricao, @PathVariable String uuid) {

		tabelaItemSession.removerItemServico(uuid, descricao);
		return mvTabelaItensServico(uuid);
	}

	/* Insere um item no array de produtos */

	@PostMapping("/produto/item")
	public ModelAndView adicionarItem(String uuid, Long codigoProduto) {

		Produto produto = produtos.findOne(codigoProduto);
		tabelaItemSession.adicionarItemProduto(uuid, produto, 1);
		return mvTabelaItensProduto(uuid);
	}

	@PutMapping("/produto/item/{codigoProduto}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("codigoProduto") Produto produto, Integer quantidade,
			String uuid) {

		tabelaItemSession.alterarQuantidadeProduto(uuid, produto, quantidade);
		return mvTabelaItensProduto(uuid);
	}

	// Private Methods

	/* Monta a tabela de serviços na view */

	private ModelAndView mvTabelaItensServico(String uuid) {
		ModelAndView mv = new ModelAndView("cadastros/ordem/TabelaItensServico");

		mv.addObject("itensServico", tabelaItemSession.getItensServico(uuid));
		mv.addObject("valorTotal", tabelaItemSession.getValorTotalServicos(uuid));
		mv.addObject("valorTotalItensServico", tabelaItemSession.getValorTotalServicos(uuid));

		return mv;
	}

	/* Monta a tabela de produtos na view */

	private ModelAndView mvTabelaItensProduto(String uuid) {
		ModelAndView mv = new ModelAndView("cadastros/ordem/TabelaItensProduto");

		mv.addObject("itensProduto", tabelaItemSession.getItensProduto(uuid));
		mv.addObject("valorTotal", tabelaItemSession.getValorTotalProdutos(uuid));
		mv.addObject("valorTotalItensProduto", tabelaItemSession.getValorTotalProdutos(uuid));

		return mv;
	}

	private void validarVenda(OrdemServico ordemServico) {
		ordemServico.adicionarItensProduto(tabelaItemSession.getItensProduto(ordemServico.getUuid()));
		ordemServico.adicionarItensServico(tabelaItemSession.getItensServico(ordemServico.getUuid()));
		ordemServico.calcularValorTotal();
	}
}

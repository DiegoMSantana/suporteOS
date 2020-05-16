package br.com.sabre.support.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sabre.support.dto.ProdutoDTO;
import br.com.sabre.support.model.Produto;
import br.com.sabre.support.repository.Produtos;
import br.com.sabre.support.service.CadastroProdutoService;

@Controller
@RequestMapping("/cadastros/produto")
public class ProdutosController {

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private Produtos produtos;

	@GetMapping
	public ModelAndView novo(Produto produto) {
		ModelAndView mv = new ModelAndView("/cadastros/produtos/CadastroProduto");
		return mv;
	}

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Produto produto, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return novo(produto);
		}

		cadastroProdutoService.salvar(produto);
		attr.addFlashAttribute("mensagem", "Produto adicionado com sucesso!");

		return new ModelAndView("redirect:/cadastros/produto");
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ProdutoDTO> pesquisar(String nomeProduto) {
		return produtos.porNomeProduto(nomeProduto);
	}
}

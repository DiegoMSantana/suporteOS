package br.com.sabre.support.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sabre.support.controller.page.PageWrapper;
import br.com.sabre.support.model.Cliente;
import br.com.sabre.support.model.TipoPessoa;
import br.com.sabre.support.repository.Clientes;
import br.com.sabre.support.service.CadastroClienteService;
import br.com.sabre.support.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/cadastros/cliente")
public class ClientesController {

	@Autowired
	private CadastroClienteService cadastroClienteService;

	@Autowired
	private Clientes clientes;

	/**
	 * @param cliente
	 *            This parameter is to bind with view.
	 * @return A view which will be shown on the web browser.
	 */

	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("/cadastros/clientes/CadastroCliente");
		mv.addObject("contatos", cliente.getContato());
		mv.addObject("tiposPessoa", TipoPessoa.values());

		return mv;
	}

	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/cadastros/clientes/ListarClientes");
		// mv.addObject("clientes", clientes.findAll());

		return mv;
	}

	/**
	 * *
	 * 
	 * @param draw
	 * @param start
	 *            - Datatable from bootstrap starts counting from 0 and increments
	 *            according with length parameter.
	 * @param length
	 *            - Determine how much data will be shown in datatable.
	 * @param search
	 *            - This parameter must be activated on datatable parameter to work
	 *            on.
	 * @return json - List of clients to bootstrap datatable
	 */

	@RequestMapping(value = "/pesquisar")
	@ResponseBody
	public ResponseEntity<PageWrapper<Cliente>> pesquisar(@RequestParam(value = "draw") int draw,
			@RequestParam(value = "start") int start, @RequestParam(value = "length") int length,
			@RequestParam(value = "search[value]", required = false) String search) {

		String busca = search;
		int inicio = start / length;

		Pageable pageable = new PageRequest(inicio, length);
		PageWrapper<Cliente> list = new PageWrapper<>(clientes.filtrar(pageable, busca), draw);

		return new ResponseEntity<PageWrapper<Cliente>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return novo(cliente);
		}

		if (cliente.getInsc_estadual() == null) {
			cliente.setInsc_estadual("Isento");
		}

		cadastroClienteService.Salvar(cliente);
		attr.addFlashAttribute("mensagem", "Cliente adicionado com sucesso!");

		return new ModelAndView("redirect:/cliente/novo");
	}

	@GetMapping("/buscar/{identificador}")
	public @ResponseBody ResponseEntity<?> buscar(@PathVariable("identificador") Long id) {

		Cliente cliente = clientes.findOne(id);
		return ResponseEntity.ok(cliente);
	}

	@RequestMapping(value = "/atualizar/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> atualizar(@RequestBody Cliente cliente, @PathVariable("id") Long id) {

		try {
			cadastroClienteService.atualizar(cliente, id);
			return ResponseEntity.ok("Cliente alterado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cliente cliente) {

		try {
			cadastroClienteService.excluir(cliente);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok().build();

	}

}

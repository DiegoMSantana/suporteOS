package br.com.sabre.support.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.sabre.support.model.Agenda;
import br.com.sabre.support.repository.Agendamentos;
import br.com.sabre.support.service.CadastroAgendaService;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

	@Autowired
	private Agendamentos agendamentoRepository;

	@Autowired
	private CadastroAgendaService agendaService;

	@RequestMapping
	public ModelAndView agenda() {
		ModelAndView mv = new ModelAndView("/agenda/Agendamento");

		return mv;
	}

	@RequestMapping(value = "/buscar")
	public @ResponseBody List<Agenda> buscarTodas() {

		// ObjectMapper mapper = new ObjectMapper();
		// mapper.registerModule(new JavaTimeModule());
		// mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		return agendamentoRepository.findAll();
	}

	@PostMapping("/salvar")
	public @ResponseBody ResponseEntity<?> novoAgendamento(@RequestBody Agenda agenda) {

		try {
			agendaService.salvar(agenda);
			return ResponseEntity.ok("Agendamento realizado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/atualizar/{id}")
	public @ResponseBody ResponseEntity<?> atualizarEvento(@RequestBody Agenda agenda, @PathVariable("id") Long id) {

		try {
			agendaService.atualizar(agenda, id);
			return ResponseEntity.ok("Evento alterado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("/deletar/{id}")
	public @ResponseBody ResponseEntity<?> excluirItem(@PathVariable("id") Agenda agenda) {

		try {
			agendaService.deletarEvento(agenda);
			return ResponseEntity.ok("Evento excluido");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Não foi possível deletar o evento" + e.getMessage());
		}

	}

}

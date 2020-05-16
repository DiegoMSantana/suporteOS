package br.com.sabre.support.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrosController {

	@GetMapping("/404")
	public String paginaNaoEncontrada(HttpServletRequest requisicao) {

		HttpServletRequest request = requisicao;
		System.out.println(request.getRequestURI());

		return "404";
	}

}

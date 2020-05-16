package br.com.sabre.support.controller;

import java.time.LocalDateTime;

import br.com.sabre.support.model.Agenda;

public class TesteAgenda {

	public static void main(String[] args) {

		LocalDateTime date = LocalDateTime.now();

		Agenda agenda = new Agenda();
		agenda.setStart(date);

		System.out.println(agenda.getStart());

	}

}

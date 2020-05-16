package br.com.sabre.support.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_eventos")
public class Agenda extends GenericDomain {

	private static final long serialVersionUID = 1L;

	private String title;
	private String color;

	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime start;

	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime end;

	private String observacoes;

	@Transient
	private String dataHoraInicio;

	@Transient
	private String dataHoraFim;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStart() {
		return start.toString();
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public String getEnd() {
		return end.toString();
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(String dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public String getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(String dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

}

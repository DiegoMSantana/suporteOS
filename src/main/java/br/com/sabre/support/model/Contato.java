package br.com.sabre.support.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_contato")
public class Contato extends GenericDomain {

	private static final long serialVersionUID = 1L;

	private String nomeContato;
	private String email;
	private String telefone;
	private String cargo;

	@ManyToOne
	@JoinColumn(name = "contato_codigo")
	private Cliente cliente;

	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

}

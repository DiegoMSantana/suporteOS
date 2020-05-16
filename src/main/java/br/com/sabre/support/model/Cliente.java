package br.com.sabre.support.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import br.com.sabre.support.model.validation.ClienteGroupSequenceProvider;
import br.com.sabre.support.model.validation.grupo.CnpjGroups;
import br.com.sabre.support.model.validation.grupo.CpfGroups;

@Entity
@Table(name = "tbl_cliente")
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente extends GenericDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa")
	private TipoPessoa tipoPessoa;

	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	@NotBlank(message = "Nome é obrigatório")
	@Column(name = "nome_fantasia")
	private String nome;

	@CPF(groups = CpfGroups.class)
	@CNPJ(groups = CnpjGroups.class)
	@Column(name = "cpf_cnpj")
	@NotBlank(message = "CPF/CNPJ é obrigatório")
	private String cpfoucnpj;

	@Column(name = "rg_razao_social")
	private String razaoSocial;

	@Column(name = "ins_estadual")
	private String insc_estadual;

	@Email(message = "Coloque um email válido")
	private String email;

	@NotBlank(message = "Digite um telefone")
	private String telefone;

	private String celular;

	private String observacoes;

	@OneToMany(mappedBy = "cliente")
	@Transient
	private List<Contato> contato = new ArrayList<Contato>();

	@Embedded
	private Endereco endereco;

	// GETTERS AND SETTERS
	// *************************************************************************

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfoucnpj() {
		return cpfoucnpj;
	}

	public void setCpfoucnpj(String cpfoucnpj) {
		this.cpfoucnpj = cpfoucnpj;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	/*
	 * public LocalDate getDataCriacao() { return dataCriacao; }
	 * 
	 * public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao =
	 * dataCriacao; }
	 *
	 */

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getInsc_estadual() {
		return insc_estadual;
	}

	public void setInsc_estadual(String insc_estadual) {
		this.insc_estadual = insc_estadual;
	}

	public List<Contato> getContato() {
		return contato;
	}

	public void setContato(List<Contato> contato) {
		this.contato = contato;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

}

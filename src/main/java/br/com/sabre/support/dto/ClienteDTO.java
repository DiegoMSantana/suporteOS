package br.com.sabre.support.dto;

public class ClienteDTO {

	private Long id;
	private String nome;
	private String cpfoucnpj;

	public ClienteDTO(Long id, String nome, String cpfoucnpj) {
		this.id = id;
		this.nome = nome;
		this.cpfoucnpj = cpfoucnpj;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfoucnpj() {
		return cpfoucnpj;
	}

	public void setCpfoucnpj(String cpfoucnpj) {
		this.cpfoucnpj = cpfoucnpj;
	}

	public String getDoisCampos() {
		return this.nome + " - " + this.cpfoucnpj;
	}

}

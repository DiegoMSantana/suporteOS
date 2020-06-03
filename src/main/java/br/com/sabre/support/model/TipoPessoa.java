package br.com.sabre.support.model;

import br.com.sabre.support.model.validation.grupo.CnpjGroups;
import br.com.sabre.support.model.validation.grupo.CpfGroups;

public enum TipoPessoa {

	FISICA("Física", "CPF", "999.999.999-99", "RG*", CpfGroups.class) {

		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
		}

	},

	JURIDICA("Jurídica", "CNPJ", "99.999.999/9999-99", "Razão Social*", CnpjGroups.class) {
		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
		}
	};

	private String descricao;
	private String documento;
	private String mascara;
	private String razao;
	private Class<?> grupo;

	private TipoPessoa(String descricao, String documento, String mascara, String razao, Class<?> grupo) {
		this.descricao = descricao;
		this.documento = documento;
		this.mascara = mascara;
		this.razao = razao;
		this.grupo = grupo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public Class<?> getGrupo() {
		return grupo;
	}

	public void setGrupo(Class<?> grupo) {
		this.grupo = grupo;
	}

	// Métodos úteis

	public static String removerFormatacao(String cpfOuCnpj) {
		return cpfOuCnpj.replaceAll("\\.|-|/", "");
	}

	public static String inserirFormatacaoCpf(String cpf) {
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
	}

	public static String inserirFormatacaoCnpj(String cnpj) {
		return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
	}

	public abstract String formatar(String cpfOuCnpj);

}

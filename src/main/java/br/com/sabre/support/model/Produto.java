package br.com.sabre.support.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tbl_produto")
public class Produto extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "O nome do produto é obrigatório")
	@Column(name = "nome_produto")
	private String nomeProduto;

	@NotBlank(message = "O código interno é obrigatório")
	@Column(name = "codigo_interno")
	private String codigoInterno;

	@Column(name = "codigo_barras")
	private String codigoBarra;

	@NotBlank(message = "Escolha um grupo para o produto")
	@Column(name = "grupo_produto")
	private String grupoProduto;

	@NotNull
	@DecimalMin(value = "0.10", message = "O valor do produto deve ser maior que R$0,10")
	@Column(name = "preco_unitario")
	private BigDecimal precoUnitario = BigDecimal.ZERO;

	private String detalhes;

	private String foto;

	@Column(name = "content_type")
	private String contentType;

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getCodigoInterno() {
		return codigoInterno;
	}

	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public String getGrupoProduto() {
		return grupoProduto;
	}

	public void setGrupoProduto(String grupoProduto) {
		this.grupoProduto = grupoProduto;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}

package br.com.sabre.support.dto;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

public class ProdutoDTO {

	private Long id;
	private String nomeProduto;
	private String codigoInterno;
	private BigDecimal precoUnitario;
	private String detalhes;
	private String foto;

	public ProdutoDTO(Long id, String nomeProduto, String codigoInterno, BigDecimal precoUnitario, String detalhes,
			String foto) {

		this.id = id;
		this.nomeProduto = nomeProduto;
		this.codigoInterno = codigoInterno;
		this.precoUnitario = precoUnitario;
		this.detalhes = detalhes;
		this.foto = StringUtils.isEmpty(foto) ? "produto_mock.png" : foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}

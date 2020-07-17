package br.com.telegram.mensagem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Ad")
public class Mensagem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;

	@Column(name = "INPUT", nullable = false)
	private String texto;
	
	@Column(name = "IMAGE", nullable = false)
	private String foto;
	
	@Column(name = "POSTEDTELEGRAM", nullable = false)
	private Long publicadoTelegram;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Long getPublicadoTelegram() {
		return publicadoTelegram;
	}

	public void setPublicadoTelegram(Long publicadoTelegram) {
		this.publicadoTelegram = publicadoTelegram;
	}


}

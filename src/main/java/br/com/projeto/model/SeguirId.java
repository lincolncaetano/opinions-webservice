package br.com.projeto.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class SeguirId implements Serializable{
	
	private static final long serialVersionUID = -1241623574111963240L;
	
	private Long idUsuario;
	private Long idUsuarioSeguido;
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdUsuarioSeguido() {
		return idUsuarioSeguido;
	}
	public void setIdUsuarioSeguido(Long idUsuarioSeguido) {
		this.idUsuarioSeguido = idUsuarioSeguido;
	}
}

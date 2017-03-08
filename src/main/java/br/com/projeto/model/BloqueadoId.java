package br.com.projeto.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class BloqueadoId implements Serializable{

	private static final long serialVersionUID = 8321736652216937170L;

	private Long idUsuario;
	private Long idUsuarioBloqueado;
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Long getIdUsuarioBloqueado() {
		return idUsuarioBloqueado;
	}
	
	public void setIdUsuarioBloqueado(Long idUsuarioBloqueado) {
		this.idUsuarioBloqueado = idUsuarioBloqueado;
	}
	
	
}

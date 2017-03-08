package br.com.projeto.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Seguir  implements Serializable{


	private static final long serialVersionUID = 3853219596621323309L;

	@EmbeddedId 
	private SeguirId id;
	
	@MapsId("idUsuario")
    @JoinColumn(name="idUsuario", referencedColumnName="id", insertable = false, updatable = false)
	@OneToOne 
	private Usuario usuario;
	
	@MapsId("idUsuarioSeguido")
    @JoinColumn(name="idUsuarioSeguido", referencedColumnName="id", insertable = false, updatable = false)
	@OneToOne 
	private Usuario usuarioSeguido;

	public SeguirId getId() {
		return id;
	}

	public void setId(SeguirId id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioSeguido() {
		return usuarioSeguido;
	}

	public void setUsuarioSeguido(Usuario usuarioSeguido) {
		this.usuarioSeguido = usuarioSeguido;
	}

	

}
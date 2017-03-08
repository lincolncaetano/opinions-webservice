package br.com.projeto.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Bloqueado  implements Serializable {

	private static final long serialVersionUID = -6907521618753106605L;
	

	@EmbeddedId 
	private BloqueadoId id;

	@MapsId("idUsuario")
    @JoinColumn(name="idUsuario", referencedColumnName="id", insertable = false, updatable = false)
	@OneToOne 
	private Usuario usuario;
	
	@MapsId("idUsuarioSeguido")
    @JoinColumn(name="idUsuarioBloqueado", referencedColumnName="id", insertable = false, updatable = false)
	@OneToOne 
	private Usuario usuarioBloqueado;
	
	public BloqueadoId getId() {
		return id;
	}

	public void setId(BloqueadoId id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioBloqueado() {
		return usuarioBloqueado;
	}

	public void setUsuarioBloqueado(Usuario usuarioBloqueado) {
		this.usuarioBloqueado = usuarioBloqueado;
	}

}


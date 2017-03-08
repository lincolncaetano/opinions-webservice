package br.com.projeto.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Notificacao implements Serializable{
	
	private static final long serialVersionUID = 7658807023962971313L;
	
	@Id
	@GeneratedValue
	private Long id;
	    
    @ManyToOne
    @JoinColumn(name="idUsuario", referencedColumnName="id")
	private Usuario usuario;

    @ManyToOne 
    @JoinColumn(name="idUsuarioNotificado", referencedColumnName="id")
	private Usuario usuarioNotificado;
    
    private String mensagem;
    
    @Temporal(TemporalType.DATE)
	private Date data;
    
    private Long tipo;
    
    private String status;
    
    @ManyToOne 
    @JoinColumn(name="idPostgem", referencedColumnName="id", insertable = false, updatable = false)
	private Postagem postagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioNotificado() {
		return usuarioNotificado;
	}

	public void setUsuarioNotificado(Usuario usuarioNotificado) {
		this.usuarioNotificado = usuarioNotificado;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notificacao other = (Notificacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    

}

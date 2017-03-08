package br.com.projeto.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comentario implements Serializable{
	
	private static final long serialVersionUID = 6559622938410108579L;
	
	public Comentario() {
		super();
	}
	
	public Comentario(Long id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)  
    @JoinColumn(name = "idUsuario")  
	private Usuario usuario;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)  
    @JoinColumn(name = "idPostagem")  
	private Postagem postagem;
	
	private String texto;

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

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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
		Comentario other = (Comentario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

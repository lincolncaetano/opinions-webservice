package br.com.projeto.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Curtida  implements Serializable{


	private static final long serialVersionUID = 3853219596621323309L;

	@EmbeddedId 
	private CurtidaId id;
	
	private int opcao;
	
	@MapsId("usuarioId")
    @JoinColumn(name="idUsuario", referencedColumnName="id")
	@OneToOne 
	private Usuario usuario;
	
	@MapsId("postagemId")
    @JoinColumn(name="idPostagen", referencedColumnName="id")
	@OneToOne 
	private Postagem postagem;

	public CurtidaId getId() {
		return id;
	}

	public void setId(CurtidaId id) {
		this.id = id;
	}

	public int getOpcao() {
		return opcao;
	}

	public void setOpcao(int opcao) {
		this.opcao = opcao;
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
	
	

}


@Embeddable
class CurtidaId implements Serializable{
    
	private static final long serialVersionUID = 5731765168046358234L;
	
	Long postagemId;
    Long usuarioId;
	
    public Long getPostagemId() {
		return postagemId;
	}
	public void setPostagemId(Long postagemId) {
		this.postagemId = postagemId;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
    
}
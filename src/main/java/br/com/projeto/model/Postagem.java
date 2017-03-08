package br.com.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Postagem  implements Serializable{

	private static final long serialVersionUID = 989955470790682153L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)  
    @JoinColumn(name = "idUsuario")  
	private Usuario usuario;
	
	@Column( name = "opcao1" )
	private String opcao1;
	
	@Column( name = "opcao2" )
	private String opcao2;
	
	@Column( name = "postagem" )
	private String pergunta;
	
	@Transient
	private String username;
	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="idPostagem")
	@Fetch(FetchMode.SUBSELECT)
	private List<Comentario> listaComentarios;
	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="idPostagen")
	@Fetch(FetchMode.SUBSELECT)
	private List<Curtida> listaCurtidas;
	
	@Transient
	private List<Curtida> listaCurtidasOp1;
	
	@Transient
	private List<Curtida> listaCurtidasOp2;
	

	public void getListaSeparaCurtidas() {
		
		if(listaCurtidasOp1 ==null){
			listaCurtidasOp1 = new ArrayList<Curtida>();
		}
		if(listaCurtidasOp2 ==null){
			listaCurtidasOp2 = new ArrayList<Curtida>();
		}
		
		for (Curtida curtida : listaCurtidas) {
			if(curtida.getOpcao() == 1){
				listaCurtidasOp1.add(curtida);
			}else{
				listaCurtidasOp2.add(curtida);
			}
		}
	}


	public String getUsername(){
		return usuario.getUsername();
	}
	
	
	protected Postagem(){
	}

	public Postagem(String opcao1, String opcao2, String pergunta) {
		super();
		this.opcao1 = opcao1;
		this.opcao2 = opcao2;
		this.pergunta = pergunta;
	}



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

	public String getOpcao1() {
		return opcao1;
	}

	public void setOpcao1(String opcao1) {
		this.opcao1 = opcao1;
	}

	public String getOpcao2() {
		return opcao2;
	}

	public void setOpcao2(String opcao2) {
		this.opcao2 = opcao2;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	

	public List<Comentario> getListaComentarios() {
		return listaComentarios;
	}


	public void setListaComentarios(List<Comentario> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}

	public List<Curtida> getListaCurtidas() {
		return listaCurtidas;
	}

	public void setListaCurtidas(List<Curtida> listaCurtidas) {
		this.listaCurtidas = listaCurtidas;
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
		Postagem other = (Postagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

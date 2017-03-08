package br.com.projeto.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = -1828214092128036519L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String nome;
	
	private String sexo;
	
	@Temporal(TemporalType.DATE)
	private Date nascimento;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String descricao;
	
	private String foto;
	
	private String background;
	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="idUsuario", insertable = false, updatable = false)
	@Fetch(FetchMode.SUBSELECT)
	private List<Postagem> listaPostagens;
	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="idUsuario", insertable = false, updatable = false)
	@Fetch(FetchMode.SUBSELECT)
	private List<Seguir> listaUsuariosSeguidos;
	

	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="idUsuarioSeguido", insertable = false, updatable = false)
	@Fetch(FetchMode.SUBSELECT)
	private List<Seguir> listaUsuarioSeguem;
	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="idUsuario", insertable = false, updatable = false)
	@Fetch(FetchMode.SUBSELECT)
	private List<Bloqueado> listaUsuarioBloqueado;
	
	@Transient
	private String fotoBase64;
	
	@Transient
	private String backgroundBase64;
	
	@Transient
	private String novaSenha;
	
	public Usuario(){
	}
	
	public Usuario(Long id, String username, String email, String password,
			String descricao) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.descricao = descricao;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public String getBackgroundBase64() {
		return backgroundBase64;
	}

	public void setBackgroundBase64(String backgroundBase64) {
		this.backgroundBase64 = backgroundBase64;
	}


	public List<Seguir> getListaUsuariosSeguidos() {
		return listaUsuariosSeguidos;
	}

	public void setListaUsuariosSeguidos(List<Seguir> listaUsuariosSeguidos) {
		this.listaUsuariosSeguidos = listaUsuariosSeguidos;
	}

	public List<Seguir> getListaUsuarioSeguem() {
		return listaUsuarioSeguem;
	}

	public void setListaUsuarioSeguem(List<Seguir> listaUsuarioSeguem) {
		this.listaUsuarioSeguem = listaUsuarioSeguem;
	}

	public List<Postagem> getListaPostagens() {
		return listaPostagens;
	}

	public void setListaPostagens(List<Postagem> listaPostagens) {
		this.listaPostagens = listaPostagens;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public List<Bloqueado> getListaUsuarioBloqueado() {
		return listaUsuarioBloqueado;
	}

	public void setListaUsuarioBloqueado(List<Bloqueado> listaUsuarioBloqueado) {
		this.listaUsuarioBloqueado = listaUsuarioBloqueado;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}

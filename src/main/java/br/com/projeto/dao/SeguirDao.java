package br.com.projeto.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.model.Seguir;

@RequestScoped
public class SeguirDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Seguir> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Seguir.class);
    }
	
	public void adiciona(Seguir seguidor) {
		 session.saveOrUpdate(seguidor);
	}
	
	public void deleta(Seguir seguidor) {
		 session.delete(seguidor);
	}
	
	@SuppressWarnings("unchecked")
	public List<Seguir> listaSeguindo(Long idSeguidor) {
		List<Seguir> lista = createCriteria().add(Restrictions.eq("id.idUsuario", idSeguidor)).list();
		return lista;
    }
	
	@SuppressWarnings("unchecked")
	public List<Seguir> listaSeguidores(Long idUsuario) {
		List<Seguir> lista = createCriteria().add(Restrictions.eq("id.idUsuarioSeguido", idUsuario)).list();
		return lista;
    }
	
	public Seguir segueUsuario(Seguir seguir){
		
		Seguir resultado = (Seguir)createCriteria().add(Restrictions.eq("id.idUsuario", seguir.getUsuario().getId()))
						.add(Restrictions.eq("id.idUsuarioSeguido", seguir.getUsuarioSeguido().getId())).uniqueResult();
		
		return resultado;
		
		
	}

}

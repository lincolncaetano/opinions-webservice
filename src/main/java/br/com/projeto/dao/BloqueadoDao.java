package br.com.projeto.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.model.Bloqueado;
import br.com.projeto.model.Seguir;

@RequestScoped
public class BloqueadoDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Seguir> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Bloqueado.class);
    }
	
	public void adiciona(Bloqueado bloqueado) {
		 session.saveOrUpdate(bloqueado);
	}
	
	public void deleta(Bloqueado bloqueado) {
		 session.delete(bloqueado);
	}
	
	@SuppressWarnings("unchecked")
	public List<Bloqueado> listaTeBloquearam(Long idBloqueado) {
		List<Bloqueado> lista = createCriteria().add(Restrictions.eq("id.idBloqueado", idBloqueado)).list();
		return lista;
    }
	
	@SuppressWarnings("unchecked")
	public List<Bloqueado> listaBloqueados(Long idUsuario) {
		List<Bloqueado> lista = createCriteria().add(Restrictions.eq("id.idUsuario", idUsuario)).list();
		return lista;
    }
	
	public Bloqueado segueUsuario(Bloqueado bloqueado){
		
		Bloqueado resultado = (Bloqueado)createCriteria().add(Restrictions.eq("id.idUsuario", bloqueado.getUsuario().getId()))
						.add(Restrictions.eq("id.idUsuarioBloqueado", bloqueado.getUsuarioBloqueado().getId())).uniqueResult();
		
		return resultado;
		
		
	}

}

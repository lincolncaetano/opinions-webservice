package br.com.projeto.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.model.Curtida;

@RequestScoped
public class CurtidaDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Curtida> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Curtida.class);
    }
	
	public void adiciona(Curtida curtida) {
		 session.saveOrUpdate(curtida);
	}
	
	public void deleta(Curtida curtida) {
		 session.delete(curtida);
	}
	
	@SuppressWarnings("unchecked")
	public List<Curtida> listaCurtidasPorPostagen(Long idPostagem) {
		List<Curtida> lista = createCriteria().add(Restrictions.eq("id.postagemId", idPostagem)).list();
		return lista;
    }

}

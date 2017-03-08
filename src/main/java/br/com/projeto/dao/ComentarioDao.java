package br.com.projeto.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.model.Comentario;

@RequestScoped
public class ComentarioDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Comentario> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Comentario.class).addOrder(Order.desc("id"));
    }
	
	 public void adiciona(Comentario comentario) {
		 session.saveOrUpdate(comentario);
	 }
	 
	 public void excluir(Comentario comentario) {
		 session.delete(comentario);
	 }
	 
	 
	 @SuppressWarnings("unchecked")
	public List<Comentario> pesquisaComentarioPorpostagem(Long idPostagem) {
		return (List<Comentario>) createCriteria().add(Restrictions.like("postagem.id", idPostagem)).list();		
	 }
}

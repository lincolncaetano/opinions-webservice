package br.com.projeto.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.model.Postagem;

@RequestScoped
public class PostagemDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Postagem> listaTodos() {
		List<Postagem> lista = createCriteria().list();
		for (Postagem postagem : lista) {
			postagem.getListaSeparaCurtidas();
		}
		return lista;
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Postagem.class).addOrder(Order.desc("id"));
    }
	
	 public void adiciona(Postagem postagem) {
		 session.saveOrUpdate(postagem);
	 }
	 
	 @SuppressWarnings("unchecked")
	public List<Postagem> listaPostagemUsuario(Long idUsuario) {
		List<Postagem> lista = createCriteria().add(Restrictions.eq("usuario.id", idUsuario)).list();
		for (Postagem postagem : lista) {
			postagem.getListaSeparaCurtidas();
		}
		return lista;
	 }

}

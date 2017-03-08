package br.com.projeto.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.model.Notificacao;
import br.com.projeto.utils.Status;

@RequestScoped
public class NotificacaoDao {
	
	@Inject
    private Session session;
	
	@SuppressWarnings("unchecked")
	public List<Notificacao> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Notificacao.class);
    }
	
	public void adiciona(Notificacao notificacao) {
		session.saveOrUpdate(notificacao);
	}
	
	@SuppressWarnings("unchecked")
	public List<Notificacao> listaNotificacao(Long idUsuario) {
		return createCriteria().add(Restrictions.eq("usuarioNotificado.id", idUsuario)).list();	
	}
	

	@SuppressWarnings("unchecked")
	public List<Notificacao> listaNotificaoPendentesUsuario(Long idUsuario) {
		return createCriteria()
				.add(Restrictions.eq("usuarioNotificado.id", idUsuario))
				.add(Restrictions.eq("status", Status.PENDENTE.getCodigo())).list();	
	}
}

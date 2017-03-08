package br.com.projeto.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.model.Usuario;

@RequestScoped
public class UsuarioDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Usuario> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Usuario.class);
    }
	
	public void adiciona(Usuario usuario) {
		if(usuario.getId() != null){
			session.merge(usuario);
		}else{
			session.saveOrUpdate(usuario);
		}
	 }
	
	public Usuario pesquisaUsuarioPorEmail(Usuario usuario) {
		return (Usuario) createCriteria().add(Restrictions.like("email", usuario.getEmail())).uniqueResult();
		//return (Usuario) session.createSQLQuery("select * from Usuario where email like '"+usuario.getEmail()+"'").addEntity(Usuario.class).uniqueResult();
		
	 }
	
	public Usuario pesquisaUsuarioPorId(Usuario usuario) {
		return (Usuario) createCriteria().add(Restrictions.eq("id", usuario.getId())).uniqueResult();
		//return (Usuario) session.createSQLQuery("select * from Usuario where email like '"+usuario.getEmail()+"'").addEntity(Usuario.class).uniqueResult();
		
	 }
	
	@SuppressWarnings("unchecked")
	public List<Usuario> pesquisaUsuarioPorUserName(String userName) {
		return createCriteria().add(Restrictions.ilike("username", "%"+userName+"%")).list();
		
	 }
	
	public Usuario pesquisaUsuarioPorUserName(Usuario usuario) {
		return (Usuario) createCriteria().add(Restrictions.ilike("username", usuario.getUsername())).uniqueResult();
		
	 }
 
   
}

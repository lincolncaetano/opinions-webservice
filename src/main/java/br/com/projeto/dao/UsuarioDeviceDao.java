package br.com.projeto.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projeto.model.UsuarioDevice;

@RequestScoped
public class UsuarioDeviceDao {
	
	@Inject
    private Session session;
	
	@SuppressWarnings("unchecked")
	public List<UsuarioDevice> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(UsuarioDevice.class);
    }
	
	public void adiciona(UsuarioDevice usuarioDevice) {
		session.saveOrUpdate(usuarioDevice);	
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioDevice> listaUsuarioDevice(Long idUsuario) {
		return createCriteria().add(Restrictions.eq("usuario.id", idUsuario)).list();	
	}

}

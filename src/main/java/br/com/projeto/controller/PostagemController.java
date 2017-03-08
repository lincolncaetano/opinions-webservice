package br.com.projeto.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.projeto.dao.ComentarioDao;
import br.com.projeto.dao.CurtidaDao;
import br.com.projeto.dao.NotificacaoDao;
import br.com.projeto.dao.UsuarioDeviceDao;
import br.com.projeto.model.Comentario;
import br.com.projeto.model.Curtida;
import br.com.projeto.model.Notificacao;
import br.com.projeto.model.UsuarioDevice;
import br.com.projeto.utils.Status;
import br.com.projeto.utils.TipoNotificacao;

@Controller
public class PostagemController {

	@Inject 
	private Result result;
	
	@Inject
	private ComentarioDao comentarioDao;
	
	@Inject
	private CurtidaDao curtidaDao;
	
	@Inject
	private UsuarioDeviceDao usuarioDeviceDao;
	
	@Inject
	private NotificacaoDao notificacaoDao;
	
	@Post("/cadastraComentario")
    @Consumes(value="application/json")
	public void cadastraComentario(Comentario comentario) {
    		
		comentarioDao.adiciona(comentario);
		result.use(Results.json()).withoutRoot().from(true).serialize();
		
    }
	
	@Get
    @Path("/comentario/{postagemId}")
	public void pesquisaComentarioPorpostagem(Long postagemId) {
		List<Comentario> lista = comentarioDao.pesquisaComentarioPorpostagem(postagemId);
        result.use(Results.json()).withoutRoot().from(lista).include("usuario").exclude("usuario.password").serialize();
    }
	
	@Delete
	@Path("/comentario/delete/{postagemId}")
	public void deleteComentario(Long postagemId) {
		comentarioDao.excluir(new Comentario(postagemId));
		result.use(Results.json()).withoutRoot().from(true).serialize();
    }
	
	@Post("/cadastraCurtida")
    @Consumes(value="application/json")
	public void cadastraCurtida(Curtida curtida) {
		
		curtidaDao.adiciona(curtida);
		gravaNotificacaoVoto(curtida);
		result.use(Results.json()).withoutRoot().from(curtida).include("id").serialize();

    }
	
   private void gravaNotificacaoVoto(Curtida curtida){
    	try {
    		Notificacao notif = new Notificacao();
        	notif.setData(new Date());
        	notif.setMensagem("Voto em uma publicação sua");
        	notif.setStatus(Status.PENDENTE.getCodigo());
        	notif.setTipo(TipoNotificacao.VOTO.getCodigo());
        	notif.setUsuario(curtida.getUsuario());
        	notif.setUsuarioNotificado(curtida.getPostagem().getUsuario());
        	notificacaoDao.adiciona(notif);
        	
    		List<UsuarioDevice> listaUsuarioDevice = usuarioDeviceDao.listaUsuarioDevice(curtida.getPostagem().getUsuario().getId());
    		for (UsuarioDevice usuarioDevice : listaUsuarioDevice) {
    			if(usuarioDevice.getTipoDevice().equals("I")){
    				EnviaNotificacaoIOS enviaNotIOS = new EnviaNotificacaoIOS(notif.getMensagem(), usuarioDevice.getToken());
    				Thread threadNot = new Thread(enviaNotIOS);
    				threadNot.start();	
    			}else{
    				EnviaNotificacaoAndroid enviaNot = new EnviaNotificacaoAndroid(notif.getMensagem(), usuarioDevice.getToken());
    				Thread threadNot = new Thread(enviaNot);
    				threadNot.start();
    			}
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Delete
	@Path("/deleteCurtida/{curtida}")
	public void deleteCurtida(Curtida curtida) {
		
		curtidaDao.deleta(curtida);
		result.use(Results.json()).withoutRoot().from(true).serialize();

    }
	
	@Get
    @Path("/curtida/{postagemId}")
	public void listaCurtidasPorPostagen(Long postagemId) {
		List<Comentario> lista = comentarioDao.pesquisaComentarioPorpostagem(postagemId);
        result.use(Results.json()).withoutRoot().from(lista).include("usuario").exclude("usuario.password").include("postagem").serialize();
    }


}

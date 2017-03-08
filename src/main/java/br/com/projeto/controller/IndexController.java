package br.com.projeto.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.simplemail.Mailer;
import br.com.caelum.vraptor.view.Results;
import br.com.projeto.dao.BloqueadoDao;
import br.com.projeto.dao.NotificacaoDao;
import br.com.projeto.dao.PostagemDao;
import br.com.projeto.dao.SeguirDao;
import br.com.projeto.dao.UsuarioDao;
import br.com.projeto.dao.UsuarioDeviceDao;
import br.com.projeto.model.Bloqueado;
import br.com.projeto.model.Notificacao;
import br.com.projeto.model.Postagem;
import br.com.projeto.model.Seguir;
import br.com.projeto.model.Usuario;
import br.com.projeto.model.UsuarioDevice;
import br.com.projeto.utils.Status;
import br.com.projeto.utils.TipoNotificacao;

@Controller
public class IndexController {

	@Inject 
	private Result result;
	
	@Inject
	private Mailer mailer;

	@Inject
	private ServletContext context;
	
	@Path("/")
	public void index() {
		result.use(Results.json()).withoutRoot().from("servidorOK").serialize();
	}
	
	@Inject
    private PostagemDao postagemDao;
	
	@Inject
    private UsuarioDao usuarioDao;
	
	@Inject
    private SeguirDao seguidorDao;
	
	@Inject
    private BloqueadoDao bloqueadoDao;
	
	@Inject
	private UsuarioDeviceDao usuarioDeviceDao;
	
	@Inject
	private NotificacaoDao notificacaoDao;
	

	@Post("/login")
    @Consumes(value="application/json")
	public void login(Usuario usr) {
		
		//Usuario user = new Usuario();
		//user.setUsername("lincoln");

		Usuario usuario = usuarioDao.pesquisaUsuarioPorUserName(usr);
		if(usuario != null){
			result.use(Results.json()).withoutRoot().from(usuario).serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
    }
	
	@Post
	public void enviarEmail() {
		try {
		
			Email email = new SimpleEmail();
			email.setSubject("teste Email");
			email.setFrom("");
			email.addTo("contato@risidevelop.com.br");
			email.setMsg("teste");
			
			mailer.send(email);
		
		
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		result.redirectTo(this).index();
    }

    @Post("/postagemNova")
    @Consumes(value="application/json")
	public void postagemNova(Postagem postagem) {
    	
    	try {
    		
    		
    		File dir = new File(context.getRealPath("/imagens")+"/"+postagem.getUsuario().getUsername());  
    		if(!dir.exists()){
    			dir.mkdirs();
    		}
    		
    		
	    	if(dir.exists()){
	    		
	    		String nomeArquivoBase = ""+System.currentTimeMillis();
	    		
	    		gravaImage(context.getRealPath("/imagens/"+postagem.getUsuario().getUsername()), postagem.getOpcao1(),nomeArquivoBase+"1");
	    		gravaImage(context.getRealPath("/imagens/"+postagem.getUsuario().getUsername()), postagem.getOpcao2(),nomeArquivoBase+"2");


	        	postagem.setOpcao1("http://192.168.0.100:8080/projeto/imagens/"+postagem.getUsuario().getUsername()+"/"+nomeArquivoBase+"1.png");
	        	postagem.setOpcao2("http://192.168.0.100:8080/projeto/imagens/"+postagem.getUsuario().getUsername()+"/"+nomeArquivoBase+"2.png");
	    		postagemDao.adiciona(postagem);
	    		
	    	} 
    		

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		result.use(Results.json()).withoutRoot().from(true).serialize();
		
    }
    
    public void salvaFotoPerfil(Usuario usuario){
    	
    	try {
    		
    		File dir = new File(context.getRealPath("/imagens")+"/"+usuario.getUsername());  
    		if(!dir.exists()){
    			dir.mkdirs();
    		}
    		
    		if(usuario.getFotoBase64() != null){
    			gravaImage(context.getRealPath("/imagens/"+usuario.getUsername()), usuario.getFotoBase64(),"foto");
    			usuario.setFoto("http://192.168.0.100:8080/projeto/imagens/"+usuario.getUsername()+"/foto.png");
    		}
    		
    		if(usuario.getBackgroundBase64() != null){
    			gravaImage(context.getRealPath("/imagens/"+usuario.getUsername()), usuario.getBackgroundBase64(),"background");
    			usuario.setBackground("http://192.168.0.100:8080/projeto/imagens/"+usuario.getUsername()+"/background.png");
    		}
    		
	
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    private void gravaImage(String path, String arquivo, String nomeArq) throws IOException{
    	
    	byte[] imageByteArray = Base64.decodeBase64(arquivo);
    	FileOutputStream imageOutFile = new FileOutputStream(
    			path+"/"+nomeArq+".png");

        imageOutFile.write(imageByteArray);
        imageOutFile.close();
    }

    
    @Post("/cadastraUsuario")
    @Consumes(value="application/json")
	public void cadastraUsuario(Usuario usuario) {
    	
    	salvaFotoPerfil(usuario);
		usuarioDao.adiciona(usuario);
		result.use(Results.json()).withoutRoot().from(usuario).serialize();
		
    }
    
    @Post("/alterarSenha")
    @Consumes(value="application/json")
	public void alterarSenha(Usuario usuario) {
    	
    	Usuario user = usuarioDao.pesquisaUsuarioPorId(usuario);
    	if(!usuario.getPassword().equals(user.getPassword())){
    		result.use(Results.json()).withoutRoot().from(false).serialize();
    	}else{
    		usuario.setPassword(usuario.getNovaSenha());
    		usuarioDao.adiciona(usuario);
    		result.use(Results.json()).withoutRoot().from(usuario).serialize();
    	}
    }
    
    @Get
    @Path("/postagem/lista")
	public void listaPostagem() {
    	
    	List<Postagem> listPostagem = postagemDao.listaTodos();
    	
    	if(listPostagem != null){
    		result.use(Results.json()).withoutRoot().from(listPostagem).include("usuario").include("listaComentarios").include("listaComentarios.usuario").exclude("usuario.password")
    		.include("listaCurtidas").include("listaComentarios.usuario").exclude("usuario.password")
    		.include("listaCurtidasOp1").include("listaCurtidasOp1.id").include("listaCurtidasOp1.usuario").exclude("usuario.password")
    		.include("listaCurtidasOp2").include("listaCurtidasOp2.id").include("listaCurtidasOp2.usuario").exclude("usuario.password").serialize();
    	}else{
    		result.use(Results.json()).withoutRoot().from("false").serialize();
    	}
    }
    
    @Get
    @Path("/listaPostagemUsuario/{idUsuario}")
	public void listaPostagemUsuario(Long idUsuario) {
    	
    	List<Postagem> listPostagem = postagemDao.listaPostagemUsuario(idUsuario);
    	
    	if(listPostagem != null){
    		result.use(Results.json()).withoutRoot().from(listPostagem).include("usuario").include("listaComentarios").include("listaComentarios.usuario").exclude("usuario.password")
    		.include("listaCurtidas").include("listaComentarios.usuario").exclude("usuario.password")
    		.include("listaCurtidasOp1").include("listaCurtidasOp1.id").include("listaCurtidasOp1.usuario").exclude("usuario.password")
    		.include("listaCurtidasOp2").include("listaCurtidasOp2.id").include("listaCurtidasOp2.usuario").exclude("usuario.password").serialize();
    	}else{
    		result.use(Results.json()).withoutRoot().from("false").serialize();
    	}
    }
    
    @Get
    @Path("/validaEmail/{usr.email}")
	public void validaEmail(Usuario usr) {
    	
    	Usuario user = usuarioDao.pesquisaUsuarioPorEmail(usr);
    	
    	if(user != null){
    		result.use(Results.json()).withoutRoot().from(user).serialize();
    	}else{
    		result.use(Results.json()).withoutRoot().from("false").serialize();
    	}
    }
    
    @Get
    @Path("/pesquisaUsuario/{userName}")
	public void pesquisaUsuario(String userName) {
    	
    	List<Usuario> lista = usuarioDao.pesquisaUsuarioPorUserName(userName);
    	result.use(Results.json()).withoutRoot().from(lista).serialize();
    	
 
    }
    
    @Get
    @Path("/pesquisaUsuarioPorId/{usr.id}")
	public void pesquisaUsuarioPorId(Usuario usr) {
    	
    	Usuario user = usuarioDao.pesquisaUsuarioPorId(usr);
    	
    	if(user != null){
    		result.use(Results.json()).withoutRoot().from(user)
    		.include("listaUsuariosSeguidos").include("listaUsuariosSeguidos.id")
    		.include("listaUsuarioSeguem").include("listaUsuariosSeguidos.id")
    		.include("listaUsuarioBloqueado").include("listaUsuarioBloqueado.id")
    		.include("listaPostagens").exclude("password").serialize();
    	}else{
    		result.use(Results.json()).withoutRoot().from(false).serialize();
    	}
    }
    
    @Post("/seguirUsuario")
    @Consumes(value="application/json")
	public void seguirUsuario(Seguir seguidor) {
    	
    	gravaNotificacaoSeguir(seguidor);
    	seguidorDao.adiciona(seguidor);
		result.use(Results.json()).withoutRoot().from(seguidor).include("id").serialize();
		
    }
    
    private void gravaNotificacaoSeguir(Seguir seguidor){
    	try {
    		Notificacao notif = new Notificacao();
        	notif.setData(new Date());
        	notif.setMensagem("Te adicionou");
        	notif.setStatus(Status.PENDENTE.getCodigo());
        	notif.setTipo(TipoNotificacao.SEGUIDOR.getCodigo());
        	notif.setUsuario(seguidor.getUsuario());
        	notif.setUsuarioNotificado(seguidor.getUsuarioSeguido());
        	notificacaoDao.adiciona(notif);
        	
    		List<UsuarioDevice> listaUsuarioDevice = usuarioDeviceDao.listaUsuarioDevice(seguidor.getUsuarioSeguido().getId());
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
    
    
    @Get
    @Path("/segueUsuario/{seguir.usuario.id}/{seguir.usuarioSeguido.id}")
	public void segueUsuario(Seguir seguir) {
    	
    	Seguir resultado = seguidorDao.segueUsuario(seguir);
    	
    	if(resultado != null){
    		result.use(Results.json()).withoutRoot().from(resultado).serialize();
    	}else{
    		result.use(Results.json()).withoutRoot().from(false).serialize();
    	}
    }
    
    @Delete
	@Path("/unfollow/{seguir.id.idUsuario}/{seguir.id.idUsuarioSeguido}")
	public void deleteSeguir(Seguir seguir) {
    	seguidorDao.deleta(seguir);
		result.use(Results.json()).withoutRoot().from(seguir).serialize();
    }
    
    
    @Get
    @Path("/listaSeguidores/{idUsuario}")
	public void listaSeguidores(Long idUsuario) {
    	List<Seguir> lista = seguidorDao.listaSeguidores(idUsuario);
    	
    	List<Usuario> listaRetorno = new ArrayList<Usuario>();
    	for (Seguir seguir : lista) {
    		listaRetorno.add(seguir.getUsuario());
		}
    	
    	result.use(Results.json()).withoutRoot().from(listaRetorno).serialize();
    }
    
    @Get
    @Path("/listaSeguindo/{idUsuario}")
	public void listaSeguindo(Long idUsuario) {
    	List<Seguir> lista = seguidorDao.listaSeguindo(idUsuario);
    	
    	List<Usuario> listaRetorno = new ArrayList<Usuario>();
    	for (Seguir seguir : lista) {
    		listaRetorno.add(seguir.getUsuarioSeguido());
		}
    	
    	result.use(Results.json()).withoutRoot().from(listaRetorno).serialize();
    }
    
    @Post("/bloquearUsuario")
    @Consumes(value="application/json")
	public void bloquearUsuario(Bloqueado bloqueado) {

    	bloqueadoDao.adiciona(bloqueado);
		result.use(Results.json()).withoutRoot().from(bloqueado).include("id").serialize();
    }
    
    @Delete
	@Path("/desbloquear/{bloqueado.id.idUsuario}/{bloqueado.id.idUsuarioBloqueado}")
	public void desbloquear(Bloqueado bloqueado) {
    	bloqueadoDao.deleta(bloqueado);
		result.use(Results.json()).withoutRoot().from(bloqueado).include("id").serialize();
    }
    
    @Get
    @Path("/pesquisaUsuariosBloqueados/{idUsuario}")
	public void pesquisaUsuariosBloqueados(Long idUsuario) {
    	
    	List<Bloqueado> lista = bloqueadoDao.listaBloqueados(idUsuario);
    	result.use(Results.json()).withoutRoot().from(lista).include("usuario").include("usuarioBloqueado").serialize();
    	
 
    }
    
    @Post("/salvaNotificao")
    @Consumes(value="application/json")
	public void salvaNotificao(Notificacao notificacao) {
    	notificacaoDao.adiciona(notificacao);
		result.use(Results.json()).withoutRoot().from(notificacao).include("id").serialize();
    }

    @Get
    @Path("/listaNotificaoUsuario/{idUsuario}")
	public void listaNotificaoUsuario(Long idUsuario) {
    	
    	List<Notificacao> lista = notificacaoDao.listaNotificacao(idUsuario);
    	result.use(Results.json()).withoutRoot().from(lista).include("usuario").include("usuarioNotificado").serialize();
    	
    }
    
    @Get
    @Path("/listaNotificaoPendentesUsuario/{idUsuario}")
	public void listaNotificaoPendentesUsuario(Long idUsuario) {
    	
    	List<Notificacao> lista = notificacaoDao.listaNotificaoPendentesUsuario(idUsuario);
    	result.use(Results.json()).withoutRoot().from(lista).include("usuario").include("usuarioNotificado").serialize();
    	
    }
    
	@Post("/atualizaNotificacao")
    @Consumes(value="application/json")
	public void atualizaNotificacao(List<Notificacao> listaNotif){
		for (Notificacao notificacao : listaNotif) {
			notificacao.setStatus(Status.CONCLUIDO.getCodigo());
			notificacaoDao.adiciona(notificacao);
		}
		
		result.use(Results.json()).withoutRoot().from(true).serialize();
	}


}
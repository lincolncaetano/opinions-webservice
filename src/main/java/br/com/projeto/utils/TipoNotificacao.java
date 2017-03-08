package br.com.projeto.utils;

public enum TipoNotificacao {

	SEGUIDOR(1L, "SEGUIDOR"),
    VOTO(2L, "VOTO");
	
	private Long codigo;
    private String tipo;
	
	private TipoNotificacao(Long codigo, String tipo) {
		this.codigo = codigo;
		this.tipo = tipo;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public String getTipo() {
		return tipo;
	}
	
	
    
    

}

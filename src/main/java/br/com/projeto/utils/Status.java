package br.com.projeto.utils;

public enum Status {

	PENDENTE("P", "SEGUIDOR"),
    CONCLUIDO("C", "VOTO");
	
	private String codigo;
    private String tipo;
	
	private Status(String codigo, String tipo) {
		this.codigo = codigo;
		this.tipo = tipo;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public String getTipo() {
		return tipo;
	}
	
	
	
    
    

}

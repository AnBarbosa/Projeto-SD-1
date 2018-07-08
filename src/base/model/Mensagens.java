package base.model;

public enum Mensagens {
	OLA_CLIENTE("Bem vindo ao cliente. Digite \"help\", para"
			+ " obter a lista de comandos, ou digite o comando desejado."), 
	COMANDO_DESCONHECIDO("Desculpa. Não entendi o que você deseja fazer.");
	
	
	
	Mensagens(String texto) {
		this.texto = texto;
	}
	public String texto;
	
	
}

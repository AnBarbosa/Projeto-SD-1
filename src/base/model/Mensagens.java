package base.model;

public enum Mensagens {
	OLA_CLIENTE(
			"Bem vindo ao cliente. Digite \"help\", para obter a lista \n"
			+ "de comandos, ou digite o comando desejado."
					), 
	COMANDO_DESCONHECIDO(
			"Desculpa. Não entendi o que você deseja fazer."
			), 
	COMANDO_COM_MUITOS_ARGUMENTOS(
			"Você digitou muita coisa. Nossos comandos não recebem tantos argumentos."
			);
	
	
	
	Mensagens(String texto) {
		this.texto = texto;
	}
	public String texto;
	
	
}

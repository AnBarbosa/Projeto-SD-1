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
			),
	ERRO_COMANDO_PEDE_UM_ARGUMENTO(
			"Esse comando pede um argumento. Tente novamente."
			),
	ERRO_COMANDO_NAO_PRECISA_DE_ARGUMENTOS(
			"Esse comando não tem argumentos. Tente novamente."
			);
	
	
	
	Mensagens(String texto) {
		this.texto = texto;
	}
	public String texto;
	
	
}

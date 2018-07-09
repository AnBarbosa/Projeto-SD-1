package base.model;

public enum Mensagens {
	
	/* Mensagens com hardcoded strings.
	* Cuidado com
	* OLA_CLIENTE -> cita comando help 
	* MY_PART_ERRO_NAO_CRIADA -> cita comando new-part
	* REMOTE_REPO_ERRO_NAO_CONECTADO -> cita comando connect <nome>
	* 
	*/
	OLA_CLIENTE(
			"Bem vindo ao cliente. Digite \"help\", para obter a lista de comandos, ou digite o comando desejado."
					), 
	COMANDO_DESCONHECIDO("Desculpa. Não entendi o que você deseja fazer."), 
	COMANDO_COM_MUITOS_ARGUMENTOS("Você digitou muita coisa. Nossos comandos não recebem tantos argumentos."),
	ERRO_COMANDO_PEDE_UM_ARGUMENTO("Esse comando pede um argumento. Tente novamente."),
	ERRO_COMANDO_NAO_PRECISA_DE_ARGUMENTOS("Esse comando não tem argumentos. Tente novamente."), 
	HELP_ERRO_COMANDO_NAO_LOCALIZADO("Comando não localizado."), 
	CONEXAO_SUCESSO("Conectado com sucesso."), 
	CONEXAO_ERRO_LOCATE("Não foi possível localizar um rmiregistry."), 
	CONEXAO_ERRO_LOOKUP_NOT_BOUND("Não foi possível localizar o repositório: "), 
	CONEXAO_ERRO_REMOTE("Erro remoto. "), 
	REPO_LIST_ERRO_REMOTE("Não foi possível obter lista de repositórios."), 
	TOKEN_FIM_DE_FUNCAO("---------------------"), 
	MY_PART_CODIGO_VALUE("Será determinado ao adicionar a parte a um repositorio."), 
	MY_PART_ERRO_NAO_CRIADA("Nenhuma parte está sendo editada. Crie uma nova parte com new-part"), 
	NEW_PART_PARTE_CRIADA("Nova parte criada com sucesso."), 
	REMOTE_REPO_ERRO_NAO_CONECTADO("Você não está conectado a nenhum repositório. Utilize \"connect <repositorio>\" para se conectar."),
	ADD_2_MYPART_ERRO_NOT_GRABBED("Você deve informar o código da peça, ou selecioná-la com grab."), 
	GRAB_READ_TO_ADD("Adicione à my-part usando add-to-my-part."),
	ERROR_REMOTE_EXCEPTION("Erro ao efetuar operação remota."), 
	GRAB_ERROR_INFORMADO_TEXTO("Por favor, informe o nome da peça.");
	; 
	
	
	
	
	Mensagens(String texto) {
		this.texto = texto;
	}
	public String texto;
	
	
}

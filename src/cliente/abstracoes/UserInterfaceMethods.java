package cliente.abstracoes;

public abstract class UserInterfaceMethods {

	// Funções gerais
	@ToUser(descricao="Essa função exibe todos os comandos possíveis.")
	public abstract void help();
	
	// Funções com servidor
	@ToUser(descricao="Exibe a lista de repositorios existentes.")
	public abstract void repoList();
	
	@ToUser(descricao="Lista todas as Parts no repositório ao qual está conectado")
	public abstract void listPartsOnServer();
	
	@ToUser(descricao="Conecta-se ao repositório escolhido.",
			parametro="nomeDoServidor")
	public abstract void connect(String servidor);
	
	
	
	// Funções my-part
	/** Exibe os dados da my-part.**/
	@ToUser(descricao="Exibe os dados da Part sendo criada.", parametro="")
	public abstract void myPart();
	
	@ToUser(descricao="Inicia o processo de criacao de uma nova Part.", parametro="nome")
	public abstract void newPart(String nome);
	
	@ToUser(descricao="Seta a descricao da my-part", parametro="")
	public abstract void setDescricao(String descricao);
	
	
	@ToUser(descricao="Obtem a descricao da my-part")
	public abstract void getDescricao();
	
	
	@ToUser(descricao="Seleciona a peca do repositorio atual com o código code. \n"
			+ "Essa peça pode ser adicionada à myPart através do comando add-to-my-part",
			parametro="codigo")
	public abstract void grab(String code);
	
	
	@ToUser(descricao="Adiciona a parte selecionada por grab à my-part.",
			parametro="")
	public abstract void addToMyPart();
	

	@ToUser(descricao="Adiciona a Part com o código especificado à my-part.", 
			parametro="codigo")
	public abstract void addToMyPart(String code);
	
	@ToUser(descricao="Adiciona my-part ao repositorio que está conectado.", 
			parametro="")
	public abstract void addMyPartToRepository();
	
	
}

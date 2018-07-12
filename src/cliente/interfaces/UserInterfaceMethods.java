package cliente.interfaces;

import cliente.interfaces.anotacoes.ToUser;


/**
 * Os métodos dessa interface marcados como \@ToUser são exportados para 
 * o usuário. 
 * 
 * Um método exportado deve:
 *   1) Retornar void
 *   2) Ter no máximo um parâmetro, que deve ser String.
 * 
 * Podemos adicionar mais funções ao usuário apenas incluindo a função nessa
 * interface e anotando a função com \@ToUser. Devemos informar a descricao e
 * os parâmetros para que eles sejam exibidos quando o usuário digitar help.
 *
 *   Veja interfaces.anotacoes.ToUser para mais informações. 
 * 
 *   Essa é uma classe abstrata pois as anotações de interface não são herdadas.
 *   // Todo: Verificar se a nova implementação do AutoWiredeReceiver precisa 
 *   que essa seja uma classe abstrata ou permite interfaces.
 * @author André Barbosa
 *
 */
public abstract class UserInterfaceMethods {

	// Funções gerais
	@ToUser(descricao="Essa função exibe todos os comandos possíveis.")
	public abstract void help();
	
	@ToUser(descricao="Exibe todas as descricoes de um comando.",
			parametro="comando")
	public abstract void help(String comando);
	
	// Interação com o Repositório
	@ToUser(descricao="Exibe a lista de repositorios existentes.")
	public abstract void repoList();
	
	@ToUser(descricao="Lista todas as Parts do repositório ao qual o usuário está conectado")
	public abstract void listp();
	
	@ToUser(descricao="Conecta-se ao repositório escolhido.",
			parametro="nomeDoRepositorio")
	public abstract void connect(String repositorio);
	
	
	// Funções my-part
	/** Exibe os dados da my-part.**/
	@ToUser(descricao="Exibe os dados da Part sendo criada.", parametro="")
	public abstract void myPart();
	
	@ToUser(descricao="Inicia o processo de criacao de uma nova my-part, descartando a atual.", parametro="nome")
	public abstract void newPart(String nome);
	
	@ToUser(descricao="Renomeia a my-part.", parametro="nome")
	public abstract void rename(String nome);
	
	@ToUser(descricao="Seta a descricao da my-part", parametro="")
	public abstract void setDescricao(String descricao);
	
	
	@ToUser(descricao="Obtem a descricao da my-part")
	public abstract void getDescricao();
	
	
	@ToUser(descricao="\"Pega\" a peca do repositorio atual com o código code. "
			+ "Essa peça pode ser adicionada à my-part através do comando addm, ou editada se o usuário usar o comando troca.",
			parametro="codigo")
	public abstract void grab(String code);
	
	
	@ToUser(descricao="Adiciona a parte selecionada por getp à my-part.",
			parametro="")
	public abstract void addMy();
	

	@ToUser(descricao="Adiciona a Part com o código especificado à my-part.", 
			parametro="codigo")
	public abstract void addMy(String code);
	
	@ToUser(descricao="Adiciona a my-part ao repositorio ao qual o usuário que esta conectado.", 
			parametro="")
	public abstract void addRepo();
	
	@ToUser(descricao="Se conecta ao repositório indicado e adiciona a my-part àquele repositório.", 
			parametro="repositorio")
	public abstract void addRepo(String repositorio);
	
	
	@ToUser(descricao="Troca a my-part e a peça \"pega\" usando grab, de forma a poder editar a peça \"grabbed\" ", 
			parametro="")
	public abstract void troca();
	
	
	
	
}

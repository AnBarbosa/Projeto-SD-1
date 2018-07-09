package cliente.view;

import java.util.List;

import cliente.controller.InformacoesSobreMetodos;
import cliente.controller.InformacoesSobreMetodos.TemParametro;
import cliente.interfaces.UserInterfaceMethods;
import cliente.view.auxiliar.Mensagens;

/**
 *   Essa classe lê todos os métodos da interface UserInterfaceMethods
 * marcados com \@ToUser. Os dados da anotação são utilizados para compor
 * a resposta da função 'help'. 
 * 
 *   Além disso, ela extraí uma tabela, utilizada pelo ClientController para
 * fazer a ligação entre os comandos do usuário e os métodos da interface.
 * 
 * 
 *   Note que só é possível exportar para o usuário métodos com ZERO ou UM
 * argumento, e que não têm retorno.
 * @author André Barbosa
 *
 */
public class AutoWiredReceiver extends UserInterfaceMethods {

	private InformacoesSobreMetodos mInfo = new InformacoesSobreMetodos();

	public AutoWiredReceiver() {
		mInfo.exportaMetodos(this);
	}
	
	public InformacoesSobreMetodos getInformacoesSobreMetodos() {
		return mInfo;
	}
	
	
	/**
	 * Essa função recebe as informações dos métodos marcados como \@ToUser
	 * na UserInterfaceMethods e as exibe para o usuário.
	 */
	@Override
	public void help() {
		
		System.out.println("Chamado help");
		StringBuilder sb = new StringBuilder("== Metodos com zero parametros: \n");
		String conteudo;
		
		for (List<String> helper : mInfo.getHelptextSem())
		{
			conteudo = mInfo.parseHelptext(helper, TemParametro.SEM_PARAMETRO);
			sb.append(conteudo+"\n");
		}

		sb.append("\n== Metodos com um parametro: \n");
		for (List<String> helper : mInfo.getHelptextCom())
		{
			conteudo = mInfo.parseHelptext(helper, TemParametro.SEM_PARAMETRO);
			sb.append(conteudo+"\n");
		}
		
		// Remove o ultimo \n
		sb.setLength(sb.length()-2);
		System.out.println(sb.toString());
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	@Override
	public void help(String comando) {
		
		StringBuilder sBuilder = new StringBuilder("= Ajuda para "+comando+" =\n");
		
		boolean isOnNoArg = mInfo.helptextContains(comando, TemParametro.SEM_PARAMETRO);
		boolean isOnOneArg = mInfo.helptextContains(comando, TemParametro.COM_PARAMETRO);
		
		if(isOnNoArg) {
		//	sBuilder.append(" Versao sem argumentos.\n");
			sBuilder.append(mInfo.getHelptext(comando, TemParametro.SEM_PARAMETRO));
			sBuilder.append("\n");
		} 
		if(isOnOneArg) {
		//  sBuilder.append(" Versao com argumentos.\n");
			sBuilder.append(mInfo.getHelptext(comando, TemParametro.COM_PARAMETRO));
			sBuilder.append("\n");
		}
		if(!(isOnNoArg || isOnOneArg) ) {
			sBuilder.append(Mensagens.HELP_ERRO_COMANDO_NAO_LOCALIZADO.texto);
			sBuilder.append("\n");
		}

	
		// Remove o ultimo \n
		sBuilder.setLength(sBuilder.length()-2);
		System.out.println(sBuilder.toString());
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	

	@Override
	public void repoList() {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado repoList");
	}

	@Override
	public void repoListParts() {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado listPartsOnServer");
	}

	@Override
	public void connect(String servidor) {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado connect com arg " + servidor);
	}

	@Override
	public void myPart() {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado myPart");
	}

	
	@Override
	public void newPart(String nome) {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado newPart com arg "+nome);
	}
	
	@Override
	public void setDescricao(String descricao) {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("setDescricao com arg "+descricao);
	}

	@Override
	public void getDescricao() {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado getDescricao");
	}

	@Override
	public void grab(String code) {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado grab com arg "+code);
	}

	@Override
	public void addToMyPart() {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado addToMyPart");
	}
	
	@Override
	public void addToMyPart(String code) {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado addToMyPart com arg "+code);
	}

	@Override
	public void repoAdd() {
		// Espera-se que essa função seja sobreescrita pela AbstractClientView
		System.out.println("Chamado addMyPartToRepository");
	}

}

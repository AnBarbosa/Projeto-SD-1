package cliente.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import base.model.Mensagens;
import cliente.abstracoes.UserInterfaceMethods;
import cliente.model.InformacoesSobreMetodos.TemParametro;
import cliente.abstracoes.ToUser;

public class AutoWiredCommands extends UserInterfaceMethods {

	private InformacoesSobreMetodos mInfo = new InformacoesSobreMetodos();

	public AutoWiredCommands() {
		mInfo.exportaMetodos(this);
	}
	
	public InformacoesSobreMetodos getInformacoesSobreMetodos() {
		return mInfo;
	}
	
	
	@Override
	public void help() {
		// TODO Auto-generated method stub
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
		String conteudo = "ERRO";
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
		// TODO Auto-generated method stub
		System.out.println("Chamado repoList");
	}

	@Override
	public void repoListParts() {
		// TODO Auto-generated method stub
		System.out.println("Chamado listPartsOnServer");
	}

	@Override
	public void connect(String servidor) {
		// TODO Auto-generated method stub
		System.out.println("Chamado connect com arg " + servidor);
	}

	@Override
	public void myPart() {
		// TODO Auto-generated method stub
		System.out.println("Chamado myPart");
	}

	
	@Override
	public void newPart(String nome) {
		// TODO Auto-generated method stub
		System.out.println("Chamado newPart com arg "+nome);
	}
	
	@Override
	public void setDescricao(String descricao) {
		// TODO Auto-generated method stub
		System.out.println("setDescricao com arg "+descricao);
	}

	@Override
	public void getDescricao() {
		// TODO Auto-generated method stub
		System.out.println("Chamado getDescricao");
	}

	@Override
	public void grab(String code) {
		// TODO Auto-generated method stub
		System.out.println("Chamado grab com arg "+code);
	}

	@Override
	public void addToMyPart() {
		// TODO Auto-generated method stub
		System.out.println("Chamado addToMyPart");
	}
	
	@Override
	public void addToMyPart(String code) {
		// TODO Auto-generated method stub
		System.out.println("Chamado addToMyPart com arg "+code);
	}

	@Override
	public void repoAdd() {
		// TODO Auto-generated method stub
		System.out.println("Chamado addMyPartToRepository");
	}

}

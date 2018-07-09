package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import cliente.abstracoes.ToUser;
import cliente.abstracoes.UserInterfaceMethods;

public class InformacoesSobreMetodos {
	private final Map<String, Runnable> comandosSemArgumento;
	private final Map<String, Consumer<String>> comandosComArgumento;
	private final List<List<String>> helptextSem;
	private final List<List<String>> helptextCom;
	public enum TemParametro { COM_PARAMETRO, SEM_PARAMETRO;}

	
	public InformacoesSobreMetodos() {
		this.comandosSemArgumento = new HashMap<>();
		this.comandosComArgumento = new HashMap<>();
		this.helptextSem = new ArrayList<>();
		this.helptextCom = new ArrayList<>();
	}
	
	public InformacoesSobreMetodos(UserInterfaceMethods uim) {
		this.comandosSemArgumento = new HashMap<>();
		this.comandosComArgumento = new HashMap<>();
		this.helptextSem = new ArrayList<>();
		this.helptextCom = new ArrayList<>();
		exportaMetodos(uim);
	}
	
	public void exportaMetodos(UserInterfaceMethods uim) {
		Method[] metodos = obtemTodosOsMetodosDaInterface();
		assert(metodos!=null);
		assert(metodos.length > 0);

		for(Method m : metodos) {
			if(m.isAnnotationPresent(ToUser.class))
			{
				exportaMetodo(m, uim);
			}
		}
	}
	

	public Map<String, Runnable> getComandosSemArgumento() {
		return new HashMap<>(comandosSemArgumento);
	}


	public Map<String, Consumer<String>> getComandosComArgumento() {
		return new HashMap<>(comandosComArgumento);
	}


	public List<List<String>> getHelptextSem() {
		return new ArrayList<List<String>>(helptextSem);
	}

	public List<List<String>> getHelptextCom() {
		return new ArrayList<List<String>>(helptextCom);
	}
	
	
	public String parseHelptext(List<String> helptext, TemParametro par) {
		String formato, nome, descricao, parametro;
		String retorno = "ERROR";
		
		if(par == TemParametro.SEM_PARAMETRO)
		{
			formato = "  %s: %s";
			nome = helptext.get(0);
			descricao = helptext.get(1);
			retorno = String.format(formato,  nome, descricao);
			
		} else if (par == TemParametro.COM_PARAMETRO){
			formato = "  %s <%s>: %s";
			nome = helptext.get(0);
			descricao = helptext.get(1);
			parametro = helptext.get(2);
			retorno =  String.format(formato,  nome, parametro, descricao);
		}
		return retorno;
	}
	
	public boolean helptextContains(String metodo, TemParametro par)
	{
		boolean tem = false;
		if(par == TemParametro.SEM_PARAMETRO)
		{
			tem = helptextSem.parallelStream().anyMatch((s)->s.contains(metodo));
		}
		if(par == TemParametro.COM_PARAMETRO)
		{
			tem = helptextCom.parallelStream().anyMatch((s)->s.contains(metodo));
		}
		return tem;
	}
	
	private List<String> helptextGetList(String metodo, TemParametro par){
		List<List<String>> listaDeListas = Collections.emptyList();
		if(helptextContains(metodo, par))
		{
			if(par == TemParametro.SEM_PARAMETRO) {
				listaDeListas = helptextSem;
			}
			if(par == TemParametro.COM_PARAMETRO) {
				listaDeListas = helptextCom;
			}
			
			for( List<String> helper : listaDeListas) {
				if(helper.get(0).equals(metodo)) {
					return helper;						
				}
			}
			
		}
		return Collections.emptyList();
	}
	
	public String getHelptext(String metodo, TemParametro par) {
		String formato, nome, descricao, parametro;
		String retorno = "Não Encontrado Texto de Ajuda para esse método.";
	
		if(helptextContains(metodo, par)) {
			List<String> helpList = helptextGetList(metodo, par);
			retorno = parseHelptext(helpList, par);
		}
		return retorno;
	}
	
	

	private Method[] obtemTodosOsMetodosDaInterface() {
		Method[] metodos = null;
		try {
			Class classe = UserInterfaceMethods.class;
			metodos = classe.getDeclaredMethods();
			
		} catch (Throwable e) {
			System.err.println("Erro ao obter lista de métodos.");
			e.printStackTrace();
		}
		
		assert(metodos!=null);
		assert(metodos.length > 0);
		return metodos;
	}
	
	
	
	private void exportaMetodo(Method m, UserInterfaceMethods uim)
	{
		boolean exportarAoUsuario = m.isAnnotationPresent(
											ToUser.class);
		if(!exportarAoUsuario) {
			return;
		}
				 
		ToUser info = (ToUser) m.getAnnotation(ToUser.class);
		
		String nome = util.StringUtils.convertCamelCase(m.getName());
		String descricao = info.descricao();
		String parametro = info.parametro();
		
		int numeroDeParametros = m.getParameterCount();
		
		
		switch(numeroDeParametros) {
		case 0:
			comandosSemArgumento.put(nome, ()->{
				try {
					m.invoke(uim);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					System.err.println("Erro ao invocar o método especificado.");
					e.getCause().printStackTrace();
					
				}
			});
			ArrayList<String> helperS = new ArrayList();
			helperS.add(nome);
			helperS.add(descricao);
			helptextSem.add(helperS);
			break;
		case 1:
			comandosComArgumento.put(nome,  (argumentoPassado)->{
				try {
					m.invoke(uim, argumentoPassado);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					System.err.println("Erro ao invocar o método especificado.");
					e.printStackTrace();
				}});
			ArrayList<String> helperC = new ArrayList();
			helperC.add(nome);
			helperC.add(descricao);
			helperC.add(parametro);
			helptextCom.add(helperC);
			//System.out.println("Lista Com: "+helpTextCom);
			break;
		} 
		
		
	}
}
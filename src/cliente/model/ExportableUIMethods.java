package cliente.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import cliente.abstracoes.UserInterfaceMethods;
import cliente.abstracoes.ToUser;

public class ExportableUIMethods extends UserInterfaceMethods {

	
	private Map<String, Runnable> comandosSemArgumento = new HashMap<>();
	private Map<String, Consumer<String>> comandosComArgumento = new HashMap<>();
	private List<String[]> helpTextSem = new ArrayList<String[]>();
	private List<String[]> helpTextCom = new ArrayList<String[]>();
	 

	public ExportableUIMethods() {
		exportaMetodos();
	}
	
	
	public Map<String, Runnable> getMapaComandosSemArgumento(){
		return new HashMap(comandosSemArgumento);
	}
	
	public Map<String, Consumer<String>> getMapaComandosComUmArgumento(){
		return new HashMap(comandosComArgumento);
	}
	
	
	private Method[] obtemTodosOsMetodosDaInterface() {
		Method[] metodos = null;
		try {
			Class classe = this.getClass();
			classe = cliente.abstracoes.UserInterfaceMethods.class;
			metodos = classe.getDeclaredMethods();
			
		} catch (Throwable e) {
			System.err.println("Erro ao obter lista de métodos.");
			e.printStackTrace();
		}
		
		assert(metodos!=null);
		assert(metodos.length > 0);
		return metodos;
	}
	
	private void exportaMetodos() {
		Method[] metodos = obtemTodosOsMetodosDaInterface();
		assert(metodos!=null);
		assert(metodos.length > 0);

		for(Method m : metodos) {
			if(m.isAnnotationPresent(cliente.abstracoes.ToUser.class))
			{
				exportaMetodo(m);
			}
		}
	}
	
	private void exportaMetodo(Method m)
	{
		boolean exportarAoUsuario = m.isAnnotationPresent(
											cliente.abstracoes.ToUser.class);
		if(!exportarAoUsuario) {
			return;
		}
				 
		ToUser info = (ToUser) m.getAnnotation(cliente.abstracoes.ToUser.class);
		
		String nome = util.StringUtils.convertCamelCase(m.getName());
		String descricao = info.descricao();
		String parametro = info.parametro();
		
		int numeroDeParametros = m.getParameterCount();
		
		
		switch(numeroDeParametros) {
		case 0:
			comandosSemArgumento.put(nome, ()->{
				try {
					m.invoke(this);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					System.err.println("Erro ao invocar o método especificado.");
					e.printStackTrace();
				}
			});
			String[] helperS = {nome, descricao};
			helpTextSem.add(helperS);
			break;
		case 1:
			comandosComArgumento.put(nome,  (argumentoPassado)->{
				try {
					m.invoke(this, argumentoPassado);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					System.err.println("Erro ao invocar o método especificado.");
					e.printStackTrace();
				}});
			
			String[] helperC = {nome, descricao, parametro};
			helpTextCom.add(helperC);
			//System.out.println("Lista Com: "+helpTextCom);
			break;
		} 
		
		
	}


	
	@Override
	public void help() {
		// TODO Auto-generated method stub
		System.out.println("Chamado help");
		StringBuilder sb = new StringBuilder("== Metodos com zero parametros: \n");
		
		for (String[] helper : helpTextSem)
		{
			String formato = "  %s: %s\n";
			String nome = helper[0];
			String descricao = helper[1];
			sb.append(String.format(formato,  nome, descricao));
		}

		sb.append("\n== Metodos com um parametro: \n");
		for (String[] helper : helpTextCom)
		{
			String formato = "  %s <%s>: %s\n";
			String nome = helper[0];
			String descricao = helper[1];
			String parametro = helper[2];
			sb.append(String.format(formato,  nome, parametro, descricao));
		}
		
		System.out.println(sb.toString());
	}

	@Override
	public void repoList() {
		// TODO Auto-generated method stub
		System.out.println("Chamado repoList");
	}

	@Override
	public void listPartsOnServer() {
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
	public void addMyPartToRepository() {
		// TODO Auto-generated method stub
		System.out.println("Chamado addMyPartToRepository");
	}

}

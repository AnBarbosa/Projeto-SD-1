package cliente.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Consumer;

import cliente.view.AutoWiredReceiver;
import cliente.view.auxiliar.Mensagens;
import util.MsgUtils;


/**
 *  Nessa classe ocorre o main loop da aplicação. 
 *  
 *   Ela recebe os inputs do usuário e processa os comandos baseados nas funções
 *  exportadas pela AutoWiredReceiver.
 *   
 *   As principais funções foram comentadas.
 * @author André Barbosa
 *
 */
public class ClienteController {

	private boolean isRunning = true;
	private AutoWiredReceiver userCommands;
	private Scanner inputScanner;

	
	public ClienteController(AutoWiredReceiver commandInterpreter) {
		userCommands = commandInterpreter;
		inputScanner = null;
	}
	
	public ClienteController(AutoWiredReceiver commandInterpreter, Scanner inputs) {
		userCommands = commandInterpreter;
		inputScanner = inputs;
	}

	private Map<String, Runnable> mapaComandosSemArgumento = new HashMap<>();
	private Map<String, Consumer<String>> mapaComandosComUmArgumento = new HashMap<>();
	
	// FUNCOES DE FLUXO 
	public void start() {
		MsgUtils.println(Mensagens.OLA_CLIENTE.texto);
		isRunning = true;
		abreInputScanner();
		InformacoesSobreMetodos info = userCommands.getInformacoesSobreMetodos();
		mapaComandosSemArgumento = info.getComandosSemArgumento();
		mapaComandosComUmArgumento = info.getComandosComArgumento();
		
		
		mapaComandosSemArgumento.put("exit", ()->this.exitClient());
		mapaComandosSemArgumento.put("quit", ()->this.exitClient());
		/*
		mapaComandosComUmArgumento.put("exemplo", (argumento)->this.exemplo(argumento));
		*/
	}
	
	public void loop() {
		while(isRunning) {
			String comandosAsString = recebeInput();
			List<String> comandosAsList = inputToList(comandosAsString);
			processaComandos(comandosAsList);
		}
	}
	
	public void end() {
		fechaInputScanner();
		MsgUtils.println("O programa terminou.");
	}


	private void exitClient() {
		this.isRunning = false;
	}
	
	
	// FUNCOES DE INTERACAO COM USUARIO
	
	/**
	 *  Essa função apenas aguarda o input do usuário e o retorna
	 *  numa String.
	 * @return comando dado pelo usuário, na forma de uma String.
	 */
	private String recebeInput(){
		
		String tokens = "";
		
		if(inputScanner == null) { 
			abreInputScanner();
		}
		
		try{
			tokens = inputScanner.nextLine();
			
		} catch (NoSuchElementException noLine) {
			tokens = "";
		} catch (Exception e) {
			MsgUtils.errorPrintln("Houve um erro ao receber o comando.");
			e.printStackTrace();
		}
		
		
		return tokens;
	}

	
	/**
	 * Essa função recebe a String contendo o comando do usuário, e 
	 * a transforma numa lista de palavras, que serão análisadas na
	 * próxima etapa. 
	 * @param comandosEmLinha
	 * @return listaDeTokens: as palavras que formam o comando, em lista.
	 */
	private List<String> inputToList(String comandosEmLinha)
	{
		if(comandosEmLinha.equals("")) {
			return Collections.emptyList();
		}
		
		List<String> listaDeTokens = new ArrayList<String>();
		try(Scanner parser = new Scanner(comandosEmLinha))
		{
			while(parser.hasNext()) {
				listaDeTokens.add(parser.next());
			}
		} catch (Exception e) {
			MsgUtils.errorPrintln("Erro ao processar token.");
			e.printStackTrace();
		}
		return listaDeTokens;
	}
	

	/**
	 * Essa método recebe uma lista de palavras. Baseado no tamanho da lista
	 * ela determina se é um comando com ou sem parâmetros, e tenta executá-lo.
	 * 
	 * Caso a lista esteja vazio, essa função não faz nada. 
	 * 
	 * Caso esse método não consiga interpretar o comando, ela irá exibir uma mensagem
	 * de erro ao usuário.
	 * @param comandosAsList
	 */
	private void processaComandos(List<String> comandosAsList) {
		if(comandosAsList.isEmpty()) {
			return;
		}
			
		int tamanhoDaLista = comandosAsList.size();
		
		String token, argumento;
		
		// token é o comando solicitado pelo usuário.
		token = comandosAsList.get(0); // é a primeira palavra que ele digitou.
		
		if(tamanhoDaLista == 1) {
			Runnable executavel = mapaComandosSemArgumento.get(token);
			if(executavel != null) {
				executaComando(executavel);
			} 
			/* Se não houver comando no mapa de comandos, o usuário digitou um
			comando desconhecido.           */ 
			else { 
				// Verificamos se conhecemos um comando que leva argumento
				if(mapaComandosComUmArgumento.containsKey(token)) {
					// Se existir, exibimos uma mensagem
					MsgUtils.println(token+": "+Mensagens.ERRO_COMANDO_PEDE_UM_ARGUMENTO.texto);
				} else { // Senão existir, exibimos outra mensagem
					MsgUtils.println(token+": "+Mensagens.COMANDO_DESCONHECIDO.texto);
				}
			}
		}
		
		
		
		if(tamanhoDaLista == 2) {
			argumento = comandosAsList.get(1); // argumento é a segunda palavra que o usuário digitou.
			Consumer<String> executavelComArgumento = mapaComandosComUmArgumento.get(token);
			
			if(executavelComArgumento != null) {
				executaComando(executavelComArgumento, argumento);
			}  
			// Se não localizarmos esse comando no mapa... 
			else { 
				// Se existir um comando igual, que não leva argumentos
				if(mapaComandosSemArgumento.containsKey(token)) {
					// Exibimos uma mensagem de erro
					MsgUtils.println(token+" *"+argumento+"*: "+Mensagens.ERRO_COMANDO_NAO_PRECISA_DE_ARGUMENTOS.texto);
				} else {
					// Senão exibimos outra.
					MsgUtils.println(token+" "+argumento+": "+Mensagens.COMANDO_DESCONHECIDO.texto);
				}
			}
		}
		
		/* 	Se a lista tiver tamanho maior que 2, então ou o usuário digitou 
		 muitos argumentos, ou ele digitou uma string no modelo "s1 s2..." */	
		if(tamanhoDaLista > 2) {
			// Primeiro avaliamos se há aspas. A análise é crua e limitada.
			
			boolean hasQuote = false;
			for(String comando : comandosAsList) {
				if ((comando).contains("\"")) { //  Se localizarmos uma única aspas,
					hasQuote=true;				// consideramos todos os outros argumentos
					break;						// como uma única palavra.
				}				// i.e aceitamos "asdf adsf asdf como um argumento.
			}
			
			
			if(hasQuote) {
				comandosAsList.remove(0);
				argumento = String.join(" ", comandosAsList);
				Consumer<String> comando1arg = mapaComandosComUmArgumento.get(token);
				if(comando1arg != null) {
					executaComando(comando1arg, argumento);
				}  else {
					if(mapaComandosSemArgumento.containsKey(token)) {
						MsgUtils.println(token+" *"+argumento+"*: "+Mensagens.ERRO_COMANDO_NAO_PRECISA_DE_ARGUMENTOS.texto);
					} else {
						MsgUtils.println(token+" "+argumento+": "+Mensagens.COMANDO_DESCONHECIDO.texto);
					}
				}
			}
			if(!hasQuote) {
				MsgUtils.println(String.join(" ", comandosAsList)+": "+Mensagens.COMANDO_COM_MUITOS_ARGUMENTOS.texto);
			}
		}
	}

	

	private void abreInputScanner() {
		if(inputScanner == null) {
			inputScanner = new Scanner(System.in);
		}
	}
	
	private void fechaInputScanner() {
		inputScanner.close();
	}
	
	private void executaComando(Runnable comando) {
		comando.run();
	}
	
	private void executaComando(Consumer<String> comando, String arg) {
		comando.accept(arg);
	}
	
	
	@SuppressWarnings("unused")
	private void exemplo(String argumento) {
		MsgUtils.println("Chamada função teste com argumento: "+argumento);
	}
}

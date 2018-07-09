package cliente.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Consumer;

import base.model.Mensagens;
import cliente.model.InformacoesSobreMetodos.TemParametro;

public class ClienteUI {

	private AutoWiredCommands userCommands = new ClientRMI();
	private boolean isRunning = true;
	
	private Scanner inputScanner;

	private Map<String, Runnable> mapaComandosSemArgumento = new HashMap<>();
	private Map<String, Consumer<String>> mapaComandosComUmArgumento = new HashMap<>();
	
	public static void main(String [] args) {
		ClienteUI c = new ClienteUI();
		c.start();	
		c.loop();
		c.end();
	}
	
	private void start() {
		System.out.println(Mensagens.OLA_CLIENTE.texto);
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
	
	private void loop() {
		while(isRunning) {
			String comandosAsString = recebeInput();
			List<String> comandosAsList = inputToList(comandosAsString);
			processaComandos(comandosAsList);
		}
	}
	
	private void end() {
		fechaInputScanner();
		System.out.println("O programa terminou.");
		
	}
	
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
			System.err.println("Erro ao processar token.");
			e.printStackTrace();
		}
		return listaDeTokens;
	}
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
			System.err.println("Houve um erro ao receber o comando.");
			e.printStackTrace();
		}
//		System.out.println("Comandos recebidos: "+tokens);
		return tokens;
	}

	private void abreInputScanner() {
		assert(inputScanner == null);
		inputScanner = new Scanner(System.in);
		
	}
	
	private void fechaInputScanner() {
		inputScanner.close();
	}
	
	private void processaComandos(List<String> comandosAsList) {
		if(comandosAsList.isEmpty()) {
			return;
		}
			
		int tamanho = comandosAsList.size();
		String token, argumento;
		
		token = comandosAsList.get(0);
		if(tamanho == 1) {
			if(mapaComandosSemArgumento.containsKey(token))
			{
				Runnable comando = mapaComandosSemArgumento.get(token);
				comando.run();
			} else {
				if(mapaComandosComUmArgumento.containsKey(token)) {
					System.out.println(token+": "+Mensagens.ERRO_COMANDO_PEDE_UM_ARGUMENTO.texto);
				} else {
					System.out.println(token+": "+Mensagens.COMANDO_DESCONHECIDO.texto);
				}
			}
		}
		
		if(tamanho == 2) {
			argumento = comandosAsList.get(1);
			if(mapaComandosComUmArgumento.containsKey(token)) {
				Consumer<String> comando = mapaComandosComUmArgumento.get(token);
				comando.accept(argumento);
			}  else {
				if(mapaComandosSemArgumento.containsKey(token)) {
					System.out.println(token+" *"+argumento+"*: "+Mensagens.ERRO_COMANDO_NAO_PRECISA_DE_ARGUMENTOS.texto);
				} else {
					System.out.println(token+" "+argumento+": "+Mensagens.COMANDO_DESCONHECIDO.texto);
				}
			}
		}
		
		if(tamanho > 2) {
			boolean hasQuote = false;
			for(String comando : comandosAsList) {
				if ((comando).contains("\"")) {
					hasQuote=true;
					break;
				}
			}
			if(hasQuote) {
				comandosAsList.remove(0);
				argumento = String.join(" ", comandosAsList);
				if(mapaComandosComUmArgumento.containsKey(token)) {
					Consumer<String> comando = mapaComandosComUmArgumento.get(token);
					comando.accept(argumento);
				}  else {
					if(mapaComandosSemArgumento.containsKey(token)) {
						System.out.println(token+" *"+argumento+"*: "+Mensagens.ERRO_COMANDO_NAO_PRECISA_DE_ARGUMENTOS.texto);
					} else {
						System.out.println(token+" "+argumento+": "+Mensagens.COMANDO_DESCONHECIDO.texto);
					}
				}
			}
			if(!hasQuote) {
				System.out.println(String.join(" ", comandosAsList)+": "+Mensagens.COMANDO_COM_MUITOS_ARGUMENTOS.texto);
			}
		}
	}

	private void exitClient() {
		this.isRunning = false;
	}
	
	
	private void exemplo(String argumento) {
		System.out.println("Chamada função teste com argumento: "+argumento);
	}
}

package cliente.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

import base.model.Mensagens;

public class Cliente {

	private boolean isRunning = true;
	private Map<String, Runnable> mapaComandos = new HashMap<>();
	private Scanner inputScanner;
	
	public static void main(String [] args) {
		Cliente c = new Cliente();
		c.start();	
//		while(true) {
//			String palavras = c.recebeComandos();
//			c.processaComandos(palavras);
//			if(palavras.equals("exit")) {
//				break;
//			}
//		}
		c.loop();
		c.end();
	}
	
	public void start() {
		System.out.println(Mensagens.OLA_CLIENTE.texto);
		abreInputScanner();
		mapaComandos.put("teste", ()->this.teste());
		mapaComandos.put("teste2", ()->this.teste2());
		mapaComandos.put("teste3", ()->this.teste3());
		mapaComandos.put("exit", ()->this.isRunning = false);
			}
	
	public void loop() {
		while(isRunning) {
			String tokens = recebeComandos();
			processaComandos(tokens);
		}
	}
	
	private void end() {
		fechaInputScanner();
		System.out.println("O programa terminou.");
		
	}
	
	private String recebeComandos(){
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
	
	private void processaComandos(String entrada) {

		try(Scanner parser = new Scanner(entrada)) {
			while(parser.hasNext()) {
				String token = parser.next();
				if(mapaComandos.containsKey(token))
				{
					Runnable comando = mapaComandos.get(token);
					comando.run();
				} else 
				{
					System.out.println(token+": "+Mensagens.COMANDO_DESCONHECIDO.texto);
				}
	//			System.out.println("Interpretado "+token);
			}
		} catch (Exception e) {
			System.err.println("Erro ao processar token.");
			e.printStackTrace();
		}
	}

	private void teste() {
		System.out.println("Chamada função teste.");
	}
	
	private void teste2() {
		System.out.println("Chamada função teste2.");
	}
	
	private void teste3() {
		System.out.println("Chamada função teste3s.");
	}
}

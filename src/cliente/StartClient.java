package cliente;

import java.util.Scanner;

import cliente.controller.ClienteController;
import cliente.view.AutoWiredReceiver;

public class StartClient {


	public static void main(String[] args) {
	
		// Scanner é utilizado para controlar a origem da entrada do usuário.
		// System.in -> irá receber comandos via terminal.
		Scanner manual = new Scanner(System.in);
		
		/* AutoWiredReceiver é utilizado para interpretar os comandos do usuário.
		 * ClienteRMI significa que usara um servidor remoto via RMI. 
		 * Use ClienteLocal() para trabalhar com um servidor local(dentro da
		 * própria execução).
		 *  */
		AutoWiredReceiver remoto = new  cliente.view.ClienteRMI();
		
		/* Recebe e processa os comandos do usuário */
		ClienteController cliente;
		cliente = new ClienteController(remoto, manual);
		
		cliente.start(); // Faz as preparações necessárias.
		cliente.loop();  // Executa o loop principal.
		cliente.end();   // Finaliza o programa.
	}

}

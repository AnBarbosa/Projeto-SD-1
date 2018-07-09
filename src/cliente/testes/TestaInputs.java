package cliente.testes;

import java.util.Scanner;

import cliente.controller.ClienteController;
import cliente.view.AutoWiredReceiver;
import cliente.view.ClienteLocal;
import cliente.view.ClienteRMI;

public class TestaInputs {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		
		String[] rotina0 = { 
				
				"repo-list", 
				"connect teste1", 
				"repo-list-parts", 
				"new-part andre",
				"my-part",
				"repo-add",
				"repo-add",
				"new-part rodas",
				"repo-add",
				"repo-list-parts",
				"grab teste11",
				"add-to-my-part",
				"my-part",
				"repo-add",
				"repo-list-parts"
				};
		
		
		String[]  rotina1 = { "connect teste1", "new-part Mnha", "repo-add", "my-part"};
		
		
		String[] rotinaCarro = {
				
				"connect remoteRepo",
				// Crio uma roda.
				"new-part roda", "repo-add", "my-part",
				
				// Crio um carro e adiciono 4 vezes (o mesmo)
				"new-part carro", "repo-add", "repo-add", "repo-add", "repo-add",
				
				// Coloco rodas 
				"grab remoteRepo1", "add-to-my-part",  "add-to-my-part", "add-to-my-part", "add-to-my-part", "repo-add", "my-part",
				// Vejo repositorio (quero ver 2 carros com teste12 e um com teste13
				"repo-list-parts"
		};
		
		String[] rotinaRepeticao = {
				
				"connect remoteRepo",
				// Crio uma roda.
				"new-part roda", "repo-add", "repo-add", "repo-add", "repo-add", "repo-add",
				
				// Vejo repositorio (quero ver varias rodas agrupadas).
				"repo-list-parts" 	};
		
		
		String[] forcaErro0 = { "help", "repo-list", "repo-list-parts", "my-part", "get-descricao", "add-to-my-part", "repo-add" };
		
		String[] forcaErro1 = { "connect invalido", "help invalido", "new-part", "set-descricao invalido", "grab invalido", "add-to-my-part invalido"};
		
		String[] inputs = forcaErro1;
		
		// remoteRepo
		// container
		
		String formato = "";
		for(int i=0; i<inputs.length; i++) {
			formato += "%s\n";
		}

		String entradas = String.format(formato, inputs);
		Scanner auto = new Scanner(entradas);
		
		@SuppressWarnings("resource")
		Scanner manual = new Scanner(System.in);
		
		AutoWiredReceiver local = new ClienteLocal();
		AutoWiredReceiver remoto = new ClienteRMI();
		
		ClienteController cliente;
		cliente = new ClienteController(remoto, auto);
		//cliente = new ClienteUI(remoto, manual);
		//cliente = new ClienteUI(local, auto);
		//cliente = new ClienteUI(local, manual);
	
		cliente.start();
		cliente.loop();
		cliente.end();
		
		
		
		

	}
	
}

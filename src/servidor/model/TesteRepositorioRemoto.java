package servidor.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import base.model.Part;
import base.model.PartImplementation;

public class TesteRepositorioRemoto {


	
	public static void main (String... args)
	{
		TesteRepositorioRemoto tr = new TesteRepositorioRemoto();
		RepositorioRemoto repo = new RepositorioRemoto("repo11");
		RepositorioRemoto repo2 = new RepositorioRemoto("repo21");
		 
		repo.start();
		repo2.start();
		
		Part p = new PartImplementation("Modelo De Peca");
		Part p2 = new PartImplementation("Modelo De Peca");
		long codigo = 0;
		long codigo2 = 0;
		List<Long> codigos1 = new ArrayList<>();
		List<Long> codigos2 = new ArrayList<>();
			
			for(int i=0; i<10; i++) {
				p.setCode(0);
				p2.setCode(0);
				codigo = repo.addPart(p);
				codigo2 = repo2.addPart(p2);
				System.out.printf("Codigos: %d , %d\n",codigo,codigo2);
				System.out.printf("Peca: %d , %d\n", p.getCode(), p2.getCode());
			
				try(
						FileOutputStream fileOut = new FileOutputStream("c:\\desenvolvedor\\eclipse\\oxygenworkspace\\Projeto-Sd\\serialization.ser");
						FileOutputStream fileOut2 = new FileOutputStream("c:\\desenvolvedor\\eclipse\\oxygenworkspace\\Projeto-Sd\\serialization2.ser");
						ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
						ObjectOutputStream objOut2 = new ObjectOutputStream(fileOut2);
						) {
					objOut.writeObject(p);
					objOut2.writeObject(p2);
					System.out.println("Serializados");
				} catch (IOException e) {
				// TODO Auto-generated catch block
					System.err.println(e.toString());
				}
				
				try(FileInputStream fileIn = new FileInputStream("c:\\desenvolvedor\\eclipse\\oxygenworkspace\\Projeto-Sd\\serialization.ser");
						FileInputStream fileIn2 = new FileInputStream("c:\\desenvolvedor\\eclipse\\oxygenworkspace\\Projeto-Sd\\serialization2.ser");
						ObjectInputStream objIn = new ObjectInputStream(fileIn);
						ObjectInputStream objIn2 = new ObjectInputStream(fileIn2);
						) {
					Part ser = (Part) objIn.readObject();
					Part ser2 = (Part) objIn2.readObject();
					codigos1.add(ser.getCode());
					codigos2.add(ser2.getCode());
					System.out.printf("Deserializado: %s %s\n ",ser, ser2);
					System.out.printf("Código Desserializado: %d %d\n",ser.getCode(), ser2.getCode());
					
				} catch (IOException e) {
				// TODO Auto-generated catch block
					System.err.println(e.toString());
				} catch (ClassNotFoundException e) {
					System.err.println("Classe não encontrada");
					e.printStackTrace();
				}
				
				
				
			}
			
			System.out.println("Recuperando pecas do servidor.");
			Part recuperada = null;
			Part recuperada2 = null;
			for(int i= 0; i<10; i++) {
				if(i<codigos1.size()) {
					try {
						recuperada  = repo.getPart(codigos1.get(i));
						recuperada2 = repo.getPart(codigos2.get(i));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(recuperada);
					System.out.println(recuperada2);
				}
			}
				
				
		

		}
	
}

package cliente.model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

import base.model.Mensagens;
import base.model.Part;
import base.model.Part.Componente;
import base.model.PartImplementation;
import base.model.PartRepository;

public class ClientRMI extends AutoWiredCommands {

	private PartRepository repositorioRemoto;
	private Registry registry;
	private Part myPart, grabbed;
	private String repositorioAtual;
	
	public ClientRMI() {
		getRMIRegistry();
	}
	
	private void getRMIRegistry() {
		try {
			registry = LocateRegistry.getRegistry();
		} catch (RemoteException e) {
			System.err.println(Mensagens.CONEXAO_ERRO_LOCATE.texto);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	@Override
	public void connect(String repositorio) {
		repositorioAtual = repositorio;
		try {
			repositorioRemoto = (PartRepository) registry.lookup(repositorio);
			System.out.println(Mensagens.CONEXAO_SUCESSO.texto);
		} catch (NotBoundException e) {
			System.err.println(Mensagens.CONEXAO_ERRO_LOOKUP_NOT_BOUND.texto+repositorio);
			
		} catch (RemoteException e) {
			System.err.println(Mensagens.CONEXAO_ERRO_REMOTE.texto+repositorio);
			System.err.println(e.toString());
		}
	}
	
	@Override
	public void repoList() {
		final String[] repositorios;
		System.out.println("== Obtendo lista de servidores ==");
		try {
			 repositorios = registry.list();
			 System.out.println(util.StringUtils.arrayToPrintableList(repositorios));
		} catch (RemoteException e) {
			System.err.println(Mensagens.REPO_LIST_ERRO_REMOTE.texto);
			System.err.println(e.toString());
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	@Override
	public void newPart(String nome) {
		myPart = new PartImplementation(nome);
		System.out.println(Mensagens.NEW_PART_PARTE_CRIADA.texto+" "+nome);
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
		
	}
	
	@Override
	public void myPart() {
		if(validaMyPart()) {
		String formato = "Parte Sob Edição: \n "
				+ "Código: %s\n "
				+ "Nome: %s\n "
				+ "Descrição: %s\n "
				+ "Componentes: %s\n ";
		String codigo = Mensagens.MY_PART_CODIGO_VALUE.texto;
		
		StringBuilder sbComponentes = new StringBuilder();
		for(Componente c : myPart.listComponents())
		{
			sbComponentes.append(c.toString()+"\n");
		}
		System.out.printf(formato, codigo, myPart.getNome(),
											myPart.getDescricao(),
											sbComponentes.toString());
		
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	private boolean validaMyPart() { 
		if(myPart == null)
		{
			System.out.println(Mensagens.MY_PART_ERRO_NAO_CRIADA.texto);
			return false;
		}
		return true;
	}
	private boolean validaRepositorioRemoto() { 
		if(repositorioRemoto == null)
		{
			System.out.println(Mensagens.REMOTE_REPO_ERRO_NAO_CONECTADO.texto);
			return false;
		}
		return true;
	}
	
	@Override public void repoListParts() { 
		Map<Part, Integer> mapaPartes = null;
		if(validaRepositorioRemoto()){
			try {
				mapaPartes = repositorioRemoto.getComponentsMap();
			} catch (RemoteException e) {
				// TODO Melhorar mensagem de erro.
				System.out.println(Mensagens.ERROR_REMOTE_EXCEPTION.texto);

			}
			StringBuilder sb = new StringBuilder("Listando Parts no Repositório\n");
			if(mapaPartes != null && mapaPartes.size()>0) {
				for( Part p : mapaPartes.keySet()) {
					
					String formato = "%s [cód %d] - %d unidade(s)\n"; 
					sb.append(String.format(formato,  p.getNome(),
														p.getCode(),
														mapaPartes.get(p)));
				
				}
			}
			System.out.println(sb.toString());
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	
	@Override public void setDescricao(String desc) {
		if(validaMyPart()) {
			myPart.setDescricao(desc);
			System.out.println("Alterada Descrição para "+desc);
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
		}
	
	
	@Override public void getDescricao() {
		if(validaMyPart()) {
			System.out.println("Descrição de my-part: ");
			System.out.println(myPart.getDescricao());
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	
	@Override public void grab(String code) { 
		if(validaRepositorioRemoto()) {
			try {
				Part p = repositorioRemoto.getPart(Long.parseLong(code));
				System.out.printf("Selecionando peca %s (cód %d)\n", p.getNome(), p.getCode());
				System.out.println(Mensagens.GRAB_READ_TO_ADD.texto);
			} catch (RemoteException e) {
					System.out.println(Mensagens.ERROR_REMOTE_EXCEPTION.texto);
			} catch (java.lang.NumberFormatException ne) {
				System.out.println(Mensagens.GRAB_ERROR_INFORMADO_TEXTO.texto);
			}
			
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	@Override public void addToMyPart() { 
		if(grabbed == null)
		{
			System.out.println(Mensagens.ADD_2_MYPART_ERRO_NOT_GRABBED.texto);
			return;
		}
		if(validaMyPart()) {
			myPart.addComponent(grabbed);
			System.out.printf("Peça %s adicionada à my-part\n",grabbed.getNome());
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	@Override public void addToMyPart(String code) {
		if(validaMyPart() && validaRepositorioRemoto()) {
			try {
				Part p = repositorioRemoto.getPart(Integer.parseInt(code));
				myPart.addComponent(p);
				System.out.printf("Peça %s adicionada à my-part\n",p.getNome());
			} catch (RemoteException e) {
				System.err.println(Mensagens.CONEXAO_ERRO_REMOTE.texto+": "+e.toString());
			}
		}
	System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	@Override public void repoAdd() {
		if(validaMyPart() && validaRepositorioRemoto()) {
			try {
				long codigo = repositorioRemoto.addPart(myPart);
				System.out.printf("Peça %s adicionada ao repositório %s com código %d\n",myPart.getNome(), repositorioRemoto.getName(), codigo);
			} catch (RemoteException e) {
				System.err.println(Mensagens.CONEXAO_ERRO_REMOTE.texto+": "+e.toString());
			}
		}
	}
}

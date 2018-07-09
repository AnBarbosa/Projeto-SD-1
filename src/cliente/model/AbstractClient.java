package cliente.model;

import java.rmi.RemoteException;
import java.util.Map;

import base.model.Mensagens;
import base.model.Part;
import base.model.PartImplementation;
import base.model.PartRepository;
import base.model.Part.Componente;
import view.AutoWiredCommands;

public abstract class AbstractClient extends AutoWiredCommands {
	
	protected Part myPart, grabbed;
	protected String repositorioAtual;
	protected PartRepository repositorioRemoto;
	protected DAO dao;
	
	public AbstractClient() {
		dao = new DAO();
		prepareRepositories();
	}
	
	// Esses métodos devem ser sobrescritos a fim de interagir com os repositórios
	protected abstract void prepareRepositories();
	public abstract void connect(String repositorio);
	public abstract void repoList();
	
	// Os métodos abaixo representam as principais funções que valem para todos
	// os clientes.
	
	@Override
	public void newPart(String nome) {
		myPart = new PartImplementation(nome);
		System.out.println(Mensagens.NEW_PART_PARTE_CRIADA.texto+" "+nome);
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
		
	}
	
	@Override
	public void myPart() {
		if(myPartValida()) {
			String formato = "Parte Sob Edição: \n "
					+ "Nome: %s\n "
					+ "Código: %s\n "
					+ "Repositorio: %s\n "
					+ "Descrição: %s\n "
					+ "Componentes: %s\n ";
			
			StringBuilder sbComponentes = new StringBuilder();
			for(Componente c : myPart.listComponents())
			{
				sbComponentes.append(c.toString()+"\n");
			}
			System.out.printf(formato, 
										myPart.getNome(),
										myPart.getCode(), 
										myPart.getRepositorioDeOrigem(),
										myPart.getDescricao(),
										sbComponentes.toString());
			
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	private boolean myPartValida() { 
		if(myPart == null)
		{
			System.out.println(Mensagens.MY_PART_ERRO_NAO_CRIADA.texto);
			return false;
		}
		return true;
	}
	private boolean repositorioRemotoEhValido() { 
		if(dao.repositorioRemotoEhValido(repositorioRemoto)) {
			return true;
		} else {
			System.out.println(Mensagens.REMOTE_REPO_ERRO_NAO_CONECTADO.texto);
			return false;
		}
	}
	
	@Override public void repoListParts() {
		Map<Part, Integer> mapaPartes = dao.getMap();
		
		System.out.println("Listando Parts no Repositório "+repositorioAtual);

		StringBuilder sb = new StringBuilder();
		
		if(mapaPartes != null && mapaPartes.size()>0) {
			
			for( Part p : mapaPartes.keySet()) {
				
				String formato = "%s [cód %s] - %s unidade(s)\n"; 		
				sb.append(String.format(formato,  p.getNome(),
													p.getCode(),
													mapaPartes.get(p)));
			}
		} else {
			sb.append(Mensagens.REPOSITORIO_VAZIO.texto+"\n");
		}
		
		System.out.println(sb.toString());
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	
	@Override public void setDescricao(String desc) {
		if(myPartValida()) {
			myPart.setDescricao(desc);
			System.out.println("Alterada Descrição para "+desc);
		}
			System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
		}
	
	
	@Override public void getDescricao() {
		if(myPartValida()) {
			System.out.println("Descrição de my-part: ");
			System.out.println(myPart.getDescricao());
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	
	@Override public void grab(String code) { 
		if(repositorioRemotoEhValido()) {
			Part p = dao.getPart(code);
			if(p==null) {
				System.out.println(Mensagens.GRAB_PART_NOT_FOUND.texto);
			}
			
			System.out.printf("Selecionando peca %s (cód %s)\n", p.getNome(), p.getCode());
			grabbed = p;
			System.out.println(Mensagens.GRAB_READ_TO_ADD.texto);
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	@Override public void addToMyPart() { 
		if(grabbed == null)
		{
			System.out.println(Mensagens.ADD_2_MYPART_ERRO_NOT_GRABBED.texto);
			System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
			return;
		}
		if(myPartValida()) {
			myPart.addComponent(grabbed);
			System.out.printf("Peça %s adicionada à my-part\n",grabbed.getNome());
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	@Override public void addToMyPart(String code) {
		if(myPartValida() && repositorioRemotoEhValido()) {
			try {
				Part p = repositorioRemoto.getPart(code);
				myPart.addComponent(p);
				System.out.printf("Peça %s adicionada à my-part\n",p.getNome());
			} catch (RemoteException e) {
				System.err.println(Mensagens.CONEXAO_ERRO_REMOTE.texto+": "+e.toString());
			}
		}
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	@Override public void repoAdd() {
		if(myPartValida() && repositorioRemotoEhValido()) {
			try {
				String codigo = repositorioRemoto.addPart(myPart);
				System.out.printf("Peça %s adicionada ao repositório %s com código %s\n",myPart.getNome(), repositorioRemoto.getName(), codigo);
				myPart = (Part) repositorioRemoto.getPart(codigo).clone();
				
			} catch (RemoteException e) {
				System.err.println(Mensagens.CONEXAO_ERRO_REMOTE.texto+": "+e.toString());
			}
		} 
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
		
	}
	
	
}

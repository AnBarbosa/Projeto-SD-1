package cliente.view;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import base.model.interfaces.PartRepository;
import util.Mensagens;
import util.MsgUtils;

/** Essa classe implementa os métodos preparaRepositories(),
 * connect(String repositorio) e repoList(), que interagem diretamente
 * com os repositórios e configuram a repositorioDAO.
 * 
 *   A repositorioDAO é utilizada pela superclasse AbstractClientView 
 * para implementar todas as outras funções às quais o usuário tem acesso, 
 * de maneira transparente ao tipo de repositório utilizado.
 *  
 * @author André Barbosa
 *
 */
public class ClienteRMI extends AbstractClientView {

	
	private Registry registry;
	
	/** Esse método obtem uma referência ao RMIREGISTRY
	 *  e a insere no campo de classe registry.  
	 */
	@Override
	protected void prepareRepositories() {
		try {
			registry = LocateRegistry.getRegistry();
		} catch (RemoteException e) {
			MsgUtils.errorPrintln(Mensagens.CONEXAO_ERRO_LOCATE.texto);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Esse método configura o repositoryDAO, obtendo a referência 
	 * ao repositório remoto através do RMIRegistry
	 */
	@Override
	public void connect(String repositorio) {
		repositorioAtual = repositorio;
		
		try {
			repositorioRemoto = (PartRepository) registry.lookup(repositorio);
			MsgUtils.println("Adicionado "+ repositorioRemoto.getName());
			MsgUtils.println(Mensagens.CONEXAO_SUCESSO.texto);
		} catch (NotBoundException e) {
			MsgUtils.errorPrintln(Mensagens.CONEXAO_ERRO_LOOKUP_NOT_BOUND.texto+repositorio);
			
		} catch (java.rmi.ConnectException conE) {
			MsgUtils.errorPrintln(Mensagens.CONEXAO_BOUND_MAS_NAO_CONECTADO.texto);
		} catch (RemoteException e) {
		
			MsgUtils.errorPrintln(Mensagens.CONEXAO_ERRO_REMOTE.texto+" "+repositorio);
			MsgUtils.errorPrintln(e.toString());
		}
		
		// Após a conexão com o repositório, setamos o repositorioDAO.
		repositoryDAO.setRepositorio(repositorioRemoto);
		
		MsgUtils.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	/** Essa função imprime na tela todos os repositorios que estão
	 * registrados na rmiregistry.
	 */
	@Override
	public void repoList() {
		final String[] repositorios;
		MsgUtils.println("== Obtendo lista de repositórios ==");
		try {
			 repositorios = registry.list();
			 MsgUtils.println(util.StringUtils.arrayToPrintableList(repositorios));
		} catch (RemoteException e) {
			MsgUtils.errorPrintln(Mensagens.REPO_LIST_ERRO_REMOTE.texto);
			MsgUtils.errorPrintln(e.toString());
		}
		MsgUtils.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}


}

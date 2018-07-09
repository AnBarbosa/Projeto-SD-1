package cliente.view;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import base.model.interfaces.PartRepository;
import cliente.view.auxiliar.Mensagens;

/** Essa classe implementa os métodos preparaRepositories(),
 * connect(String servidor) e repoList(), que interagem diretamente
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
			System.err.println(Mensagens.CONEXAO_ERRO_LOCATE.texto);
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
			System.out.println(Mensagens.CONEXAO_SUCESSO.texto);
		} catch (NotBoundException e) {
			System.err.println(Mensagens.CONEXAO_ERRO_LOOKUP_NOT_BOUND.texto+repositorio);
			
		} catch (RemoteException e) {
			System.err.println(Mensagens.CONEXAO_ERRO_REMOTE.texto+repositorio);
			System.err.println(e.toString());
		}
		
		// Após a conexão com o repositório, setamos o repositorioDAO.
		repositoryDAO.setRepositorio(repositorioRemoto);
		
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	/** Essa função imprime na tela todos os servidores que estão
	 * registrados na rmiregistry.
	 */
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
	

}

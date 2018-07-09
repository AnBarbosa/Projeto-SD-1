package cliente.model;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import base.model.Mensagens;
import base.model.PartContainer;
import base.model.PartRepository;
import servidor.model.RemoteRepo;

public class ClientLocal extends AbstractClient {

	private Map<String, PartRepository> testeLocal;
		

	@Override
	protected void prepareRepositories()
	{
		testeLocal = new HashMap<>();
		testeLocal.put("remoteRepo", new RemoteRepo("remoteRepo"));
		testeLocal.put("container", new PartContainer("container"));
	}
	
	@Override
	public void connect(String repositorio) {
		this.repositorioRemoto = testeLocal.get(repositorio);
		repositorioAtual = repositorio;
		this.dao.setRepositorio(repositorioRemoto);
		System.out.println(Mensagens.CONEXAO_SUCESSO.texto);
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	@Override
	public void repoList() {
		System.out.println("== Obtendo lista de servidores ==");
		final Set<String> repositorios = testeLocal.keySet();
		repositorios.forEach(System.out::println);
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
}


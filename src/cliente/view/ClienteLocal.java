package cliente.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import base.model.PartContainer;
import base.model.interfaces.PartRepository;
import cliente.view.auxiliar.Mensagens;
import servidor.model.RemoteRepo;

public class ClienteLocal extends AbstractClientView {

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
		this.repositoryDAO.setRepositorio(repositorioRemoto);
		System.out.println(Mensagens.CONEXAO_SUCESSO.texto);
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
	
	@Override
	public void repoList() {
		System.out.println("== Obtendo lista de repositórios ==");
		final Set<String> repositorios = testeLocal.keySet();
		repositorios.forEach(System.out::println);
		System.out.println(Mensagens.TOKEN_FIM_DE_FUNCAO.texto);
	}
}


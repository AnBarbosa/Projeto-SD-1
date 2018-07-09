# Projeto Sistemas Distribuidos

## Como Iniciar:
 Compilando com o eclipse, vá com o cmd até a pasta bin.

 Na pasta bin, execute o rmiregistry. No windows, você pode digitar start rmiregistry. 

 Da mesma pasta, execute o servidor. java servidor.model.StartServer <nomeDoServidor>

 Da mesma pasta, execute o cliente com java cliente.StartClient


 *OBS*: o sistema não funciona se o rmiregistry for executado fora da pasta raiz, considerando os arquivos .class e os pacotes. O rmiregistry tenta encontrar as classes seguindo a hierarquia de diretórios.


 
 ## Linha de Comando
O usuário pode interagir com o cliente utilizando comandos e comandos com um argumento. 

Ele pode obter a lista de comandos digitando help.


## Desenvolvimento
### UserInterfaceMethods
A interface UserInterfaceMethods é o ponto de onde o sistema tira os métodos disponíveis ao usuário.

Para renomear um comando, basta renomear o método existente (use uma IDE para renomear o de todas as dependencias também).

Para incluir um comando, basta incluir um novo método à essa interface e marcá-lo com a anotação @ToUser. 

Os comandos serão realizados pelor uma classe que extenda AutoWiredReceiver. Atualmente, está sendo utilizada a classe ClienteRMI, que é instanciada em StartClient. 

### ClienteRMI 
ClienteRMI é uma forma concreta da superclasse AbstractClientView.

A AbstractClientView pode ser usada como modelo para qualquer AutoWiredReceiver, e implementa a interface UserInterfaceMethods. Os únicos métodos abstratos são aqueles que exigem a conexão com a rmiregistry. Esses métodos são implementados pela ClienteRMI.
 
### ClienteLocal
ClienteLocal pode ser usado no lugar da ClienteRMI para testes, ele implementa o repositório no proprio cliente, e não faz chamadas pela registry.

### AbstractClientView
Essa classe contem a implementação da maioria dos métodos da UserInterfaceMethods, e atualmente é onde está a base da lógica do cliente. 

### AutoWiredReceiver e InformacoesSobreMetodos
São classes utilizadas para converter automáticamente a interface UserInterfaceMethods em comandos utilizaveis pelo usuário.

Classes que extendam a *AutoWiredReceiver* podem ser utilizadas no lugar da ClienteRMI na StartClient, e seus métodos serão chamados quando o usuário digitar
um dos comandos.


### Comandos e Métodos Exportados
Os comandos são interpretados pela classe ClienteController.

Um método exportado será exibido, junto com sua descrição, quando o usuário digitar 'help'.
 
Um método exportado é utilizado pelo usuário através da linha de comando.
Para invocá-lo, o cliente deve digitar o nome do método convertido de camelCase
para small-hifen.
    *Ex: o usuário chama o método myPart digitando* >> **my-part**
  
Se o método tiver algum parâmetro, esse deve ser informado logo após o comando, antes do usuário apertar enter.
    
    *Ex: >> **connect servidor** -> equivale a connect("servidor");*
 
Se o usuário quiser informar uma String com espaços, deve utilizar aspas duplas "
 	
	*Ex: >> set-descricao "Esse é um exemplo de descricao" *
  
OBS: Atualmente o ClienteController não trabalha bem com aspas. Ele irá manter as aspas quando entrar como argumento. Então, por exemplo, a descição acima seria equivalente a setDescrição("\"Esse é um exemplo de descricao\"").

*Avaliar a possibilidade de concatenar tudo automaticamente como um segundo argumento.*

### ClienteController
Essa classe recebe os inputs do usuário através de um Scanner, separa os comandos pelos espaços. 
Com o número de palavras ela verifica se é um comando sem argumentos, ou com um argumento.  Ela compara a primeira palavra com as tabelas de comandos existentes,
mostra uma mensagem de erro se o comando não for localizado na tabela correta, ou efetua o comando seja realizado.









## TODO
O que é possível fazer:
* Listar os repositórios disponíveis.
* Se conectar a diferentes repositórios.
* Listar todas as peças do repositório ao qual está conectado.
* Criar uma nova peça.
* Criar uma descrição para a peça.
* Adicionar peças já existentes à peça que está sendo editada. 
* Consultar as informações da peça que está editando.
* Adicionar a peça que está sendo editada ao repositório em que está conectado.
* Melhorar os nomes dos comandos.

O que falta fazer:
* Retirar peças da peça que está sendo editada.
*  Limpar a lista de subpeças atual
*  Adicionar à peça atual varias unidades de outra peça. ( ex.: Adicionar 4 rodas de uma vez a um carro)
*  Após obter uma peça, ver os detalhes dela.
	*  Examinar o nome e  a descrição da peça
	*  Verificar se ela é primitiva ou agragada
	*  Obter número de subcomponentes diretos e primitivos da peça
	*  listar suas subpeças



|Exemplo Professor  | Comando Atual |
|:-------------------| :--------------:|
|   bind   arg      |  connect arg  |
|   listp           |  repo-list    |
|   getp            |  grab         |
|   showp           |  my-part      |
|   clearlist       |   x          |
|   addsubpart      |  my-part-add  |
|   addp            |   repo-add    |
|   quit            | quit / exit   |

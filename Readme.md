# Projeto Sistemas Distribuidos



O que é possível fazer:
* Listar os repositórios disponíveis.
* Se conectar a diferentes repositórios.
* Listar todas as peças do repositório ao qual está conectado.
* Criar uma nova peça.
* Criar uma descrição para a peça.
* Adicionar peças já existentes à peça que está sendo editada. 
* Consultar as informações da peça que está editando.
* Adicionar a peça que está sendo editada ao repositório em que está conectado.
* 

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
|-------------------| --------------|
|   bind   arg      |  connect arg  |
|   listp           |  repo-list    |
|   getp            |  grab         |
|   showp           |  my-part      |
|   clearlist       |   xx          |
|   addsubpart      |  my-part-add  |
|   addp            |   repo-add    |
|   quit            | quit / exit   |

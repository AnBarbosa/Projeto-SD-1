package cliente.interfaces.anotacoes;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;




/**
 *   Essa anotação é utilizada pela AutoWiredReceiver para exportar um método da
 * interface UserInterfaceMethods e permitir que o usuário o chame da linha de
 * comando.
 * 
 *   Um método exportado será exibido, junto com sua descrição, quando o usuário
 * digitar 'help'.
 * 
 *   Um método exportado é utilizado pelo usuário através da linha de comando.
 * Para invocá-lo, o cliente deve digitar o nome do método convertido de camelCase
 * para small-hifen.
 *       Ex: o usuário chama o método myPart digitando >> my-part.
 *   
 *   Se o método tiver algum parâmetro, esse deve ser informado logo após o comando,
 * antes do usuário apertar enter.
 *       Ex: >> connect repositorio -> equivale a connect("repositorio"); 
 *  
 *    Se o usuário quiser informar uma String com espaços, deve utilizar aspas
 *  duplas "
 *  	Ex: >> set-descricao "Esse é um exemplo de descricao"
 *  
 * 
 * 
 * @author CooperMind
 *
 */
@Retention(RUNTIME)
@Target(METHOD) @Inherited
public @interface ToUser {
	String parametro() default "sem informação sobre os argumentos";
	String descricao() default "adicione uma descricao";
}

package cliente.abstracoes;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD) @Inherited
public @interface ToUser {
	String parametro() default "sem informação sobre os argumentos";
	String descricao() default "adicione uma descricao";
}

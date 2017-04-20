/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	literalInt:expresion -> valor:int

public class LiteralInt extends AbstractExpresion {

	public LiteralInt(int valor) {
		this.valor = valor;
	}

	public LiteralInt(Object valor) {
		this.valor = (valor instanceof Token) ? Integer.parseInt(((Token)valor).getLexeme()) : (Integer) valor;

		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "LiteralInt [valor=" + valor + "]";
	}

	private int valor;
}


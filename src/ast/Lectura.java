/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	lectura:sentencia -> exprLectura:expresion

public class Lectura extends AbstractSentencia {

	public Lectura(Expresion exprLectura) {
		this.exprLectura = exprLectura;
		System.out.println(exprLectura);
		searchForPositions(exprLectura);	// Obtener linea/columna a partir de los hijos
	}

	public Lectura(Object exprLectura) {
		this.exprLectura = (Expresion) exprLectura;

		searchForPositions(exprLectura);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExprLectura() {
		return exprLectura;
	}
	public void setExprLectura(Expresion exprLectura) {
		this.exprLectura = exprLectura;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "Lectura [exprLectura=" + exprLectura + "]";
	}

	private Expresion exprLectura;
}


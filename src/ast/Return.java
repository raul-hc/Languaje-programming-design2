/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	return:sentencia -> exprRetorno:expresion

public class Return extends AbstractSentencia {

	public Return(Expresion exprRetorno) {
		this.exprRetorno = exprRetorno;

		searchForPositions(exprRetorno);	// Obtener linea/columna a partir de los hijos
	}

	public Return(Object exprRetorno) {
		this.exprRetorno = (Expresion) exprRetorno;

		searchForPositions(exprRetorno);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExprRetorno() {
		return exprRetorno;
	}
	public void setExprRetorno(Expresion exprRetorno) {
		this.exprRetorno = exprRetorno;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion exprRetorno;
}


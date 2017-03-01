/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	escritura:sentencia -> exprEscritura:expresion  tipoEscritura:String

public class Escritura extends AbstractSentencia {

	public Escritura(Expresion exprEscritura, String tipoEscritura) {
		this.exprEscritura = exprEscritura;
		this.tipoEscritura = tipoEscritura;

		searchForPositions(exprEscritura);	// Obtener linea/columna a partir de los hijos
	}

	public Escritura(Object exprEscritura, Object tipoEscritura) {
		this.exprEscritura = (Expresion) exprEscritura;
		this.tipoEscritura = (tipoEscritura instanceof Token) ? ((Token)tipoEscritura).getLexeme() : (String) tipoEscritura;

		searchForPositions(exprEscritura, tipoEscritura);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExprEscritura() {
		return exprEscritura;
	}
	public void setExprEscritura(Expresion exprEscritura) {
		this.exprEscritura = exprEscritura;
	}

	public String getTipoEscritura() {
		return tipoEscritura;
	}
	public void setTipoEscritura(String tipoEscritura) {
		this.tipoEscritura = tipoEscritura;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion exprEscritura;
	private String tipoEscritura;
}


/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	cast:expresion -> tipoDestino:tipo  expresionAConvertir:expresion

public class Cast extends AbstractExpresion {

	public Cast(Tipo tipoDestino, Expresion expresionAConvertir) {
		this.tipoDestino = tipoDestino;
		this.expresionAConvertir = expresionAConvertir;

		searchForPositions(tipoDestino, expresionAConvertir);	// Obtener linea/columna a partir de los hijos
	}

	public Cast(Object tipoDestino, Object expresionAConvertir) {
		this.tipoDestino = (Tipo) tipoDestino;
		this.expresionAConvertir = (Expresion) expresionAConvertir;

		searchForPositions(tipoDestino, expresionAConvertir);	// Obtener linea/columna a partir de los hijos
	}

	public Tipo getTipoDestino() {
		return tipoDestino;
	}
	public void setTipoDestino(Tipo tipoDestino) {
		this.tipoDestino = tipoDestino;
	}

	public Expresion getExpresionAConvertir() {
		return expresionAConvertir;
	}
	public void setExpresionAConvertir(Expresion expresionAConvertir) {
		this.expresionAConvertir = expresionAConvertir;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Tipo tipoDestino;
	private Expresion expresionAConvertir;
}


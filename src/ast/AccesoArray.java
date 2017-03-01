/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	accesoArray:expresion -> array:expresion  posicion:expresion

public class AccesoArray extends AbstractExpresion {

	public AccesoArray(Expresion array, Expresion posicion) {
		this.array = array;
		this.posicion = posicion;

		searchForPositions(array, posicion);	// Obtener linea/columna a partir de los hijos
	}

	public AccesoArray(Object array, Object posicion) {
		this.array = (Expresion) array;
		this.posicion = (Expresion) posicion;

		searchForPositions(array, posicion);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getArray() {
		return array;
	}
	public void setArray(Expresion array) {
		this.array = array;
	}

	public Expresion getPosicion() {
		return posicion;
	}
	public void setPosicion(Expresion posicion) {
		this.posicion = posicion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion array;
	private Expresion posicion;
}


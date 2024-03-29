/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	variable:expresion -> nombre:String

public class Variable extends AbstractExpresion {

	public Variable(String nombre) {
		this.nombre = nombre;
	}

	public Variable(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;

		searchForPositions(nombre);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public DefVariable getDefinicion() {
		return definicion;
	}
	public void setDefinicion(DefVariable definicion) {
		this.definicion = definicion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "Variable [nombre=" + nombre + "]";
	}

	private String nombre;
	private DefVariable definicion;
}


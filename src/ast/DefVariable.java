/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	defVariable:definicion -> tipo:tipo  nombre:String  ambito:int

public class DefVariable extends AbstractDefinicion {

	public DefVariable(Tipo tipo, String nombre, int ambito) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.ambito = ambito;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public DefVariable(Object tipo, Object nombre, Object ambito) {
		this.tipo = (Tipo) tipo;
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.ambito = (ambito instanceof Token) ? Integer.parseInt(((Token)ambito).getLexeme()) : (Integer) ambito;

		searchForPositions(tipo, nombre, ambito);	// Obtener linea/columna a partir de los hijos
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAmbito() {
		return ambito;
	}
	public void setAmbito(int ambito) {
		this.ambito = ambito;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Tipo tipo;
	private String nombre;
	private int ambito;
}


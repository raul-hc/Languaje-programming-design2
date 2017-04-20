/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.Visitor;

//	defVariable:definicion -> tipo:tipo  nombre:String

public class DefVariable extends AbstractDefinicion {

	public DefVariable(Tipo tipo, String nombre, ContextoVariable contextoVariable) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.contextoVariable = contextoVariable;
		
		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public DefVariable(Object tipo, Object nombre) {
		this.tipo = (Tipo) tipo;
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;

		searchForPositions(tipo, nombre);	// Obtener linea/columna a partir de los hijos
	}

	public DefVariable(Object tipo, Object nombre, ContextoVariable contextoVariable) {
		this.tipo = (Tipo) tipo;
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.contextoVariable = contextoVariable;
		
		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
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
	
	public int getDireccion() {
		return direccion;
	}
	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
	
	public ContextoVariable getContextoVariable() {
		return contextoVariable;
	}
	public void setContextoVariable(ContextoVariable contextoVariable) {
		this.contextoVariable = contextoVariable;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "DefVariable [tipo=" + tipo + ", nombre=" + nombre + ", direccion=" + direccion + ", contextoVariable="
				+ contextoVariable + "]";
	}


	private Tipo tipo;
	private String nombre;
	private int direccion;
	private ContextoVariable contextoVariable;
	
}


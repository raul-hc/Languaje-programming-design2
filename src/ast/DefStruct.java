/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	defStruct:definicion -> nombre:String  campos:defCampo*

public class DefStruct extends AbstractDefinicion {

	public DefStruct(String nombre, List<DefCampo> campos) {
		this.nombre = nombre;
		this.campos = campos;

		searchForPositions(campos);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefStruct(Object nombre, Object campos) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.campos = (List<DefCampo>) campos;

		searchForPositions(nombre, campos);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DefCampo> getCampos() {
		return campos;
	}
	public void setCampos(List<DefCampo> campos) {
		this.campos = campos;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "DefStruct [nombre=" + nombre + ", campos=" + campos + "]";
	}

	private String nombre;
	private List<DefCampo> campos;
}


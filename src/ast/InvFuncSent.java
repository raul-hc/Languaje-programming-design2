/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	invFuncSent:sentencia -> nombreFuncion:String  parametros:expresion*

public class InvFuncSent extends AbstractSentencia {

	public InvFuncSent(String nombreFuncion, List<Expresion> parametros) {
		this.nombreFuncion = nombreFuncion;
		this.parametros = parametros;

		searchForPositions(parametros);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public InvFuncSent(Object nombreFuncion, Object parametros) {
		this.nombreFuncion = (nombreFuncion instanceof Token) ? ((Token)nombreFuncion).getLexeme() : (String) nombreFuncion;
		this.parametros = (List<Expresion>) parametros;

		searchForPositions(nombreFuncion, parametros);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombreFuncion() {
		return nombreFuncion;
	}
	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	}

	public List<Expresion> getParametros() {
		return parametros;
	}
	public void setParametros(List<Expresion> parametros) {
		this.parametros = parametros;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "InvFuncSent [nombreFuncion=" + nombreFuncion + ", parametros="
				+ parametros + "]";
	}

	private String nombreFuncion;
	private List<Expresion> parametros;
}


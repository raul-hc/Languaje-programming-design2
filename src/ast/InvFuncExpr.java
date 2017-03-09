/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	invFuncExpr:expresion -> nombreFuncion:String  parametros:expresion*

public class InvFuncExpr extends AbstractExpresion {

	public InvFuncExpr(String nombreFuncion, List<Expresion> parametros) {
		this.nombreFuncion = nombreFuncion;
		this.parametros = parametros;

		searchForPositions(parametros);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public InvFuncExpr(Object nombreFuncion, Object parametros) {
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
		return "InvFuncExpr [nombreFuncion=" + nombreFuncion + ", parametros="
				+ parametros + "]";
	}

	private String nombreFuncion;
	private List<Expresion> parametros;
}


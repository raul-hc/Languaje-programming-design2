/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	while:sentencia -> condicion:expresion  sentenciasWhile:sentencia*

public class While extends AbstractSentencia {

	public While(Expresion condicion, List<Sentencia> sentenciasWhile) {
		this.condicion = condicion;
		this.sentenciasWhile = sentenciasWhile;

		searchForPositions(condicion, sentenciasWhile);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public While(Object condicion, Object sentenciasWhile) {
		this.condicion = (Expresion) condicion;
		this.sentenciasWhile = (List<Sentencia>) sentenciasWhile;

		searchForPositions(condicion, sentenciasWhile);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getCondicion() {
		return condicion;
	}
	public void setCondicion(Expresion condicion) {
		this.condicion = condicion;
	}

	public List<Sentencia> getSentenciasWhile() {
		return sentenciasWhile;
	}
	public void setSentenciasWhile(List<Sentencia> sentenciasWhile) {
		this.sentenciasWhile = sentenciasWhile;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "While [condicion=" + condicion + ", sentenciasWhile="
				+ sentenciasWhile + "]";
	}

	private Expresion condicion;
	private List<Sentencia> sentenciasWhile;
}


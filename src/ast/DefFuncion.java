/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	defFuncion:definicion -> nombre:String  parametros:defVariable*  tipoRetorno:tipo  variableLocales:defVariable*  sentencias:sentencia*

public class DefFuncion extends AbstractDefinicion {

	public DefFuncion(String nombre, List<DefVariable> parametros, Tipo tipoRetorno, List<DefVariable> variableLocales, List<Sentencia> sentencias) {
		this.nombre = nombre;
		this.parametros = parametros;
		this.tipoRetorno = tipoRetorno;
		this.variableLocales = variableLocales;
		this.sentencias = sentencias;

		searchForPositions(parametros, tipoRetorno, variableLocales, sentencias);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefFuncion(Object nombre, Object parametros, Object tipoRetorno, Object variableLocales, Object sentencias) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.parametros = (List<DefVariable>) parametros;
		this.tipoRetorno = (Tipo) tipoRetorno;
		this.variableLocales = (List<DefVariable>) variableLocales;
		this.sentencias = (List<Sentencia>) sentencias;

		searchForPositions(nombre, parametros, tipoRetorno, variableLocales, sentencias);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DefVariable> getParametros() {
		return parametros;
	}
	public void setParametros(List<DefVariable> parametros) {
		this.parametros = parametros;
	}

	public Tipo getTipoRetorno() {
		return tipoRetorno;
	}
	public void setTipoRetorno(Tipo tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}

	public List<DefVariable> getVariableLocales() {
		return variableLocales;
	}
	public void setVariableLocales(List<DefVariable> variableLocales) {
		this.variableLocales = variableLocales;
	}

	public List<Sentencia> getSentencias() {
		return sentencias;
	}
	public void setSentencias(List<Sentencia> sentencias) {
		this.sentencias = sentencias;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<DefVariable> parametros;
	private Tipo tipoRetorno;
	private List<DefVariable> variableLocales;
	private List<Sentencia> sentencias;
}


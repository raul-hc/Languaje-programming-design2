package semantico;

import java.util.*;

import ast.*;
import main.*;
import visitor.*;


public class Identificacion extends DefaultVisitor {

	public Identificacion(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	
	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo tipoRetorno;  List<DefVariable> variableLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {

			DefFuncion defFunc = functions.get(node.getNombre());
			predicado(defFunc == null, "Función repetida: " + node.getNombre(), node.getStart());
			functions.put(node.getNombre(), node);
			
		variables.set();
			visitChildren(node.getParametros(), param);
			node.getTipoRetorno().accept(this, param);
			visitChildren(node.getVariableLocales(), param);
			visitChildren(node.getSentencias(), param);
		variables.reset();
		
		return null;
	}

	//	class InvFuncExpr { String nombreFuncion;  List<Expresion> parametros; }
	public Object visit(InvFuncExpr node, Object param) {

		DefFuncion defFunc = functions.get(node.getNombreFuncion());
		predicado(defFunc != null, "Función no definida: " + node.getNombreFuncion() , node.getStart());
		node.setDefinicion(defFunc);
		
		// Tenemos que visitar los parametros
		if (node.getParametros() != null){
			for (Expresion parametro : node.getParametros()){
				parametro.accept(this, node);
			}
		}
		
		return null;
	}
	
	//	class InvFuncSent { String nombreFuncion;  List<Expresion> parametros; }
	public Object visit(InvFuncSent node, Object param) {

		DefFuncion defFunc = functions.get(node.getNombreFuncion());
		predicado(defFunc != null, "Función (PROCEDIMIENTO) no definida: " + node.getNombreFuncion(), node.getStart());
		node.setDefinicion(defFunc);

		// Tenemos que visitar los parametros
		if (node.getParametros() != null){
			for (Expresion parametro : node.getParametros()){
				parametro.accept(this, node);
			}
		}
		
		return null;
	}

	
	//	class DefStruct { String nombre;  List<DefCampo> campos; }
	public Object visit(DefStruct node, Object param) {
		
		DefStruct defStruct = structs.get(node.getNombre());
		predicado(defStruct == null, "Estructura repetida: " + node.getNombre(), node.getStart());
		structs.put(node.getNombre(), node);
		
		campos = new HashMap<String, DefCampo>();
		
		visitChildren(node.getCampos(), param);
		
		return null;
	}
	
	//	class TipoIdent { String tipo; }
	public Object visit(TipoStruct node, Object param) {
		
		DefStruct defStruct = structs.get(node.getTipo());
		 
		if (defStruct == null)
			predicado(true, "Estructura no definida: " + node.getTipo(), node.getStart());
		else
			node.setDefinicionEstructura(defStruct);// Enlazar referencia con estructura
		
		return null;
	}

	//	class DefCampo { String nombre;  Tipo tipo; }
	public Object visit(DefCampo node, Object param) {
	
		DefCampo defcampo = campos.get(node.getNombre());
		predicado(defcampo == null, "Campo repetido: "+node.getNombre(), node.getStart());
		campos.put(node.getNombre(), node);
		
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
			
		return null;
	}
		
//	class AccesoStruct { Expresion struct;  String nombreCampo; }
	public Object visit(AccesoStruct node, Object param) {
		
		if (node.getStruct() != null)
			node.getStruct().accept(this, param);
		
		return null;
	}
	
	
	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable node, Object param) {

		DefVariable definicion = variables.getFromTop(node.getNombre());
		predicado(definicion == null, "Variable repetida: " + node.getNombre(), node.getStart());
		variables.put(node.getNombre(), node);

		if (node.getTipo() != null) // Hay que visitar el tipo de la variable (Si TipoStruct se tiene que enlazar la defStruct con el acceso al Struct)
			node.getTipo().accept(this, param);
		
		return null;
	}

	
	//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		
		DefVariable definicionVarLocal = variables.getFromTop(node.getNombre());
		if (definicionVarLocal == null){
			DefVariable definicionVarGlobal = variables.getFromAny(node.getNombre());
			if (definicionVarGlobal == null){
				gestorErrores.error("Identificación", "Variable no definida: "+node.getNombre(), node.getStart());				
			} else {
				node.setDefinicion(definicionVarGlobal);
			}
		} else {
			node.setDefinicion(definicionVarLocal);
		}
		
		return null;
	}
	
	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la Gramática Atribuida.
	 * 
	 * Ejemplo de uso:
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", expr.getStart());
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de donde se leyó el nodo.
	 * Si se usa VGen dicho método será generado en todos los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion Debe cumplirse para que no se produzca un error
	 * @param mensajeError Se imprime si no se cumple la condición
	 * @param posicionError Fila y columna del fichero donde se ha producido el error. Es opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Identificación", mensajeError, posicionError);
	}


	private GestorErrores gestorErrores;
	
	private Map<String, DefStruct> structs = new HashMap<String, DefStruct>();
	private Map<String, DefCampo> campos = new HashMap<String, DefCampo>();
	private Map<String, DefFuncion> functions = new HashMap<String, DefFuncion>();
	private ContextMap<String, DefVariable> variables = new ContextMap<String, DefVariable>();
	
}

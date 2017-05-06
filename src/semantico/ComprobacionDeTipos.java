package semantico;

import java.util.List;
import ast.*;
import main.*;
import visitor.*;

public class ComprobacionDeTipos extends DefaultVisitor {

	public ComprobacionDeTipos(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo tipoRetorno;  List<DefVariable> variableLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {

		if (node.getParametros() != null){
			for (DefVariable defVar : node.getParametros()){
				predicado(esTipoSimple(defVar.getTipo()), "El parametro : " + defVar.getNombre() + " no es de tipo simple", defVar.getStart());
				defVar.accept(this, param);
			}
		}

		if (node.getTipoRetorno() != null) {
			node.getTipoRetorno().accept(this, param);
		}

		if (node.getVariableLocales() != null){
			for (DefVariable defVarLocal : node.getVariableLocales()){
				defVarLocal.accept(this, param);
			}
		}
			
		if (node.getSentencias() != null){
			for (Sentencia s : node.getSentencias()){
				s.setDefFuncion(node); // reglas semanticas R(p)
				s.accept(this, param);
			}
		}
		
		// predicados B(p)
		predicado(esTipoSimple(node.getTipoRetorno()), "El tipo de retorno de la funcion :" + node.getNombre() + " no es de tipo simple", node.getStart());

		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {

		super.visit(node, param);

		// predicados B(p)
		predicado(esTipoSimple(node.getLeft().getTipo()), "La expresion de la izda debe ser un tipo simple", node.getLeft().getStart());
		predicado(node.getLeft().getTipo().getClass() == node.getRight().getTipo().getClass(), "El tipo de la expresion izda debe ser el mismo tipo que el de la expresion dcha", node.getStart());
		predicado(node.getLeft().isModificable(), "La expresion izda tiene que ser un LValue", node.getLeft().getStart());
		 
		return null;
	}

	//	class Escritura { Expresion exprEscritura;  String tipoEscritura; }
	public Object visit(Escritura node, Object param) {

		if (node.getExprEscritura() != null){
			node.getExprEscritura().accept(this, param);
		}
			
		// predicados B(p)
		if (node.getExprEscritura().getTipo() == null){
			predicado(node.getExprEscritura().getTipo() != null, "La expresion no tiene tipo de retorno: ", node.getExprEscritura().getStart());
		} else {
			predicado(esTipoSimple(node.getExprEscritura().getTipo()), "La expresion a escribir tiene que ser de tipo simple: " + node.getExprEscritura().getTipo(), node.getExprEscritura().getStart());
		}

		return null;
	}

	//	class Lectura { Expresion exprLectura; }
	public Object visit(Lectura node, Object param) {

		if (node.getExprLectura() != null){
			node.getExprLectura().accept(this, param);
		}

		// predicados B(p)
		predicado(esTipoSimple(node.getExprLectura().getTipo()), "La expresion a leer tiene que ser de tipo simple: " + node.getExprLectura().getTipo(), node.getExprLectura().getStart());
		predicado(node.getExprLectura().isModificable(), "La expresion a leer tiene que ser un LValue: " + node.getExprLectura().toString(), node.getExprLectura().getStart());
		
		return null;
	}

	//	class Return { Expresion exprRetorno; }
	public Object visit(Return node, Object param) {

		if (node.getExprRetorno() != null){
			node.getExprRetorno().accept(this, param);
		}
		
		// predicados B(p)
		if (node.getExprRetorno() == null){
			/*  Un return puede ser de una funcion o bien de un ifelse o un while Return de una expresion: return ; --> node.getDefFuncion() == null  */
			if (node.getDefFuncion() != null){ // Return de una funcion: return x;
				predicado(node.getDefFuncion().getTipoRetorno() instanceof TipoVoid, "El tipo de la funcion deberia de ser TipoVoid para que la expresion del return fuese nula", node.getDefFuncion().getEnd());
			}
		} else { // node.getExprRetorno() is null
			predicado(node.getExprRetorno().getTipo().getClass() == node.getDefFuncion().getTipoRetorno().getClass(), 
					"El tipo de la expresion a retornar tiene que coincidir con el tipo de retorno de la funcion", 
					node.getExprRetorno().getStart());			
		}
		
		return null;
	}

	//	class Ifelse { Expresion condicion;  List<Sentencia> sentenciasIf;  List<Sentencia> sentenciasElse; }
	public Object visit(Ifelse node, Object param) {

		if (node.getCondicion() != null){
			node.getCondicion().accept(this, param);
		}
		
		// reglas semanticas R(p)
		if (node.getSentenciasElse() != null){
			for (Sentencia sIf : node.getSentenciasIf()){
				sIf.setDefFuncion(node.getDefFuncion());
				sIf.accept(this, param);
			}
		}
		
		if (node.getSentenciasElse() != null){
			for (Sentencia sElse : node.getSentenciasElse()){
				sElse.setDefFuncion(node.getDefFuncion());	
				sElse.accept(this, param);
			}
		}
		
		// predicados B(p)
		predicado(node.getCondicion().getTipo() instanceof TipoEntero, "El tipo de la condicion debe ser tipoEntero", node.getCondicion().getStart());

		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> sentenciasWhile; }
	public Object visit(While node, Object param) {

		if (node.getCondicion() != null){
			node.getCondicion().accept(this, param);
		}
				
		// reglas semanticas R(p)
		if (node.getSentenciasWhile() != null){
			for (Sentencia s : node.getSentenciasWhile()){
				s.setDefFuncion(node.getDefFuncion());
				s.accept(this, param);
			}
		}

		// predicados B(p)
		predicado(node.getCondicion().getTipo() instanceof TipoEntero, "El tipo de la condicion debe ser tipoEntero", node.getCondicion().getStart());

		return null;
	}

	//	class InvFuncSent { String nombreFuncion;  List<Expresion> parametros; }
	public Object visit(InvFuncSent node, Object param) {

		// predicados B(p)
		predicado( node.getParametros().size() == node.getDefinicion().getParametros().size() , "El numero de argumentos no concuerda con los de la definicion de la funcion " + node.getNombreFuncion(), node.getStart());
		
		if (node.getParametros() != null){
			for (int i = 0 ; i < node.getParametros().size() ; i++){
				try {
					node.getParametros().get(i).accept(this, param);
					predicado(node.getParametros().get(i).getTipo().getClass() == node.getDefinicion().getParametros().get(i).getTipo().getClass(), "El tipo de los argumentos no concuerda: "+ node.getDefinicion().getParametros().get(i).getNombre() + " " + node.getDefinicion().getParametros().get(i).getTipo(), node.getParametros().get(i).getStart());
				} catch (java.lang.IndexOutOfBoundsException iob) {
					// TODO - add desired behaviour
				}
			}
		}

		return null;
	}

	//	class ExpresionBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {

		if (node.getLeft() != null){
			node.getLeft().accept(this, param);
		}
		
		if (node.getRight() != null){
			node.getRight().accept(this, param);
		}

		// predicados B(p)
		predicado(node.getLeft().getTipo().getClass() == node.getRight().getTipo().getClass(), "Los tipos de las expresiones de la izda y de la dcha tienen que coincidir para la operacion: " + node.getOperador(), node.getStart());
		predicado(node.getLeft().getTipo() instanceof TipoEntero || node.getLeft().getTipo() instanceof TipoReal, "La operacion "+ node.getOperador() + " solo esta definida para el tipoEntero y el tipoReal", node.getLeft().getStart());
		
		
		// reglas semanticas R(p)
		node.setTipo(node.getLeft().getTipo());
		node.setModificable(false);
		
    	if (node.getOperador().equals(">=") || node.getOperador().equals("<=") 
			|| node.getOperador().equals("<") || node.getOperador().equals(">") 
			|| node.getOperador().equals("!=") || node.getOperador().equals("==")) {
    		// El tipo resultado de una de las operaciones anteriores siempre es entero ( 3.14 >= 2.5 -> TipoEntero )
    		node.setTipo(new TipoEntero()); 
    	}
		
		
		return null;
	}

	//	class ExpresionLogica { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionLogica node, Object param) {

		super.visit(node, param);

		// predicados B(p)
		predicado(node.getLeft().getTipo() instanceof TipoEntero && node.getRight().getTipo() instanceof TipoEntero, 
				"La expresion izda y la expr dcha tienen que ser de tipo TipoEntero", node.getLeft().getStart());
		
		// reglas semanticas R(p)
		node.setTipo(new TipoEntero());
				
		return null;
	}

	//	class ExpresionUnariaNegacion { Expresion expresion; }
	public Object visit(ExpresionUnariaNegacion node, Object param) {

		super.visit(node, param);

		// predicados B(p)
		predicado(node.getExpresion().getTipo() instanceof TipoEntero, "La negacion unaria solo esta permitida sobre el tipoEntero", node.getExpresion().getStart());

		// reglas semanticas R(p)
		node.setTipo(new TipoEntero()); //node.setTipo(node.getExpresion().getTipo());
		node.setModificable(false);
		
		return null;
	}

	//	class Cast { Tipo tipoDestino;  Expresion expresionAConvertir; }
	public Object visit(Cast node, Object param) {

		super.visit(node, param);

		// predicados B(p)    -    Importante: Necesitamos usar .getClass() para la comparacion 
		predicado(node.getExpresionAConvertir().getTipo().getClass() != node.getTipoDestino().getClass(), "El tipo de destino debe ser distinto al tipo actual de la expresion", node.getStart());
		predicado(esTipoSimple(node.getExpresionAConvertir().getTipo()), "El tipo de la expresion a convertir tiene que ser simple: " + node.getExpresionAConvertir().getTipo(), node.getStart());
		predicado(esTipoSimple(node.getTipoDestino()), "El tipo destino al que convertir una expresion tiene que ser simple", node.getStart());
		
		// reglas semanticas R(p)
		node.setTipo(node.getTipoDestino());
		node.setModificable(false);
		
		return null;
	}

	//	class AccesoArray { Expresion array;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {

		super.visit(node, param);

		// predicados B(p)
		predicado(node.getPosicion().getTipo() instanceof TipoEntero, "La posicion a la que se esta intentando acceder no es de tipoEntero: "+node.getPosicion().toString(), node.getPosicion().getStart());
		predicado(node.getArray().getTipo() instanceof TipoArray, "El tipo del array tiene que ser obligatoriamente tipoArray: "+node.getArray().getTipo(), node.getArray().getStart());
		
		// reglas semanticas R(p)
		if (node.getArray().getTipo() instanceof TipoArray){
			node.setTipo(((TipoArray) node.getArray().getTipo()).getTipo());
		} else {
			node.setTipo(node.getArray().getTipo());
		}

		node.setModificable(true); // v[3] = 4;
		
		return null;
	}

	//	class AccesoStruct { Expresion struct;  String nombreCampo; }
	public Object visit(AccesoStruct node, Object param) {

		super.visit(node, param);

		// predicados B(p)
		predicado(node.getStruct().getTipo() instanceof TipoStruct, "Una estructura tiene que ser obligatoriamente de TipoStruct", node.getStruct().getStart());

		if (!(node.getStruct().getTipo() instanceof TipoStruct))
			return null;
		
		List<DefCampo> campos = ((TipoStruct) node.getStruct().getTipo()).getDefinicionEstructura().getCampos();
		
		boolean campoExiste = false;
		
		for (DefCampo dc : campos) {
			if (dc.getNombre().equals(node.getNombreCampo())){
				campoExiste = true;
				node.setDefinicion(dc);
				node.setTipo(dc.getTipo());
			}
		}
		
		// reglas semanticas R(p)
		node.setModificable(true); // microsoft.numEmpleados = 43;

		// predicados B(p)
		predicado(campoExiste, "El campo al que intentas acceder no existe: "+ node.getNombreCampo(), node.getStart());
		
		return null;
	}

	//	class InvFuncExpr { String nombreFuncion;  List<Expresion> parametros; }
	public Object visit(InvFuncExpr node, Object param) {

		// predicados B(p)
		predicado( node.getParametros().size() == node.getDefinicion().getParametros().size() , 
				"El numero de argumentos no concuerda con los de la funcion " + node.getNombreFuncion(), 
				node.getStart());
		
		for (int i = 0 ; i < node.getParametros().size() ; i++){
			try {
				node.getParametros().get(i).accept(this, param);
				predicado(node.getParametros().get(i).getTipo().getClass() == node.getDefinicion().getParametros().get(i).getTipo().getClass(),	"El tipo de los argumentos no concuerda: "+ node.getDefinicion().getParametros().get(i).getNombre(), node.getParametros().get(i).getStart());
			} catch (java.lang.IndexOutOfBoundsException iob) {
				// TODO - add desired behaviour
			}
		}
	
		predicado(!(node.getDefinicion().getTipoRetorno() instanceof TipoVoid), "El tipo de retorno de la funcion no puede ser Void, tiene que devolver algo", node.getStart());
			
		// reglas semanticas R(p)
		node.setTipo(node.getDefinicion().getTipoRetorno()); // El tipo de una inv. como expresion es el tipo de retorno de la funcion a la que llamamos
		node.setModificable(false);
		
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		
//		System.out.println(node.getNombre());
		// reglas semanticas R(p)
		node.setTipo(node.getDefinicion().getTipo()); // El tipo de una variable es el tipo de su definición
		node.setModificable(true); // z = 7
		
		return null;
	}

	//	class LiteralInt { int valor; }
	public Object visit(LiteralInt node, Object param) {
		
		// reglas semanticas R(p)
		node.setTipo(new TipoEntero());
		
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		
		// reglas semanticas R(p)
		node.setTipo(new TipoReal());
		
		return null;
	}

	//	class LiteralCaracter { String valor; }
	public Object visit(LiteralCaracter node, Object param) {
		
		// reglas semanticas R(p)
		node.setTipo(new TipoCaracter());
		
		return null;
	}

	/**
	 * M�todo auxiliar opcional para ayudar a implementar los predicados de la Gram�tica Atribuida.
	 * 
	 * Ejemplo de uso (suponiendo implementado el m�todo "esPrimitivo"):
	 * 	predicado(esPrimitivo(expr.tipo), "La expresi�n debe ser de un tipo primitivo", expr.getStart());
	 * 	predicado(esPrimitivo(expr.tipo), "La expresi�n debe ser de un tipo primitivo", null);
	 * 
	 * NOTA: El m�todo getStart() indica la linea/columna del fichero fuente de donde se ley� el nodo.
	 * Si se usa VGen dicho m�todo ser� generado en todos los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion Debe cumplirse para que NO se produzca un error
	 * @param mensajeError Se imprime si no se cumple la condici�n
	 * @param posicionError Fila y columna del fichero donde se ha producido el error. Es opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Comprobacion de tipos", mensajeError, posicionError);
	}
	
	private boolean esTipoSimple(Tipo tipo){
		return (tipo instanceof TipoEntero || tipo instanceof TipoReal || tipo instanceof TipoCaracter || tipo instanceof TipoVoid);
	}
	
	
	private GestorErrores gestorErrores;
}

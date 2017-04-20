package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import ast.*;

import visitor.DefaultVisitor;

public class SeleccionDeInstrucciones extends DefaultVisitor {

	private PrintWriter writer;
	private String sourceFile;
	
	enum Funcion {
		DIRECCION, VALOR
	}
	
	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
	}
	
	
	//	class Programa { List<Definicion> definicion; }
	public Object visit(Programa node, Object param) {

		genera("#source \"" + sourceFile + "\"");
		genera("call main");
		genera("halt");
		
		super.visit(node, param);	// Recorrer los hijos

		return null;
	}

	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo tipoRetorno;  List<DefVariable> variableLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {

		// define[[parametrosi]]
		if (node.getParametros() != null)
			for (DefVariable child : node.getParametros())
				child.accept(this, param);
				
		if (node.getTipoRetorno() != null)
			node.getTipoRetorno().accept(this, param);

		//define[[variablesLocalesi]]
		if (node.getVariableLocales() != null)
			for (DefVariable child : node.getVariableLocales())
				child.accept(this, param);
		
		//ejecuta[[sentenciasi]]
		if (node.getSentencias() != null)
			for (Sentencia child : node.getSentencias())
				child.accept(this, param);
		
		int tamReturn = 0;
		int tamLocales = 0;
		int tamParametros = 0;
		
		genera("RET " + tamReturn + "," + tamLocales + "," + tamParametros);
		
		return null;
	}

	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable node, Object param) {

		if ( node.getContextoVariable() == ContextoVariable.GLOBAL) {
			genera("#VAR " + node.getNombre()); // + node.getTipoMAPL(node.getTipo);
		}
		
		return null;
	}

//	//	class DefStruct { String nombre;  List<DefCampo> campos; }
//	public Object visit(DefStruct node, Object param) {
//
//		// super.visit(node, param);
//
//		if (node.getCampos() != null)
//			for (DefCampo child : node.getCampos())
//				child.accept(this, param);
//
//		return null;
//	}

//	//	class DefCampo { String nombre;  Tipo tipo; }
//	public Object visit(DefCampo node, Object param) {
//
//		// super.visit(node, param);
//
//		if (node.getTipo() != null)
//			node.getTipo().accept(this, param);
//
//		return null;
//	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		genera("#LINE " + node.getLeft().getEnd());
		node.getLeft().accept(this, Funcion.DIRECCION); 	//visit(node.getLeft(), Funcion.DIRECCION);
		node.getRight().accept(this, Funcion.VALOR); 		//visit(node.getRight(), Funcion.VALOR);
		genera("store" + node.getLeft().getTipo().getSufijo());

		return null;
	}

	//	class Escritura { Expresion exprEscritura;  String tipoEscritura; }
	public Object visit(Escritura node, Object param) {

		genera("#LINE " + node.getEnd());
		node.getExprEscritura().accept(this, Funcion.VALOR); //visit(node.getExprEscritura(), Funcion.VALOR);
		genera("OUT" + node.getExprEscritura().getTipo().getSufijo());
		
		// TODO : Tipo de escritura; println, print, print con tabulacion
		
		return null;
	}

//	//	class Lectura { Expresion exprLectura; }
//	public Object visit(Lectura node, Object param) {
//
//		// super.visit(node, param);
//
//		if (node.getExprLectura() != null)
//			node.getExprLectura().accept(this, param);
//
//		return null;
//	}
//
//	//	class Return { Expresion exprRetorno; }
//	public Object visit(Return node, Object param) {
//
//		// super.visit(node, param);
//
//		if (node.getExprRetorno() != null)
//			node.getExprRetorno().accept(this, param);
//
//		return null;
//	}
//
//	//	class Ifelse { Expresion condicion;  List<Sentencia> sentenciasIf;  List<Sentencia> sentenciasElse; }
//	public Object visit(Ifelse node, Object param) {
//
//		// super.visit(node, param);
//
//		if (node.getCondicion() != null)
//			node.getCondicion().accept(this, param);
//
//		if (node.getSentenciasIf() != null)
//			for (Sentencia child : node.getSentenciasIf())
//				child.accept(this, param);
//
//		if (node.getSentenciasElse() != null)
//			for (Sentencia child : node.getSentenciasElse())
//				child.accept(this, param);
//
//		return null;
//	}
//
//	//	class While { Expresion condicion;  List<Sentencia> sentenciasWhile; }
//	public Object visit(While node, Object param) {
//
//		// super.visit(node, param);
//
//		if (node.getCondicion() != null)
//			node.getCondicion().accept(this, param);
//
//		if (node.getSentenciasWhile() != null)
//			for (Sentencia child : node.getSentenciasWhile())
//				child.accept(this, param);
//
//		return null;
//	}
//
//	//	class InvFuncSent { String nombreFuncion;  List<Expresion> parametros; }
//	public Object visit(InvFuncSent node, Object param) {
//
//		// super.visit(node, param);
//
//		if (node.getParametros() != null)
//			for (Expresion child : node.getParametros())
//				child.accept(this, param);
//
//		return null;
//	}

	//	class ExpresionBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {
				
		node.getLeft().accept(this, Funcion.DIRECCION); //visit(node.getLeft(), Funcion.DIRECCION);
		node.getRight().accept(this, Funcion.VALOR); 	//visit(node.getRight(), Funcion.VALOR);
				                                           	
		if (node.getOperador().equals("+")) {						// aritmeticos
			genera("add" + node.getLeft().getTipo().getSufijo()); 
		} else if (node.getOperador().equals("-")) {
			genera("sub" + node.getLeft().getTipo().getSufijo()); 
		} else if (node.getOperador().equals("*")) {
			genera("mul" + node.getLeft().getTipo().getSufijo()); 
		} else if (node.getOperador().equals("/")) {
			genera("div" + node.getLeft().getTipo().getSufijo()); 
		} else if (node.getOperador().equals(">")) {  				// relacionales
			genera("gt" + node.getLeft().getTipo().getSufijo()); 
		} else if (node.getOperador().equals(">=")) {
			genera("ge" + node.getLeft().getTipo().getSufijo()); 
		} else if (node.getOperador().equals("<")) {
			genera("lt" + node.getLeft().getTipo().getSufijo()); 
		} else if (node.getOperador().equals("<=")) {
			genera("le" + node.getLeft().getTipo().getSufijo()); 
		} else if (node.getOperador().equals("!=")) {
			genera("ne" + node.getLeft().getTipo().getSufijo()); 
		} else {
			if (node.getOperador().equals("==")) {
				genera("eq" + node.getLeft().getTipo().getSufijo()); 
			}
		}
		
		return null;
	}

	//	class ExpresionLogica { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionLogica node, Object param) {

		node.getLeft().accept(this, Funcion.DIRECCION); //visit(node.getLeft(), Funcion.DIRECCION);
		node.getRight().accept(this, Funcion.VALOR);	//visit(node.getRight(), Funcion.VALOR);
		
		if (node.getOperador().equals("&&")){
			genera("and");
		}
		
		if (node.getOperador().equals("||")){
			genera("or");
		}

		return null;
	}

	//	class ExpresionUnariaNegacion { Expresion expresion; }
	public Object visit(ExpresionUnariaNegacion node, Object param) {

		node.getExpresion().accept(this, Funcion.VALOR); // visit(node.getExpresion(), Funcion.VALOR);
		genera("not");
		
		return null;
	}

	//	class Cast { Tipo tipoDestino;  Expresion expresionAConvertir; }
	public Object visit(Cast node, Object param) {

		String s = "";
		
		s += node.getExpresionAConvertir().getTipo().getSufijo();
		s += "2"; 
		s += node.getTipoDestino().getSufijo();

		genera(s); //   i2b || b2i || f2i || i2f

		return null;
	}

	//	class AccesoArray { Expresion array;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {

		if ((Funcion) param == Funcion.DIRECCION) {
			
			// dir base del array
			node.getArray().accept(this, Funcion.DIRECCION); 	//visit(node.getArray(), Funcion.DIRECCION);
			node.getPosicion().accept(this, Funcion.VALOR); //visit(node.getPosicion(), Funcion.VALOR);
			genera("push " + node.getArray().getTipo().getSize());
			genera("mul");
			genera("add");
			
			return null;
		}
		
		if ((Funcion) param == Funcion.VALOR) {
			
			node.accept(this, Funcion.DIRECCION); 
			genera("LOAD" + node.getArray().getTipo().getSufijo());
			
			return null;
		}

		return null;
	}

	//	class AccesoStruct { Expresion struct;  String nombreCampo; }
	public Object visit(AccesoStruct node, Object param) {

		if ((Funcion) param == Funcion.DIRECCION) {
			
			List<DefCampo> campos = ((DefStruct) node.getStruct()).getCampos();
			for (DefCampo dc : campos){
				if(dc.getNombre().equals(node.getNombreCampo())){
					// visit(node.getStruct(), Funcion.DIRECCION)
					// dir base -> No necesaria porque un Struct solo puede ser una variable global por lo que la direccion que necesitamos es la del campo directamente					
					genera("push" + dc.getDireccion());
					genera("add");
					return null;				
				}
			}
		
		}
		
		if ((Funcion) param == Funcion.VALOR){
			
			visit(node, Funcion.DIRECCION);
			
			List<DefCampo> campos = ((DefStruct) node.getStruct()).getCampos();
			for (DefCampo dc : campos){
				if(dc.getNombre().equals(node.getNombreCampo())){
					genera("load" + dc.getTipo().getSufijo());
					return null;				
				}
			}
			
		}
		
		return null;
	}

//	//	class InvFuncExpr { String nombreFuncion;  List<Expresion> parametros; }
//	public Object visit(InvFuncExpr node, Object param) {
//
//		// super.visit(node, param);
//
//		if (node.getParametros() != null)
//			for (Expresion child : node.getParametros())
//				child.accept(this, param);
//
//		return null;
//	}

	//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		
		if ((Funcion) param == Funcion.DIRECCION) {
			
			if (node.getDefinicion().getContextoVariable() == ContextoVariable.GLOBAL) {
				
				genera("pusha " + node.getDefinicion().getDireccion());
		
			} else { // variable local o parametro -> tenemos que usar BP
				
				genera("pusha bp");
				genera("pusha " + node.getDefinicion().getDireccion()); 
				// Si es un parametro la direccion sera positiva
				// Si es una variable local la direccion sera negativa
				
			}
		}
		
		if ((Funcion) param == Funcion.VALOR){
			// NO TIENE SENTIDO COMPROBAR EL CONTEXTO (YA SE HACE EN DIRECCION)
			visit(node, Funcion.DIRECCION); // node.accept(node, Funcion.DIRECCION)
			genera("load"+ node.getDefinicion().getTipo().getSufijo());
		}
		
		return null;
	}

	//	class LiteralInt { int valor; }
	public Object visit(LiteralInt node, Object param) {
		
		genera("pushi " + node.getValor());
		
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		
		genera("pushf " + node.getValor());
		
		return null;
	}

	//	class LiteralCaracter { String valor; }
	public Object visit(LiteralCaracter node, Object param) {
		
		genera("pushb " + node.getValor());
		
		return null;
	}

	//	class TipoEntero {  }
	public Object visit(TipoEntero node, Object param) {
		return null;
	}

	//	class TipoReal {  }
	public Object visit(TipoReal node, Object param) {
		return null;
	}

	//	class TipoCaracter {  }
	public Object visit(TipoCaracter node, Object param) {
		return null;
	}

	//	class TipoArray { int tamano;  Tipo tipo; }
	public Object visit(TipoArray node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	//	class TipoIdent { String tipo; }
	public Object visit(TipoStruct node, Object param) {
		return null;
	}


	
	// Metodo auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}

	
}

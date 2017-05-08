package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;

import ast.AccesoArray;
import ast.AccesoStruct;
import ast.Asignacion;
import ast.Cast;
import ast.ContextoVariable;
import ast.DefCampo;
import ast.DefFuncion;
import ast.DefStruct;
import ast.DefVariable;
import ast.Definicion;
import ast.Escritura;
import ast.Expresion;
import ast.ExpresionBinaria;
import ast.ExpresionLogica;
import ast.ExpresionUnariaNegacion;
import ast.Ifelse;
import ast.InvFuncExpr;
import ast.InvFuncSent;
import ast.Lectura;
import ast.LiteralCaracter;
import ast.LiteralInt;
import ast.LiteralReal;
import ast.Programa;
import ast.Return;
import ast.Sentencia;
import ast.TipoArray;
import ast.TipoCaracter;
import ast.TipoEntero;
import ast.TipoReal;
import ast.TipoStruct;
import ast.TipoVoid;
import ast.Variable;
import ast.While;
import visitor.DefaultVisitor;

public class SeleccionDeInstrucciones extends DefaultVisitor {

	private PrintWriter writer;
	private String sourceFile;
	
	int contador = 0;

	enum Funcion {
		DIRECCION, VALOR
	}

	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
	}

	// class Programa { List<Definicion> definicion; }
	public Object visit(Programa node, Object param) {

		genera("#source \"" + sourceFile + "\" \n");
		genera("call main");
		genera("halt\n");

		// Recorrer los hijos
		if (node.getDefinicion() != null){
			for (Definicion child : node.getDefinicion()){
				child.accept(this, param);
			}
		}

		return null;
	}

	// class DefFuncion { String nombre; List<DefVariable> parametros; Tipo
	// tipoRetorno; List<DefVariable> variableLocales; List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {

		genera("\n#func " + node.getNombre());
		genera(node.getNombre() + ":"); // Etiqueta

		genera("\n#line " + node.getStart().getLine());

		int tamLocales = 0;
		int tamParametros = 0;
		
		// define[[parametrosi]]
		if (node.getParametros() != null){
			for (DefVariable dv : node.getParametros()){
				dv.accept(this, param);
				tamParametros += dv.getTipo().getSize();
			}
		}

		if (node.getTipoRetorno() != null){
			node.getTipoRetorno().accept(this, param);
		}
		
		// define[[variablesLocalesi]]
		if (node.getVariableLocales() != null){
			for (DefVariable dv : node.getVariableLocales()){
				dv.accept(this, param);
				tamLocales += dv.getTipo().getSize();
			}
		}
		
		genera("ENTER " + tamLocales);

		// ejecuta[[sentenciasi]]
		if (node.getSentencias() != null)
			for (Sentencia child : node.getSentencias())
				child.accept(this, param);
		
		genera("#ret " + node.getTipoRetorno().getTipoMAPL());

		if (node.getTipoRetorno() instanceof TipoVoid ){
			genera("ret 0," + tamLocales + "," + tamParametros);
		}

		return null;
	}

	// class DefVariable { Tipo tipo; String nombre; }
	public Object visit(DefVariable node, Object param) {

		if (node.getContextoVariable() == ContextoVariable.GLOBAL) {
			genera("\n#global " + node.getNombre() + ":" + node.getTipo().getTipoMAPL());
		}
		
		if (node.getContextoVariable() == ContextoVariable.LOCAL) {
			genera("\n#local " + node.getNombre() + ":" + node.getTipo().getTipoMAPL());
		}
		
		if (node.getContextoVariable() == ContextoVariable.PARAMETRO) {
			genera("\n#param " + node.getNombre() + ":" + node.getTipo().getTipoMAPL());
		}
		
		if (node.getTipo() != null){
			node.getTipo().accept(this, param);
		}

		return null;
	}

	// class DefStruct { String nombre; List<DefCampo> campos; }
	public Object visit(DefStruct node, Object param) {

		genera("#type " + node.getNombre() + " : {");

			if (node.getCampos() != null){
				for (DefCampo dc : node.getCampos()){
					dc.accept(this, param);
				}
			}

		genera("}");

		return null;
	}

	// class DefCampo { String nombre; Tipo tipo; }
	public Object visit(DefCampo node, Object param) {

		if (node.getTipo() != null){
			node.getTipo().accept(this, param);
		}

		genera(node.getNombre() + " : " + node.getTipo().getTipoMAPL());

		return null;
	}

	// class Asignacion { Expresion left; Expresion right; }
	public Object visit(Asignacion node, Object param) {
		genera("\n#line " + node.getEnd().getLine());
		
		if (node.getLeft() != null){
			node.getLeft().accept(this, Funcion.DIRECCION);
		}
		
		if (node.getRight() != null){
			node.getRight().accept(this, Funcion.VALOR); 
		}
		
		genera("store" + node.getLeft().getTipo().getSufijo());

		return null;
	}

	// class Escritura { Expresion exprEscritura; String tipoEscritura; }
	public Object visit(Escritura node, Object param) {

		genera("\n#line " + node.getEnd().getLine());
		
		node.getExprEscritura().accept(this, Funcion.VALOR); // visit(node.getExprEscritura(),
																// Funcion.VALOR);
		genera("OUT" + node.getExprEscritura().getTipo().getSufijo());
		
		if (node.getTipoEscritura().equals("printsp")){
			genera("pushb 32"); // Espacio 
			genera("OUTb");
		}
		
		if (node.getTipoEscritura().equals("println")){
			genera("pushb 10"); // Salto de linea \n
			genera("OUTb");
		}

		return null;
	}

	 // class Lectura { Expresion exprLectura; }
	 public Object visit(Lectura node, Object param) {
	
		 genera("\n#line " + node.getEnd().getLine());
		
		if (node.getExprLectura() != null){
			node.getExprLectura().accept(this, Funcion.DIRECCION);
		}
		 
		 genera("IN" + node.getExprLectura().getTipo().getSufijo());
		 genera("store" + node.getExprLectura().getTipo().getSufijo()); //node.getExprLectura().accept(this, Funcion.VALOR);
		 
		 return null;
	 }
	
	// class Return { Expresion exprRetorno; }
	public Object visit(Return node, Object param) {

		if (node.getEnd() != null) {
			genera("\n #line " + node.getEnd().getLine());
		}

		int tamLocales = 0;
		int tamParametros = 0;

		if (node.getDefFuncion() != null){
			
			for (DefVariable dv : node.getDefFuncion().getVariableLocales()){	// locales
				tamLocales += dv.getTipo().getSize();
			}
			
			for (DefVariable dv : node.getDefFuncion().getParametros()) {			// parametros
				tamParametros += dv.getTipo().getSize();
			}

			if (node.getExprRetorno() != null) { // if (!(node.getExprRetorno().getTipo() instanceof TipoVoid))
				node.getExprRetorno().accept(this, Funcion.VALOR);
				genera("RET " + node.getExprRetorno().getTipo().getSize() + ", " + tamLocales + ", " + tamParametros);
			} else
				genera("RET 0, " + tamLocales + ", " + tamParametros);  // return vacio [ return ; ]
		}

		return null;
	}
	
	// class Ifelse { Expresion condicion; List<Sentencia> sentenciasIf; List<Sentencia> sentenciasElse; }
	public Object visit(Ifelse node, Object param) {

		int idx = contador++;

		genera("\n#line " + node.getStart().getLine());

		node.getCondicion().accept(this, Funcion.VALOR); // valor[[condicion]]

		genera("JZ " + "else" + idx);// JZ else

		if (node.getSentenciasIf() != null) {
			for (Sentencia s : node.getSentenciasIf()) {
				s.accept(this, Funcion.VALOR);
			}
		}

		genera("JMP finIfElse" + idx);
		genera("else" + idx + ":");

		if (node.getSentenciasElse() != null) {
			for (Sentencia s : node.getSentenciasElse()) {
				s.accept(this, Funcion.VALOR);
			}
		}

		genera("finIfElse" + idx + ":");

		return null;
	}
	
	// class While { Expresion condicion; List<Sentencia> sentenciasWhile; }
	public Object visit(While node, Object param) {

		int idx = contador++;

		genera("\n\n#line " + node.getStart().getLine());

		genera("inicioWhile" + idx + ":");
		node.getCondicion().accept(this, Funcion.VALOR);
		genera("JZ " + "finalWhile" + idx);

		for (Sentencia s : node.getSentenciasWhile()) {
			s.accept(this, Funcion.VALOR);
		}

		genera("JMP inicioWhile" + idx);
		genera("finalWhile" + idx + ":");

		return null;
	}
	
	 // class InvFuncSent { String nombreFuncion; List<Expresion> parametros; }
	 public Object visit(InvFuncSent node, Object param) {
			 
		 genera("\n\n#line " + node.getStart().getLine());
		
		 if (node.getParametros() != null){
			 for (Expresion parametro : node.getParametros()){
				 parametro.accept(this, Funcion.VALOR);
			 }
		 }
		 
		 genera("CALL " + node.getNombreFuncion());
		 
		 if (!(node.getDefinicion().getTipoRetorno() instanceof TipoVoid)) {
				genera("POP" + node.getDefinicion().getTipoRetorno().getSufijo());
			}
		 
		 return null;
	 }

	// class ExpresionBinaria { Expresion left; String operador; Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {

		if (node.getLeft() != null)
			node.getLeft().accept(this, Funcion.VALOR); 
		
		if (node.getRight() != null)
			node.getRight().accept(this, Funcion.VALOR); 


		if (node.getOperador().equals("+")) { // aritmeticos
			genera("add" + node.getLeft().getTipo().getSufijo());
		} else if (node.getOperador().equals("-")) {
			genera("sub" + node.getLeft().getTipo().getSufijo());
		} else if (node.getOperador().equals("*")) {
			genera("mul" + node.getLeft().getTipo().getSufijo());
		} else if (node.getOperador().equals("/")) {
			genera("div" + node.getLeft().getTipo().getSufijo());
		} else if (node.getOperador().equals(">")) { // relacionales
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

	// class ExpresionLogica { Expresion left; String operador; Expresion right; }
	public Object visit(ExpresionLogica node, Object param) {

		if (node.getLeft() != null)
			node.getLeft().accept(this, Funcion.VALOR); 
		
		if (node.getRight() != null)
			node.getRight().accept(this, Funcion.VALOR); 


		if (node.getOperador().equals("&&")) {
			genera("and");
		}

		if (node.getOperador().equals("||")) {
			genera("or");
		}

		return null;
	}

	// class ExpresionUnariaNegacion { Expresion expresion; }
	public Object visit(ExpresionUnariaNegacion node, Object param) {

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, Funcion.VALOR); 

		genera("not");

		return null;
	}

	// class Cast { Tipo tipoDestino; Expresion expresionAConvertir; }
	public Object visit(Cast node, Object param) {

		if (node.getTipoDestino() != null)
			node.getTipoDestino().accept(this, param);
		
		if (node.getExpresionAConvertir() != null)
			node.getExpresionAConvertir().accept(this, param);

		// i2b || b2i || f2i || i2f
		genera(node.getExpresionAConvertir().getTipo().getSufijo() + "2" + node.getTipoDestino().getSufijo());

		return null;
	}

	// class AccesoArray { Expresion array; Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {

		if ((Funcion) param == Funcion.DIRECCION) {
			
			// dir base del array
			if (node.getArray() != null)
				node.getArray().accept(this, Funcion.DIRECCION); 
			
			// desplazamiento
			if (node.getPosicion() != null)
				node.getPosicion().accept(this, Funcion.VALOR); 
			
			genera("push " + node.getTipo().getSize());
			genera("mul"); // node.getTipo().getSufijo()
			genera("add");
		}

		if ((Funcion) param == Funcion.VALOR) {
			node.accept(this, Funcion.DIRECCION); // visit(node, Funcion.DIRECCION);
			genera("LOAD" + node.getTipo().getSufijo());//node.getArray().getTipo().getSufijo());
		}

		return null;
	}

	// class AccesoStruct { Expresion struct; String nombreCampo; }
	public Object visit(AccesoStruct node, Object param) {

			//	TipoStruct struct = (TipoStruct) node.getStruct().getTipo();
			//	List<DefCampo> campos = struct.getDefinicionEstructura().getCampos();

		if ((Funcion) param == Funcion.DIRECCION) {
			
			if (node.getStruct() != null){
				node.getStruct().accept(this, Funcion.DIRECCION);
				genera("push " + node.getDefinicion().getDireccion());
				genera("add");
			}
			
				//	super.visit(node, Funcion.DIRECCION); // direccion base del array
				//	for (DefCampo dc : campos) {
				//		if (dc.getNombre().equals(node.getNombreCampo())) {
				//			// visit(node.getStruct(), Funcion.DIRECCION)
				//			// dir base -> No necesaria porque un Struct solo puede ser
				//			// una variable global por lo que la direccion que
				//			// necesitamos es la del campo directamente
				//			genera("push " + dc.getDireccion());
				//			genera("add");
				//		}
				//	}
		}

		if ((Funcion) param == Funcion.VALOR) {
			visit(node, Funcion.DIRECCION);
			genera("load" + node.getDefinicion().getTipo().getSufijo() );
				//	for (DefCampo dc : campos) {
				//		if (dc.getNombre().equals(node.getNombreCampo())) {
				//			genera("load" + dc.getTipo().getSufijo());
				//			return null;
				//		}
				//	}
		}

		return null;
	}

	// class InvFuncExpr { String nombreFuncion; List<Expresion> parametros; }
	public Object visit(InvFuncExpr node, Object param) {

		genera("\n#line " + node.getEnd().getLine());

		if (node.getParametros() != null) {
			for (Expresion e : node.getParametros()) {
				e.accept(this, Funcion.VALOR);
			}
		}

		genera("CALL " + node.getNombreFuncion());

		return null;
	}

	// class Variable { String nombre; }
	public Object visit(Variable node, Object param) {

		if ((Funcion) param == Funcion.DIRECCION) {

			if (node.getDefinicion().getContextoVariable() == ContextoVariable.GLOBAL) {
				genera("pusha " + node.getDefinicion().getDireccion());
			} else { // variable local o parametro -> tenemos que usar BP
				genera("pusha bp");
				
				if (node.getDefinicion().getContextoVariable() == ContextoVariable.LOCAL){
					genera("push " + (-1)*node.getDefinicion().getDireccion()); // Si es un parametro la direccion sera positiva. Si es una variable local la direccion sera negativa				
				} else { //ContextoVariable.PARAMETRO					
					genera("push " + node.getDefinicion().getDireccion()); // Si es un parametro la direccion sera positiva. Si es una variable local la direccion sera negativa
				}
				
				genera("add"); // genera("add" + node.getDefinicion().getTipo().getSufijo());
			}
		}

		if ((Funcion) param == Funcion.VALOR) {
			// NO TIENE SENTIDO COMPROBAR EL CONTEXTO (YA SE HACE EN DIRECCION)
			visit(node, Funcion.DIRECCION); // node.accept(node, Funcion.DIRECCION)
			genera("load" + node.getDefinicion().getTipo().getSufijo());
		}

		return null;
	}

	// class LiteralInt { int valor; }
	public Object visit(LiteralInt node, Object param) {

		genera("pushi " + node.getValor());

		return null;
	}

	// class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {

		genera("pushf " + node.getValor());

		return null;
	}

	// class LiteralCaracter { String valor; }
	public Object visit(LiteralCaracter node, Object param) {

		if (node.getValor().equals("'\\n'")){   // '\n'  (id=65)	
			genera("pushb 10"); // Salto de linea \n
		} else {
			genera("pushb " + (int) node.getValor().charAt(1));
		}

		return null;
	}

	// class TipoEntero { }
	public Object visit(TipoEntero node, Object param) {
		return null;
	}

	// class TipoReal { }
	public Object visit(TipoReal node, Object param) {
		return null;
	}

	// class TipoCaracter { }
	public Object visit(TipoCaracter node, Object param) {
		return null;
	}

	// class TipoArray { int tamano; Tipo tipo; }
	public Object visit(TipoArray node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	// class TipoIdent { String tipo; }
	public Object visit(TipoStruct node, Object param) {
		return null;
	}

	// Metodo auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}

}

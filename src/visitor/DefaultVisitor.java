/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Programa { List<Definicion> definicion; }
	public Object visit(Programa node, Object param) {
		visitChildren(node.getDefinicion(), param);
		return null;
	}

	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo tipoRetorno;  List<DefVariable> variableLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {
		visitChildren(node.getParametros(), param);
		if (node.getTipoRetorno() != null)
			node.getTipoRetorno().accept(this, param);
		visitChildren(node.getVariableLocales(), param);
		visitChildren(node.getSentencias(), param);
		return null;
	}

	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefStruct { String nombre;  List<DefCampo> campos; }
	public Object visit(DefStruct node, Object param) {
		visitChildren(node.getCampos(), param);
		return null;
	}

	//	class DefCampo { String nombre;  Tipo tipo; }
	public Object visit(DefCampo node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Escritura { Expresion exprEscritura;  String tipoEscritura; }
	public Object visit(Escritura node, Object param) {
		if (node.getExprEscritura() != null)
			node.getExprEscritura().accept(this, param);
		return null;
	}

	//	class Lectura { Expresion exprLectura; }
	public Object visit(Lectura node, Object param) {
		if (node.getExprLectura() != null)
			node.getExprLectura().accept(this, param);
		return null;
	}

	//	class Return { Expresion exprRetorno; }
	public Object visit(Return node, Object param) {
		if (node.getExprRetorno() != null)
			node.getExprRetorno().accept(this, param);
		return null;
	}

	//	class Ifelse { Expresion condicion;  List<Sentencia> sentenciasIf;  List<Sentencia> sentenciasElse; }
	public Object visit(Ifelse node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getSentenciasIf(), param);
		visitChildren(node.getSentenciasElse(), param);
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> sentenciasWhile; }
	public Object visit(While node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getSentenciasWhile(), param);
		return null;
	}

	//	class InvFuncSent { String nombreFuncion;  List<Expresion> parametros; }
	public Object visit(InvFuncSent node, Object param) {
		visitChildren(node.getParametros(), param);
		return null;
	}

	//	class ExpresionBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class ExpresionLogica { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionLogica node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class ExpresionUnariaNegacion { Expresion expresion; }
	public Object visit(ExpresionUnariaNegacion node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Cast { Tipo tipoDestino;  Expresion expresionAConvertir; }
	public Object visit(Cast node, Object param) {
		if (node.getTipoDestino() != null)
			node.getTipoDestino().accept(this, param);
		if (node.getExpresionAConvertir() != null)
			node.getExpresionAConvertir().accept(this, param);
		return null;
	}

	//	class AccesoArray { Expresion array;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {
		if (node.getArray() != null)
			node.getArray().accept(this, param);
		if (node.getPosicion() != null)
			node.getPosicion().accept(this, param);
		return null;
	}

	//	class AccesoStruct { Expresion struct;  String nombreCampo; }
	public Object visit(AccesoStruct node, Object param) {
		if (node.getStruct() != null)
			node.getStruct().accept(this, param);
		return null;
	}

	//	class InvFuncExpr { String nombreFuncion;  List<Expresion> parametros; }
	public Object visit(InvFuncExpr node, Object param) {
		visitChildren(node.getParametros(), param);
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		return null;
	}

	//	class LiteralInt { int valor; }
	public Object visit(LiteralInt node, Object param) {
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		return null;
	}

	//	class LiteralCaracter { String valor; }
	public Object visit(LiteralCaracter node, Object param) {
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
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class TipoIdent { String tipo; }
	public Object visit(TipoIdent node, Object param) {
		return null;
	}
	
	// Método auxiliar -----------------------------
	protected void visitChildren(List<? extends AST> children, Object param) {
		if (children != null)
			for (AST child : children)
				child.accept(this, param);
	}
}

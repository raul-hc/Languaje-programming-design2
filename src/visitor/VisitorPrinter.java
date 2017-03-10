package visitor;

import ast.*;

/* Añadir a cada método visit aquello adicional que tenga que realizar sobre su nodo del AST */

public class VisitorPrinter extends DefaultVisitor {

	// ---------------------------------------------------------
	// Tareas a realizar en cada método visit:
	//
	// Si en algún método visit NO SE QUIERE HACER NADA más que recorrer los hijos entonces se puede 
	// borrar (dicho método se heredará de DefaultVisitor con el código de recorrido).
	//
	// Lo siguiente es para cuando se quiera AÑADIR alguna funcionalidad adicional a un visit:
	//
	// - El código que aparece en cada método visit es aquel que recorre los hijos. Es el mismo código
	//		que está implementado en el padre (DefaultVisitor). Por tanto la llamada a 'super.visit' y el
	//		resto del código del método hacen lo mismo (por ello 'super.visit' está comentado).
	//
	// - Lo HABITUAL será borrar todo el código de recorrido dejando solo la llamada a 'super.visit'. De esta
	//		manera cada método visit se puede centrar en la tarea que tiene que realizar sobre su nodo del AST.
	//
	// - La razón de que aparezca el código de recorrido de los hijos es por si se necesita realizar alguna
	//		tarea DURANTE el mismo (por ejemplo ir comprobando su tipo). En este caso ya se tiene implementado
	//		dicho recorrido y solo habrá que incrustar las acciones adicionales en el mismo. En este caso
	//		la llamada a 'super.visit' deberá ser borrada.
	// ---------------------------------------------------------

	//	class Programa { List<Definicion> definicion; }
	public Object visit(Programa node, Object param) {

		// super.visit(node, param);

		if (node.getDefinicion() != null) {
			for (Definicion child : node.getDefinicion()) {
				child.accept(this, param);
			}
		}
					

		return null;
	}

	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo tipoRetorno;  List<DefVariable> variableLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {

		// super.visit(node, param);

		System.out.print(node.getNombre() + "(");
		
		if (node.getParametros() != null){
			for (DefVariable child : node.getParametros()){
				System.out.print(child.getNombre() + ":" + child.getTipo() + ", ");
//				child.accept(this, param);
			}
		}
		
		System.out.print(")");
		
		if (node.getTipoRetorno() != null) {
			System.out.print(":");
			node.getTipoRetorno().accept(this, param);
		}
		
		System.out.println(" {");
		
		if (node.getVariableLocales() != null) {
			for (DefVariable child : node.getVariableLocales()){
				System.out.print("\t");
				child.accept(this, param);
			}
		}
		
		System.out.println();
		
		if (node.getSentencias() != null) {
			for (Sentencia child : node.getSentencias()) {
				System.out.print("\t");
				child.accept(this, param);
				System.out.println();				
			}
		}
		
		System.out.println("}");

		return null;
	}

	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable node, Object param) {

		// super.visit(node, param);

		System.out.print("var " + node.getNombre() + ":");
		
		if (node.getTipo() != null) {
			node.getTipo().accept(this, param);
		}
		
		System.out.println(";");

		return null;
	}

	//	class DefStruct { String nombre;  List<DefCampo> campos; }
	public Object visit(DefStruct node, Object param) {

		// super.visit(node, param);

		System.out.print("struct " + node.getNombre() + " {" + "\n");
		
		if (node.getCampos() != null){
			for (DefCampo child : node.getCampos()){
				child.accept(this, param);
			}
		}
		
		System.out.println("};");

		return null;
	}

	//	class DefCampo { String nombre;  Tipo tipo; }
	public Object visit(DefCampo node, Object param) {

		// super.visit(node, param);

		System.out.print("\t" + node.getNombre() + ":");
		
		if (node.getTipo() != null) {
			node.getTipo().accept(this, param);
		}
		
		System.out.println(";");

		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {

		System.out.print("\t");
		
		if (node.getLeft() != null){
			node.getLeft().accept(this, param);
		}
		
		System.out.print(" = ");

		if (node.getRight() != null){
			node.getRight().accept(this, param);
		}

		System.out.print(";" );

		return null;
	}

	//	class Escritura { Expresion exprEscritura;  String tipoEscritura; }
	public Object visit(Escritura node, Object param) {

		System.out.print("\t");
		System.out.print(node.getTipoEscritura() + " ");

		if (node.getExprEscritura() != null){
			node.getExprEscritura().accept(this, param);
		}

		System.out.println(";");
		
		return null;
	}

	//	class Lectura { Expresion exprLectura; }
	public Object visit(Lectura node, Object param) {

		System.out.print("\t");
		System.out.print("read ");

		if (node.getExprLectura() != null){
			node.getExprLectura().accept(this, param);
		}
		
		System.out.println(";");

		return null;
	}

	//	class Return { Expresion exprRetorno; }
	public Object visit(Return node, Object param) {

		System.out.print("\t");
		System.out.print("return ");
		
		if (node.getExprRetorno() != null){
			node.getExprRetorno().accept(this, param);
		}
		
		System.out.println(";");

		return null;
	}

	//	class Ifelse { Expresion condicion;  List<Sentencia> sentenciasIf;  List<Sentencia> sentenciasElse; }
	public Object visit(Ifelse node, Object param) {

		// super.visit(node, param);

		System.out.print("if (");
		
		if (node.getCondicion() != null){
			node.getCondicion().accept(this, param);
		}

		System.out.println(") {");
		
		if (node.getSentenciasIf() != null){
			for (Sentencia child : node.getSentenciasIf()) {
				System.out.println("\t");
				child.accept(this, param);
			}
		}

		System.out.print("\t}");
		
		if (node.getSentenciasElse() != null){
			System.out.println("\nelse {");
			for (Sentencia child : node.getSentenciasElse()){
				System.out.println("\t");
				child.accept(this, param);
			}
			System.out.println("}");
		}

		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> sentenciasWhile; }
	public Object visit(While node, Object param) {

		System.out.print("while (");
		
		if (node.getCondicion() != null){
			node.getCondicion().accept(this, param);
		}
		
		System.out.print(") {");

		if (node.getSentenciasWhile() != null){
			for (Sentencia child : node.getSentenciasWhile()){
				System.out.println("\t");
				child.accept(this, param);
			}
		}
		
		System.out.print("\n" + "}");

		return null;
	}

	//	class InvFuncSent { String nombreFuncion;  List<Expresion> parametros; }
	public Object visit(InvFuncSent node, Object param) {

		System.out.print("\t");

		System.out.print(node.getNombreFuncion() + "(");
		
		if (node.getParametros() != null){
			for (Expresion child : node.getParametros()){
				child.accept(this, param);
				System.out.print(", ");
			}
		}
		
		System.out.println(")");

		return null;
	}

	//	class ExpresionBinaria { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {

		if (node.getLeft() != null){
			node.getLeft().accept(this, param);
		}
		
		System.out.print(node.getOperador());

		if (node.getRight() != null) {
			node.getRight().accept(this, param);
		}

		return null;
	}

	//	class ExpresionLogica { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionLogica node, Object param) {

		if (node.getLeft() != null){
			node.getLeft().accept(this, param);
		}
		
		System.out.print(node.getOperador());

		if (node.getRight() != null){
			node.getRight().accept(this, param);
		}

		return null;
	}

	//	class ExpresionUnaria { Expresion expresion; }
	public Object visit(ExpresionUnariaNegacion node, Object param) {

		if (node.getExpresion() != null){
			System.out.print("!");
			node.getExpresion().accept(this, param);
		}

		return null;
	}

	//	class Cast { Tipo tipoDestino;  Expresion expresionAConvertir; }
	public Object visit(Cast node, Object param) {

		System.out.print("cast<");
		
		if (node.getTipoDestino() != null){
			node.getTipoDestino().accept(this, param);
			// System.out.println(node);
		}
		
		System.out.print(">(");

		if (node.getExpresionAConvertir() != null){
			node.getExpresionAConvertir().accept(this, param);
		}
		
		System.out.print(")");

		return null;
	}

	//	class AccesoArray { Expresion array;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {

		if (node.getArray() != null){
			node.getArray().accept(this, param);
		}
		
		System.out.print("[");

		if (node.getPosicion() != null){
			node.getPosicion().accept(this, param);
		}

		System.out.print("]");

		return null;
	}

	//	class AccesoStruct { Expresion struct;  String nombreCampo; }
	public Object visit(AccesoStruct node, Object param) {
		
		if (node.getStruct() != null){
			node.getStruct().accept(this, param);
		}
		
		System.out.print("." + node.getNombreCampo());

		return null;
	}

	//	class InvFuncExpr { String nombreFuncion;  List<Expresion> parametros; }
	public Object visit(InvFuncExpr node, Object param) {

		// super.visit(node, param);

		System.out.print(node.getNombreFuncion() + "(" );
		
		if (node.getParametros() != null){
			for (Expresion child : node.getParametros()){
				child.accept(this, param);
				System.out.print(",");
			}
		}
		
		System.out.print(")");
		
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		System.out.print(node.getNombre());
		return null;
	}

	//	class LiteralInt { int valor; }
	public Object visit(LiteralInt node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	//	class LiteralCaracter { String valor; }
	public Object visit(LiteralCaracter node, Object param) {
		System.out.print(node.getValor());
		return null;
	}

	//	class TipoEntero {  }
	public Object visit(TipoEntero node, Object param) {
		System.out.print(" int");
		return null;
	}

	//	class TipoReal {  }
	public Object visit(TipoReal node, Object param) {
		System.out.print(" float");
		return null;
	}

	//	class TipoCaracter {  }
	public Object visit(TipoCaracter node, Object param) {
		System.out.print(" char");
		return null;
	}

	//	class TipoArray { int tamano;  Tipo tipo; }
	public Object visit(TipoArray node, Object param) {

		// super.visit(node, param);

		System.out.print("[" + node.getTamano() + "]");

		if (node.getTipo() != null){
			node.getTipo().accept(this, param);
		}
		

		return null;
	}

	//	class TipoIdent { String tipo; }
	public Object visit(TipoIdent node, Object param) {
		
		System.out.print(" " + node.getTipo());
		
		return null;
	}
}

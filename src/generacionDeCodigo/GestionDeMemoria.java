package generacionDeCodigo;

import visitor.DefaultVisitor;
import ast.*;

/** 
 * Clase encargada de asignar direcciones a las variables 
 */
public class GestionDeMemoria extends DefaultVisitor {

	int offsetGlobal;
	int offsetLocal;
	int offsetParametros;
	
	int offsetCampos;
		
	
	//	class DefVariable { Tipo tipo;  String nombre; }
	public Object visit(DefVariable node, Object param) {

		super.visit(node, param);
		
		switch (node.getContextoVariable()) {
		case GLOBAL:
			node.setDireccion(offsetGlobal); // R(p) - Reglas semanticas
			offsetGlobal += node.getTipo().getSize();
			break;
		case LOCAL:
			offsetLocal += node.getTipo().getSize();
			node.setDireccion(offsetLocal); // R(p) - Reglas semanticas
			break;
		case PARAMETRO:
			offsetParametros += node.getTipo().getSize();
			node.setDireccion(offsetParametros); // R(p) - Reglas semanticas
			break;
		default:
			break;
		}
		
	System.out.println("\t"+node.toString());
	
		return null;
	}
	
	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  Tipo tipoRetorno;  List<DefVariable> variableLocales;  List<Sentencia> sentencias; }
	public Object visit(DefFuncion node, Object param) {

		// Tenemos que recorrer los parametros en orden inverso
		if (node.getParametros() != null){	
			for (int i = node.getParametros().size() ; i > 0 ; i--){
				node.getParametros().get(i-1).accept(this, param); // child.accept(this, param);				
			}
		}
		
		offsetParametros = 0;

		if (node.getTipoRetorno() != null)
			node.getTipoRetorno().accept(this, param);

		if (node.getVariableLocales() != null)
			for (DefVariable child : node.getVariableLocales())
				child.accept(this, param);

		offsetLocal = 0;
		
		if (node.getSentencias() != null)
			for (Sentencia child : node.getSentencias())
				child.accept(this, param);

		return null;
	}
	
	//	class DefCampo { String nombre;  Tipo tipo; }
	public Object visit(DefCampo node, Object param) {

		super.visit(node, param);

		// R(p) - Reglas semanticas
		node.setDireccion(offsetCampos);	
	System.out.println("\t"+node.toString());
		offsetCampos += node.getTipo().getSize(); // = offsetLocal + node.getTipo().getSize();
		
		return null;
	}
	
	//	class DefStruct { String nombre;  List<DefCampo> campos; }
	public Object visit(DefStruct node, Object param) {

		super.visit(node, param);

		offsetCampos  = 0;
		
		return null;
	}


}

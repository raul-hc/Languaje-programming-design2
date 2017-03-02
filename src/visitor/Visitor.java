/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Programa node, Object param);
	public Object visit(DefFuncion node, Object param);
	public Object visit(DefVariable node, Object param);
	public Object visit(DefStruct node, Object param);
	public Object visit(DefCampo node, Object param);
	public Object visit(Asignacion node, Object param);
	public Object visit(Escritura node, Object param);
	public Object visit(Lectura node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(Ifelse node, Object param);
	public Object visit(While node, Object param);
	public Object visit(InvFuncSent node, Object param);
	public Object visit(ExpresionBinaria node, Object param);
	public Object visit(ExpresionLogica node, Object param);
	public Object visit(ExpresionUnaria node, Object param);
	public Object visit(EntreParentesis node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(AccesoArray node, Object param);
	public Object visit(AccesoStruct node, Object param);
	public Object visit(InvFuncExpr node, Object param);
	public Object visit(Variable node, Object param);
	public Object visit(LiteralInt node, Object param);
	public Object visit(LiteralReal node, Object param);
	public Object visit(LiteralCaracter node, Object param);
	public Object visit(TipoEntero node, Object param);
	public Object visit(TipoReal node, Object param);
	public Object visit(TipoCaracter node, Object param);
	public Object visit(TipoArray node, Object param);
	public Object visit(TipoIdent node, Object param);
}

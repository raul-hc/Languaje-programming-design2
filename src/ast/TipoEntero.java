/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	tipoEntero:tipo -> 

public class TipoEntero extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "TipoEntero";
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public String getSufijo() {
		return "i";
	}

	@Override
	public String getTipoMAPL() {
		return "int";
	}

}


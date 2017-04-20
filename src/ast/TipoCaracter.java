/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	TipoCaracter:tipo -> 

public class TipoCaracter extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "TipoCaracter";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public String getSufijo() {
		return "b";
	}

}


/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	tipoIdent:tipo -> tipo:String

public class TipoStruct extends AbstractTipo {

	public TipoStruct(String tipo) {
		this.tipo = tipo;
	}

	public TipoStruct(Object tipo) {
		this.tipo = (tipo instanceof Token) ? ((Token)tipo).getLexeme() : (String) tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public DefStruct getDefinicionEstructura() {
		return definicionEstructura;
	}

	public void setDefinicionEstructura(DefStruct definicionEstructura) {
		this.definicionEstructura = definicionEstructura;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public String toString() {
		return "TipoStruct (" + tipo + ")";
	}

	@Override
	public int getSize() {
		int total = 0;
		
		for (DefCampo dc : definicionEstructura.getCampos()){
			total += dc.getTipo().getSize();
		}
		
		return total;
	}

	private String tipo;
	private DefStruct definicionEstructura;
}


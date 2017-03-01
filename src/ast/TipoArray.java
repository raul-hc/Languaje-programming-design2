/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	TipoArray:tipo -> tamano:int  tipo:tipo

public class TipoArray extends AbstractTipo {

	public TipoArray(int tamano, Tipo tipo) {
		this.tamano = tamano;
		this.tipo = tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public TipoArray(Object tamano, Object tipo) {
		this.tamano = (tamano instanceof Token) ? Integer.parseInt(((Token)tamano).getLexeme()) : (Integer) tamano;
		this.tipo = (Tipo) tipo;

		searchForPositions(tamano, tipo);	// Obtener linea/columna a partir de los hijos
	}

	public int getTamano() {
		return tamano;
	}
	public void setTamano(int tamano) {
		this.tamano = tamano;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private int tamano;
	private Tipo tipo;
}


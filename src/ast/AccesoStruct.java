/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	accesoStruct:expresion -> struct:expresion  nombreCampo:String

public class AccesoStruct extends AbstractExpresion {

	public AccesoStruct(Expresion struct, String nombreCampo) {
		this.struct = struct;
		this.nombreCampo = nombreCampo;

		searchForPositions(struct);	// Obtener linea/columna a partir de los hijos
	}

	public AccesoStruct(Object struct, Object nombreCampo) {
		this.struct = (Expresion) struct;
		this.nombreCampo = (nombreCampo instanceof Token) ? ((Token)nombreCampo).getLexeme() : (String) nombreCampo;

		searchForPositions(struct, nombreCampo);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getStruct() {
		return struct;
	}
	public void setStruct(Expresion struct) {
		this.struct = struct;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}
	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion struct;
	private String nombreCampo;
}


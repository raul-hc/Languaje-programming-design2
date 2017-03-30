/**
 * @generated VGen 1.3.3
 */

package ast;

public abstract class AbstractExpresion extends AbstractTraceable implements Expresion {

	private Tipo tipo;
	private boolean modificable;
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
		
	}
	public Tipo getTipo() {
		return tipo;
	}

	public void setModificable(boolean modificable) {
		this.modificable = modificable;
	}
	public boolean isModificable() {
		return modificable;
	}
}
/**
 * @generated VGen 1.3.3
 */

package ast;

public abstract class AbstractSentencia extends AbstractTraceable implements Sentencia {

	DefFuncion defFuncion;
	
	public void setDefFuncion(DefFuncion defFuncion) {
		this.defFuncion = defFuncion;
	}
	
	public DefFuncion getDefFuncion() {
		return defFuncion;
	}
	
}


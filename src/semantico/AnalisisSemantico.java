package semantico;

import main.*;
import ast.*;

/**
 * Esta clase coordina las dos fases del Analisis Semantico:
 * 1- Fase de Identificación
 * 2- Fase de Inferencia
 * 
 * No es necesario modificar esta clase. En su lugar hay que modificar las clases
 * que son llamadas desde aquí: "Identificacion.java" y "ComprobacionDeTipos.java"
 *
 */
public class AnalisisSemantico {
	
	public AnalisisSemantico(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}
	
	public void analiza(AST raiz) {
		
		System.out.println("- Identificacion y Comprobacion de tipos... ");
		
		Identificacion identificacion = new Identificacion(gestorErrores);
		raiz.accept(identificacion, null);

		if (gestorErrores.hayErrores())
			return;

		ComprobacionDeTipos comprobacion = new ComprobacionDeTipos(gestorErrores);
		raiz.accept(comprobacion, null);
		
		System.out.println(" ...Okk");
	}

	private GestorErrores gestorErrores;
}

package main;

import generacionDeCodigo.*;

import java.io.*;

import semantico.*;
import sintactico.*;
import visitor.*;
import ast.*;


/**
 * Clase que inicia el compilador e invoca a todas sus fases.
 * 
 * No es necesario modificar este fichero. En su lugar hay que modificar:
 * - Para An�lisis Sint�ctico: 'sintactico/sintac.y' y 'sintactico/lexico.l'
 * - Para An�lisis Sem�ntico: 'semantico/Identificacion.java' y 'semantico/ComprobacionDeTipos.java'
 * - Para Generaci�n de C�digo: 'generacionDeCodigo/GestionDeMemoria.java' y 'generacionDeCodigo/SeleccionDeInstrucciones.java'
 *
 * @author Ra�l Izquierdo
 * 
 */
public class Main {
	public static final String programa = "src/ejemplo.txt";	// Entrada a usar durante el desarrollo

	public static void main(String[] args) throws Exception {
		GestorErrores gestor = new GestorErrores();

		AST raiz = compile(programa, gestor); // Poner args[0] en vez de "programa" en la version final
		if (!gestor.hayErrores())
			System.out.println("El programa se ha compilado correctamente.");

		ASTPrinter.toHtml(programa, raiz, "Traza arbol"); // Utilidad generada por VGen (opcional)
	}

	/**
	 * M�todo que coordina todas las fases del compilador
	 */
	public static AST compile(String sourceName, GestorErrores gestor) throws Exception {

		// 1. Fases de Analisis Lexico y Sint�ctico
		Yylex lexico = new Yylex(new FileReader(sourceName), gestor);
		Parser sintactico = new Parser(lexico, gestor, false);
		sintactico.parse();

		AST raiz = sintactico.getAST();
		if (raiz == null) // Hay errores o el AST no se ha implementado aun
			return null;

		// 2. Fase de Analisis Semantico
		AnalisisSemantico semantico = new AnalisisSemantico(gestor);
		semantico.analiza(raiz);
		if (gestor.hayErrores())
			return raiz;

		// 3. Fase de Generacion de Codigo
		File sourceFile = new File(sourceName);
		Writer out = new FileWriter(new File(sourceFile.getParent(), "salida.txt"));

		GeneracionDeCodigo generador = new GeneracionDeCodigo();
		generador.genera(sourceFile.getName(), raiz, out);
		out.close();

		return raiz;
	}
}

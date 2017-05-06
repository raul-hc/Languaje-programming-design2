package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import ast.AST;
import generacionDeCodigo.GeneracionDeCodigo;
import semantico.AnalisisSemantico;
import sintactico.Parser;
import sintactico.Yylex;
import visitor.ASTPrinter;

/**
 * Clase que inicia el compilador e invoca a todas sus fases.
 * 
 * No es necesario modificar este fichero. En su lugar hay que modificar:
 * - Para Analisis Sintactico: 'sintactico/sintac.y' y 'sintactico/lexico.l'
 * - Para Analisis Semantico: 'semantico/Identificacion.java' y 'semantico/ComprobacionDeTipos.java'
 * - Para Generacion de Codigo: 'generacionDeCodigo/GestionDeMemoria.java' y 'generacionDeCodigo/SeleccionDeInstrucciones.java'  */
public class Main {
	
/*# Test para el lexico y el sintactico */
//	public static final String programa = "src/ejemplo.txt";	
//	public static final String programa = "src/Hipoteca.txt"

/*# Test para el semantico parte 1 */
//	public static final String programa = "src/test/semantico-identificacion/prog1.txt";
//	public static final String programa = "src/test/semantico-identificacion/1. Funciones.txt";
//	public static final String programa = "src/test/semantico-identificacion/2. Estructuras.txt";
//	public static final String programa = "src/test/semantico-identificacion/3. Variables.txt";

/*# Test para el semantico parte 2 */
//	public static final String programa = "src/test/semantico-identificacion/prog1.txt";
//	public static final String programa = "src/test/semantico-identificacion/prog2.txt";

/*# Test para el semantico parte 3 */
//	public static final String programa = "src/test/semantico-tipos/Test-tipos.txt";
//	public static final String programa = "src/test/semantico-tipos/Test-tipos-short.txt";	

/*# Test para generacion de codigo parte1 (gestion de memoria) */
//	public static final String programa = "src/test/generacion-codigo/Test-gestion-memoria.txt";

/*# Test para generacion de codigo parte2 */
//	public static final String programa = "src/test/generacion-codigo/Test-generacion-codigo-basico-lab10.txt";
//	public static final String programa = "src/test/generacion-codigo/Test-generacion-codigo-basico-lab10-short.txt";

/*# Test para generacion de codigo parte3 */
//	public static final String programa = "src/test/generacion-codigo-parte-2/test-codigo-lab11-1.txt";
//	public static final String programa = "src/test/generacion-codigo-parte-2/test-codigo-lab11-2.txt";
	public static final String programa = "src/test/generacion-codigo-parte-2/test-codigo-lab11-3.txt";
//	public static final String programa = "src/test/generacion-codigo-parte-2/test-codigo-lab11-4.txt";

//	public static final String programa = "src/test/test-final/prueba.txt";
	
	public static void main(String[] args) throws Exception {
		GestorErrores gestor = new GestorErrores();

		AST raiz = compile(programa, gestor); // Poner args[0] en vez de "programa" en la version final
//		compile(programa, gestor);
		
		if (!gestor.hayErrores())
			System.out.print("\nEL PROGRAMA SE HA COMPILADO CORRECTAMENTE.");

		ASTPrinter.toHtml(programa, raiz, "Traza arbol"); // Utilidad generada por VGen (opcional)
		
//		VisitorPrinter vprinter = new VisitorPrinter();
//		raiz.accept(vprinter, null);		
	}

	/** Metodo que coordina todas las fases del compilador */
	public static AST compile(String sourceName, GestorErrores gestor) throws Exception {

		// 1. Fases de Analisis Lexico y Sintactico
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
		Writer out = new FileWriter(new File(sourceFile.getParent(), "salida13.txt"));

		GeneracionDeCodigo generador = new GeneracionDeCodigo();
		generador.genera(sourceFile.getName(), raiz, out);
		out.close();

		return raiz;
	}
}

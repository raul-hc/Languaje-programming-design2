
/* -- No es necesario modificar esta parte ----------------------------------------------- */
package sintactico;

import java.io.*;
import main.*;
import ast.Position;

%%
%byaccj
%unicode
%line
%column
%public

%{
	public Yylex(Reader in, GestorErrores gestor) {
		this(in);
		this.gestor = gestor;
	}

	public int line() { return yyline + 1; }
	public int column() { return yycolumn + 1; }
	public String lexeme() { return yytext(); }

	// Traza para probar el lexico de manera independiente al sint�ctico
	public static void main(String[] args) throws Exception {
		Yylex lex = new Yylex(new FileReader(Main.programa), new GestorErrores());
		int token;
		while ((token = lex.yylex()) != 0)
			System.out.println("\t[" + lex.line() + ":" + lex.column() + "] Token: " + token + ". Lexema: " + lex.lexeme());
	}

	private GestorErrores gestor;
%}

%%

/* -- Modificar aqui. Añadir reglas en esta seccion ----------------------------------------- */
	
[+\-\*/<>;,.:(){}=\]\[!\']			{ return yytext().charAt(0); }

">=" 	{ return Parser.MAYORIGUAL; }
"<="	{ return Parser.MENORIGUAL; }
"=="	{ return Parser.IGUALDAD; }
"!="	{ return Parser.DISTINTO; }

"&&"	{ return Parser.Y; }
"||"	{ return Parser.O; }

int		{ return Parser.INT; }
float	{ return Parser.FLOAT; }
char	{ return Parser.CHAR; }

var		{ return Parser.VAR; }
cast	{ return Parser.CAST; }
struct	{ return Parser.STRUCT; }

read 	{ return Parser.READ; }
return 	{ return Parser.RETURN; }
if		{ return Parser.IF; }
else 	{ return Parser.ELSE; }
while	{ return Parser.WHILE; }
print	{ return Parser.PRINT; }
printsp	{ return Parser.PRINTSP; }
println	{ return Parser.PRINTLN; }

[a-zA-ZñÑ][a-zA-Z0-9_ñÑ]*			{ return Parser.IDENTIFICADOR; }
[0-9]+								{ return Parser.LITERAL_ENTERO; }
[0-9]+"."[0-9]*						{ return Parser.LITERAL_REAL; }
'[a-zA-Z0-9_ñÑ()+\- =]' | '\\n' 	{ return Parser.LITERAL_CARACTER; }

/* -- Se aconseja dejar estas reglas al final ----------------------------------------------- */

"/*"([^*]|\*+[^*/])*\*+"/"		{ }		/* Comentario de varias lineas como este */
"//".*							{ }		// Comentario de una linea como este

[ \n\r]		{ }
"\t"		{ yycolumn += 3; } // Para que coincida con la info del editor de Eclipse (opcional). En eclipse: \t == 4 caracteres. En Jflex: \t == 1 carActer.

.			{ gestor.error("Lexico", "Cadena \"" + yytext() +"\" no reconocida.", new Position(line(), column())); }

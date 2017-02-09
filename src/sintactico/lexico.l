/* ------- CÓDIGO A INCLUIR ------- */
package sintactico;

import java.io.*;
import main.*;
import ast.Position;

%%

/* ------- OPCIONES ------- */

%byaccj
//%class Lexico // If this line is commented the default name for the generated class is Yylex
%unicode
%line
%column
%public

%{

/* ------- ATRIBUTOS Y MÉTODOS ------- */

	public Yylex(Reader in, GestorErrores gestor) {
		this(in);
		this.gestor = gestor;
	}

	public int line() { return yyline + 1; }
	public int column() { return yycolumn + 1; }
	public String lexeme() { return yytext(); }

	// Traza para probar el l�xico de manera independiente al sint�ctico
	public static void main(String[] args) throws Exception {
		Yylex lex = new Yylex(new FileReader(Main.programa), new GestorErrores());
		int token;
		while ((token = lex.yylex()) != 0)
			System.out.println("\t[" + lex.line() + ":" + lex.column() + "] Token: " + token + ". Lexema: " + lex.lexeme());
	}

	private GestorErrores gestor;
%}

/* ------- PATRONES (MACROS) - Opcional ------- */

%%

/* ------- REGLAS / ACCIONES ------- */

[+<>;\-\*/(){}=]				{ return yytext().charAt(0); }

DATA							{ return Tokens.DATA; 	}
CODE							{ return Tokens.CODE; 	}

read							{ return Tokens.READ; 	}
print							{ return Tokens.PRINT;	}
int								{ return Tokens.INT; 	}
real							{ return Tokens.REAL; 	}
if								{ return Tokens.IF; 	}
else							{ return Tokens.ELSE; 	}
while							{ return Tokens.WHILE; 	}


[a-zA-ZñÑ][a-zA-Z0-9_ñÑ]*		{ return Tokens.IDENTIFICADOR; }
[0-9]+							{ return Tokens.LITERAL_ENTERO; }
[0-9]+"."[0-9]*					{ return Tokens.LITERAL_REAL; }

/* -- Se aconseja dejar estas reglas al final ----------------------------------------------- */

"/*"([^*]|\*+[^*/])*\*+"/"		{ }		/* Comentario de varias lineas como este */
"//".*							{ }		// Comentario de una linea como este

[ \n\r]							{ }

// Para que coincida con la info del editor de Eclipse (opcional). En eclipse: \t == 4 caracteres. En Jflex: \t == 1 carácter.
"\t"							{ yycolumn += 3; } 

.			{ gestor.error("Léxico", "Cadena \"" + yytext() +"\" no reconocida.", new Position(line(), column())); }

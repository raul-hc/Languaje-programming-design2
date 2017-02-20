/* No es necesario modificar esta seccion ------------------ */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

// * Declaraciones Yacc
%token LITERAL_ENTERO
%token LITERAL_REAL
%token LITERAL_CARACTER

%token Y
%token O

%token DISTINTO
%token IGUALDAD
%token MAYORIGUAL
%token MENORIGUAL


/* Precedencias aqui --------------------------------------- */
%left '+' '-'
%left '*' '/'
%left '>' MAYORIGUAL '<' MENORIGUAL DISTINTO IGUALDAD
%left Y O '!'
%left '[' ']' '.'
%nonassoc '(' ')'

%%

/* Añadir las reglas en esta seccion ----------------------- */

programa: definiciones
		|
		;	

definiciones : definiciones definicion 
			| definicion
			;
			
definicion: defVar // variables globales
			| defStruct 		/* Structs solo pueden ser globales */
			| defFuncion
			;

defStruct: 'STRUCT' 'IDENTIFICADOR' '{' defCampos '}' ';'
;

defCampos: defCampos 'IDENTIFICADOR' ':' tipo ';' 
		| 
		;

tipo: 'INT'
	| 'FLOAT'
	| 'CHAR'
	| 'IDENTIFICADOR'
	| tipoArray 
	;

tipoArray: '[' LITERAL_ENTERO ']' tipo
		| '[' LITERAL_ENTERO ']' 
;

defFuncion: 'IDENTIFICADOR' '(' parametros ')' tipoRetorno '{' defVarLocales sentencias '}'
			| 'IDENTIFICADOR' '(' ')' tipoRetorno '{' defVarLocales sentencias '}'
			;
			
tipoRetorno: ':' tipo
			|
			;

parametros: parametros ',' 'IDENTIFICADOR' ':' tipo
		| 'IDENTIFICADOR' ':' tipo
		;
			
defVarLocales: defVarLocales defVar
			| 
			;

defVar: 'VAR' 'IDENTIFICADOR' ':' tipo ';'
	;

sentencias: sentencias sentencia
			| 
			;
			
sentencia: 	expr '=' expr ';'
			| escritura ';'
			| 'READ' expr ';'
			| 'RETURN' exprOpt ';'
			| if 
			| 'WHILE' '(' expr ')' '{' sentencias '}'		
			/* Invocacion a funcion como SENTENCIA */
			| invocacionFuncion ';' 
			;
			
exprOpt : expr
		| 
		;			
	
escritura: 		'PRINT' expr 
			| 	'PRINTSP' expr 
			| 	'PRINTLN' expr 
			;

if: 	'IF' '(' expr ')' '{' sentencias '}'			
	| 	'IF' '(' expr ')' '{' sentencias '}' 'ELSE' '{' sentencias '}'
	;

invocacionFuncion: 'IDENTIFICADOR' '(' listaExprSeparador ')' 
				| 'IDENTIFICADOR' '(' ')' 
				;
	
listaExprSeparador: listaExprSeparador ',' expr 
					| expr
					;
	
expr:  		expr '+' expr 			
		  | expr '-' expr 			
	      | expr '*' expr 			
	      | expr '/' expr 			
	      
	      | expr MAYORIGUAL expr		
	      | expr MENORIGUAL expr		
	      | expr '<' expr				
	      | expr '>' expr	
		  
	      | expr DISTINTO expr		
	      | expr IGUALDAD expr
	      | '!' expr		
	      
	      | expr Y expr				
	      | expr O expr				
		  
	      | 'IDENTIFICADOR'								
	      | LITERAL_ENTERO							
		  | LITERAL_REAL						
		  | LITERAL_CARACTER						
		  
		  | '(' expr ')' 					
		  | '{' expr '}' 					
		 
		  | 'CAST' '<' tipoBasico '>' expr

		  /* Acceso a un array */
	      | expr '[' expr ']'
		  
		  /* Acceso a un campo */
		  | expr '.' 'IDENTIFICADOR'	
	
		  /* Invocacion a funcion como expr */
		  | 'IDENTIFICADOR' '(' listaExprSeparador ')'	
		  ;

tipoBasico: 'INT'
	| 'FLOAT'
	| 'CHAR'
	;
			
	
%%

/* No es necesario modificar esta seccion ------------------ */

	private Yylex lex;
	private GestorErrores gestor;
	private AST raiz;
	
	public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
		this(debug);
		this.lex = lex;
		this.gestor = gestor;
	}

// Metodos de acceso para el main -------------
	public int parse() { return yyparse(); }
	public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
	void yyerror(String msg) {
		Token lastToken = (Token) yylval;
		gestor.error("Sintactico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
	}

	int yylex() {
		try {
			int token = lex.yylex();
			yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
			return token;
		} catch (IOException e) {
			return -1;
		}
	}


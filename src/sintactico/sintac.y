/* No es necesario modificar esta seccion ------------------ */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

// * Declaraciones Yacc
%token Y
%token O

%token DISTINTO
%token IGUALDAD
%token MAYORIGUAL
%token MENORIGUAL

																	/* El enlace dinamico solo funciona a la izda del punto. 
																	Visitor que reconstruya el txt de entrada original. */

/* Precedencias aqui --------------------------------------- */
%left '+' '-'
%left '*' '/'
%left '>' MAYORIGUAL '<' MENORIGUAL DISTINTO IGUALDAD
%left Y O '!'
%left '[' ']' '.'
%nonassoc '(' ')'

%%

/* Añadir las reglas en esta seccion ----------------------- */

programa: definiciones		{ raiz = new Programa($1); }
		|					{ $$ = new ArrayList(); }
		;	

definiciones : definiciones definicion 		{ $$ = $1; ((List)$$).add($2); }
			| definicion					{ $$ = new ArrayList<Definicion>(); ((List)$$).add($1); }
			;
			
definicion: defVar 			{ $$ = $1; }		// variables globales
			| defStruct 	{ $$ = $1; }		/* Structs solo pueden ser globales */
			| defFuncion	{ $$ = $1; }
			;

defStruct: 'STRUCT' 'IDENTIFICADOR' '{' defCampos '}' ';'	{ $$ = new DefStruct($2, $4); }
;

defCampos: defCampos 'IDENTIFICADOR' ':' tipo ';' 		{ $$ = $1; ((List)$$).add(new DefCampo($2, $4)); }
		| 												{ $$ = new ArrayList<DefCampo>(); }
		;

tipo: 'INT'				{ $$ = new TipoEntero(); }
	| 'FLOAT'			{ $$ = new TipoReal(); }
	| 'CHAR'			{ $$ = new TipoCaracter(); }
	| 'IDENTIFICADOR'	{ $$ = new TipoIdent($1); }
	| tipoArray 		{ $$ = $1; }
	;

tipoArray: '[' 'LITERAL_ENTERO' ']' tipo 			{ $$ = new TipoArray($2, $4); }
;

defFuncion: 'IDENTIFICADOR' '(' parametrosOpt ')' tipoRetorno '{' defVarLocales sentencias '}' 		{ $$ = new DefFuncion($1, $3, $5, $7, $8); }
			| 'IDENTIFICADOR' '(' parametrosOpt ')' '{' defVarLocales sentencias '}'				{ $$ = new DefFuncion($1, $3, null, $6, $7); }
			;
			
tipoRetorno: ':' tipo		{ $$ = $2; }
;

parametrosOpt: parametros	{ $$ = $1; }
			|				{ $$ = new ArrayList<DefVariable>(); }
			;

parametros: parametros ',' 'IDENTIFICADOR' ':' tipo		{ $$ = $1; ((List)$$).add(new DefVariable($5, $3)); }
		| 'IDENTIFICADOR' ':' tipo						{ $$ = new ArrayList<DefVariable>(); ((List)$$).add(new DefVariable($3, $1)); }
		;												
			
defVarLocales: defVarLocales defVar				{ $$ = $1; ((List)$$).add($2); }
			| 									{ $$ = new ArrayList<DefVariable>(); }
			;

defVar: 'VAR' 'IDENTIFICADOR' ':' tipo ';'		{ $$ = new DefVariable($4, $2); }
	;
	

sentencias: sentencias sentencia		{ $$ = $1; ((List)$$).add($2); }
			| 							{ $$ = new ArrayList<Sentencia>(); }
			;
			
sentencia: 	expr '=' expr ';'							{ $$ = new Asignacion($1, $3); }
			| escritura ';'								{ $$ = $1; }
			| 'READ' expr ';'							{ $$ = new Lectura($2); }
			| 'RETURN' expr ';'							{ $$ = new Return($2); }
			| 'RETURN' ';'								{ $$ = new Return(null); }
			| 'IF' '(' expr ')' '{' sentencias '}'									{ $$ = new Ifelse($3, $6, null); }	
			| 'IF' '(' expr ')' '{' sentencias '}' 'ELSE' '{' sentencias '}' 		{ $$ = new Ifelse($3, $6, $10); }	
			| 'WHILE' '(' expr ')' '{' sentencias '}'								{ $$ = new While($3, $6); }
			/* Invocacion a funcion como SENTENCIA */
			| invocacionFuncion ';' 					{ $$ = $1; }					
			;			
	
escritura: 		'PRINT'   expr 		{ $$ = new Escritura($2, "print"); }
			| 	'PRINTSP' expr 		{ $$ = new Escritura($2, "printsp"); }
			| 	'PRINTLN' expr 		{ $$ = new Escritura($2, "println"); }
			;

invocacionFuncion: 'IDENTIFICADOR' '(' listaExprSeparador ')' 	{ $$ = new InvFuncSent($1, $3); }
				|  'IDENTIFICADOR' '(' ')' 						{ $$ = new InvFuncSent($1, null); }
				;
	
listaExprSeparador: listaExprSeparador ',' expr 	{ $$ = $1; ((List)$$).add($3); }
					| expr							{ $$ = new ArrayList<Expresion>(); ((List)$$).add($1); }
					;								
	
expr:  		expr '+' expr 				{ $$ = new ExpresionBinaria($1, "+" ,$3); }	
		  | expr '-' expr 				{ $$ = new ExpresionBinaria($1, "-" ,$3); }	
	      | expr '*' expr 				{ $$ = new ExpresionBinaria($1, "*" ,$3); }	
	      | expr '/' expr 				{ $$ = new ExpresionBinaria($1, "/" ,$3); }	
	      
	      | expr MAYORIGUAL expr		{ $$ = new ExpresionBinaria($1, ">=" ,$3); }	
	      | expr MENORIGUAL expr		{ $$ = new ExpresionBinaria($1, "<=" ,$3); }	
	      | expr '<' expr				{ $$ = new ExpresionBinaria($1, "<" ,$3); }	
	      | expr '>' expr				{ $$ = new ExpresionBinaria($1, ">" ,$3); }	
		  
	      | expr DISTINTO expr			{ $$ = new ExpresionBinaria($1, ">" ,$3); }	
	      | expr IGUALDAD expr			{ $$ = new ExpresionBinaria($1, ">" ,$3); }	
	      
	      | '!' expr					{ $$ = new ExpresionUnariaNegacion($2); }	
	      | expr Y expr					{ $$ = new ExpresionLogica($1, "&&" ,$3); }	
	      | expr O expr					{ $$ = new ExpresionLogica($1, "||" ,$3); }	
		  
	      | 'IDENTIFICADOR'				{ $$ = new Variable($1); }				
	      | 'LITERAL_ENTERO'			{ $$ = new LiteralInt($1); }				
		  | 'LITERAL_REAL'				{ $$ = new LiteralReal($1); }	
		  | 'LITERAL_CARACTER'			{ $$ = new LiteralCaracter($1); }	
		  
		  | '(' expr ')' 				{ $$ = $2; }					
		 
		  | 'CAST' '<' tipo '>' expr	{ $$ = new Cast($3, $5); }	

		  /* Acceso a un array */	
	      | expr '[' expr ']'			{ $$ = new AccesoArray($1, $3); }	
		  
		  /* Acceso a un campo */
		  | expr '.' 'IDENTIFICADOR'	{ $$ = new AccesoStruct($1, $3); }	
	
		  /* Invocacion a funcion como expr */
		  | 'IDENTIFICADOR' '(' listaExprSeparador ')'			{ $$ = new InvFuncExpr($1, $3); }	
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


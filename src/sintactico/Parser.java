//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 3 "sintac.y"
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
final Object dup_yyval(Object val)
{
  return val;
}
//#### end semantic value section ####
public final static short Y=257;
public final static short O=258;
public final static short DISTINTO=259;
public final static short IGUALDAD=260;
public final static short MAYORIGUAL=261;
public final static short MENORIGUAL=262;
public final static short STRUCT=263;
public final static short IDENTIFICADOR=264;
public final static short INT=265;
public final static short FLOAT=266;
public final static short CHAR=267;
public final static short LITERAL_ENTERO=268;
public final static short VAR=269;
public final static short PRINT=270;
public final static short PRINTSP=271;
public final static short PRINTLN=272;
public final static short READ=273;
public final static short RETURN=274;
public final static short IF=275;
public final static short ELSE=276;
public final static short WHILE=277;
public final static short LITERAL_REAL=278;
public final static short LITERAL_CARACTER=279;
public final static short CAST=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    2,    4,    6,    6,
    7,    7,    7,    7,    7,    5,    5,    9,    8,    8,
   12,   12,   10,   10,   13,    3,   11,   11,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   16,
   16,   17,   17,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,    1,    6,    5,    0,
    1,    1,    1,    1,    4,    9,    8,    2,    1,    0,
    5,    3,    2,    0,    5,    5,    2,    0,    4,    3,
    3,    3,    3,    3,    2,    7,   11,    7,    5,    1,
    0,    3,    1,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    2,    3,    3,    1,    1,    1,    1,
    3,    5,    4,    3,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    4,    5,    6,    7,    0,
    0,    0,    3,   10,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   14,   11,   12,   13,    0,    0,    0,
   22,   24,    0,    0,    0,    0,   26,    0,    8,    0,
   18,   24,    0,    0,    0,    0,    0,   23,    0,   21,
   15,    9,    0,    0,    0,    0,   17,   58,    0,    0,
    0,    0,    0,    0,    0,   59,   60,    0,   27,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   35,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   16,    0,    0,   61,    0,    0,    0,   30,   31,   32,
   33,   34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   64,    0,
   25,    0,    0,    0,    0,    0,    0,   63,   29,   65,
   39,    0,   28,   28,    0,    0,    0,    0,   38,    0,
   28,    0,   37,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   19,   28,   16,   34,   40,
   47,   17,   48,   69,   70,  106,  107,
};
final static short yysindex[] = {                      -224,
 -238,   -5, -232,    0, -224,    0,    0,    0,    0,  -81,
 -214,   -6,    0,    0,    7,   25,   23,  -73,  -94,  -73,
  -42, -195, -198,    0,    0,    0,    0,   13,   16,   17,
    0,    0,  -73,  -44,   26,  -10,    0,  -73,    0, -184,
    0,    0,  -73,  -73,   28, -176,  -33,    0, -184,    0,
    0,    0,   31,   90,   90,   51,    0,    0,   90,   90,
   90,   90,   87,   54,   55,    0,    0,   36,    0,  495,
  -16,  -73,   66,   -9,  143,   90,  501,  507,  528,  554,
    0,  575,   90,   90,  -73,   90,   90,   90,   90,   90,
   90,   90,   90,   90,   90,   90,   90,   90, -167,   90,
    0,   48,   90,    0,  779,   67,   71,    0,    0,    0,
    0,    0,  346,  368,   49,   -9,   -9,  -45,  -45,  -45,
  -45,  646,  646,  596,  596,  -45,  -45,  629,    0,  732,
    0,   76,   60,   90,   -1,    2,   90,    0,    0,    0,
    0,  779,    0,    0,  -45,    1,   18, -152,    0,    6,
    0,   35,    0,
};
final static short yyrindex[] = {                       128,
    0,    0,    0,    0,  131,    0,    0,    0,    0,    0,
   91,    0,    0,    0,    0,    0,   92,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
    0,    0,    0,    0,    0,    0,    0,    0,   53,    0,
    0,    0,    0,    0,    0,  639,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -32,  -39,    0,   93,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   93,    0,   -8,    0,   94,    0,    0,    0,
    0,    0,    0,    0,    0,  114,  121,  379,  401,  408,
  436,   12,   19,   57,  802,  443,  465,    0,    0,    0,
    0,    0,  757,    0,    0,    0,    0,    0,    0,    0,
    0,    3,    0,    0,  472,    0,    0,   70,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  132,    0,    0,    0,    0,    5,    0,    0,   96,
  -30,    0,    0,    0,  861,   33,    0,
};
final static int YYTABLESIZE=1041;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
   99,   54,   54,   54,   54,   54,   55,   54,   57,   57,
   57,   57,   57,   57,   57,   33,   54,   23,   71,   54,
   54,   54,   54,   55,   31,   10,   57,   57,   57,   57,
   30,   12,   43,   54,   11,   43,   99,   41,    1,    2,
   55,   14,   45,   42,    3,   98,   42,   50,   51,   15,
   54,   18,   44,   54,   44,   44,   44,   55,   57,   45,
   57,   45,   45,   45,   20,   21,   22,   54,   35,   36,
   44,   37,   44,   38,   55,   39,  102,   45,   42,   45,
   32,   98,   44,   43,   46,   28,   52,   53,   72,  115,
   76,   57,   28,   83,   84,   85,  129,   46,   46,   46,
   46,   46,   36,   46,   44,  103,  131,  133,  101,   36,
  137,   45,  146,  147,  134,   46,  140,   46,  141,   54,
  152,  143,   54,  150,  144,  148,   55,    2,  151,   55,
    1,   20,   19,   41,   40,  132,   13,   49,    0,    0,
    0,    0,  149,    0,    0,   81,    0,    0,    0,   46,
    0,    0,    0,    0,   55,   55,   55,   55,   55,  153,
   55,   56,   56,   56,   56,   56,    0,   56,    0,   29,
    0,    0,   55,   55,   55,   55,    0,   28,    0,   56,
   56,   56,   56,  104,   94,   92,    0,   93,   99,   95,
   24,   25,   26,   27,   36,    0,    0,    0,    0,    0,
    0,    0,   97,    0,   96,    0,   55,    0,    0,    0,
    0,   86,   87,   56,    0,    0,    0,   54,   54,   54,
   54,   54,   54,    0,   57,   57,   57,   57,   57,   57,
   56,    0,    0,   98,   58,    0,   59,   60,   61,   62,
   63,   64,    0,   65,   66,   67,   68,   56,    0,    0,
    0,   58,    0,   59,   60,   61,   62,   63,   64,    0,
   65,   66,   67,   68,   56,    0,    0,    0,   58,    0,
   59,   60,   61,   62,   63,   64,    0,   65,   66,   67,
   68,   56,    0,    0,    0,   58,    0,   59,   60,   61,
   62,   63,   64,    0,   65,   66,   67,   68,   56,    0,
    0,    0,   58,    0,   59,   60,   61,   62,   63,   64,
    0,   65,   66,   67,   68,    0,   28,    0,    0,    0,
   28,    0,   28,   28,   28,   28,   28,   28,    0,   28,
   28,   28,   28,   36,    0,    0,    0,   36,    0,   36,
   36,   36,   36,   36,   36,    0,   36,   36,   36,   36,
   73,    0,    0,   73,   58,    0,    0,   58,    0,    0,
    0,    0,    0,    0,   66,   67,   68,   66,   67,   68,
   55,   55,   55,   55,   55,   55,    0,   56,   56,   56,
   56,   56,   56,    0,    0,    0,  135,   94,   92,    0,
   93,   99,   95,    0,    0,    0,    0,    0,    0,   86,
   87,   88,   89,   90,   91,   97,    0,   96,  136,   94,
   92,    0,   93,   99,   95,    0,    0,    0,    0,   52,
   52,   52,   52,   52,    0,   52,    0,   97,    0,   96,
    0,    0,    0,    0,    0,    0,   98,   52,   52,   52,
   52,   53,   53,   53,   53,   53,    0,   53,   48,   48,
   48,   48,   48,    0,   48,    0,    0,    0,   98,   53,
   53,   53,   53,    0,    0,    0,   48,   48,   48,   48,
    0,   52,    0,    0,    0,    0,   49,   49,   49,   49,
   49,    0,   49,   51,   51,   51,   51,   51,    0,   51,
    0,    0,    0,   53,   49,   49,   49,   49,    0,    0,
   48,   51,   51,   51,   51,   50,   50,   50,   50,   50,
    0,   50,   62,   62,   62,   62,   62,    0,   62,    0,
    0,    0,    0,   50,   50,   50,   50,    0,   49,    0,
   62,   62,   62,   62,    0,   51,   94,   92,    0,   93,
   99,   95,   94,   92,    0,   93,   99,   95,   94,   92,
    0,   93,   99,   95,   97,  100,   96,   50,    0,  108,
   97,    0,   96,    0,   62,  109,   97,    0,   96,   94,
   92,    0,   93,   99,   95,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   98,  110,   97,    0,   96,
    0,   98,    0,    0,    0,   94,   92,   98,   93,   99,
   95,    0,   86,   87,   88,   89,   90,   91,    0,    0,
    0,    0,  111,   97,    0,   96,   94,   92,   98,   93,
   99,   95,    0,    0,   86,   87,   88,   89,   90,   91,
    0,    0,    0,  112,   97,    0,   96,   52,   52,   52,
   52,   99,    0,    0,   98,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   97,    0,   96,    0,   53,
   53,   53,   53,    0,    0,   98,   48,   48,   48,   48,
   94,   92,    0,   93,   99,   95,    0,    0,    0,    0,
   57,   57,    0,   57,   57,   57,   98,   94,   97,    0,
   96,   99,   95,    0,   49,   49,   49,   49,   57,   57,
   57,   51,   51,   51,   51,   97,    0,   96,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   98,
    0,  138,    0,   50,   50,   50,   50,    0,    0,   57,
   62,   62,   62,   62,    0,    0,   98,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   86,   87,   88,   89,   90,   91,   86,   87,   88,
   89,   90,   91,   86,   87,   88,   89,   90,   91,    0,
    0,    0,    0,   94,   92,    0,   93,   99,   95,    0,
    0,    0,    0,    0,   86,   87,   88,   89,   90,   91,
  139,   97,    0,   96,    0,    0,    0,    0,   65,   65,
    0,   65,   65,   65,    0,    0,    0,    0,    0,    0,
   86,   87,   88,   89,   90,   91,   65,   65,   65,    0,
   94,   92,   98,   93,   99,   95,    0,    0,    0,    0,
    0,   86,   87,   88,   89,   90,   91,    0,   97,    0,
   96,    0,   47,   47,   47,   47,   47,   65,   47,    0,
    0,    0,   86,   87,   88,   89,   90,   91,    0,    0,
   47,    0,   47,    0,    0,    0,    0,    0,    0,   98,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   86,   87,   88,   89,   90,
   91,    0,    0,    0,   47,   57,   57,   57,   57,   57,
   57,    0,   86,   87,   88,   89,   90,   91,    0,    0,
    0,    0,    0,    0,   74,   75,    0,    0,    0,   77,
   78,   79,   80,   82,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  105,    0,    0,    0,
    0,    0,    0,  113,  114,    0,  116,  117,  118,  119,
  120,  121,  122,  123,  124,  125,  126,  127,  128,    0,
  130,    0,    0,  105,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   86,   87,
   88,   89,   90,   91,  142,    0,    0,  145,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   65,   65,   65,   65,   65,   65,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   86,   87,   88,   89,   90,
   91,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   46,   41,   42,   43,   44,   45,   40,   47,   41,   42,
   43,   44,   45,   46,   47,   58,   33,   91,   49,   59,
   60,   61,   62,   40,   20,  264,   59,   60,   61,   62,
  125,  264,   41,   33,   40,   44,   46,   33,  263,  264,
   40,  123,   38,   41,  269,   91,   44,   43,   44,  264,
   33,   58,   41,   93,   43,   44,   45,   40,   91,   41,
   93,   43,   44,   45,   58,   41,   44,   33,  264,  268,
   59,   59,   61,   58,   40,   59,   72,   59,  123,   61,
  123,   91,   93,   58,  269,   33,   59,  264,   58,   85,
   40,  125,   40,   40,   40,   60,  264,   41,   42,   43,
   44,   45,   33,   47,   93,   40,   59,   41,  125,   40,
   62,   93,  143,  144,   44,   59,   41,   61,   59,   33,
  151,  123,   33,  276,  123,  125,   40,    0,  123,   40,
    0,   41,   41,   41,   41,  103,    5,   42,   -1,   -1,
   -1,   -1,  125,   -1,   -1,   59,   -1,   -1,   -1,   93,
   -1,   -1,   -1,   -1,   41,   42,   43,   44,   45,  125,
   47,   41,   42,   43,   44,   45,   -1,   47,   -1,  264,
   -1,   -1,   59,   60,   61,   62,   -1,  125,   -1,   59,
   60,   61,   62,   41,   42,   43,   -1,   45,   46,   47,
  264,  265,  266,  267,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   60,   -1,   62,   -1,   93,   -1,   -1,   -1,
   -1,  257,  258,   93,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,  257,  258,  259,  260,  261,  262,
  264,   -1,   -1,   91,  268,   -1,  270,  271,  272,  273,
  274,  275,   -1,  277,  278,  279,  280,  264,   -1,   -1,
   -1,  268,   -1,  270,  271,  272,  273,  274,  275,   -1,
  277,  278,  279,  280,  264,   -1,   -1,   -1,  268,   -1,
  270,  271,  272,  273,  274,  275,   -1,  277,  278,  279,
  280,  264,   -1,   -1,   -1,  268,   -1,  270,  271,  272,
  273,  274,  275,   -1,  277,  278,  279,  280,  264,   -1,
   -1,   -1,  268,   -1,  270,  271,  272,  273,  274,  275,
   -1,  277,  278,  279,  280,   -1,  264,   -1,   -1,   -1,
  268,   -1,  270,  271,  272,  273,  274,  275,   -1,  277,
  278,  279,  280,  264,   -1,   -1,   -1,  268,   -1,  270,
  271,  272,  273,  274,  275,   -1,  277,  278,  279,  280,
  264,   -1,   -1,  264,  268,   -1,   -1,  268,   -1,   -1,
   -1,   -1,   -1,   -1,  278,  279,  280,  278,  279,  280,
  257,  258,  259,  260,  261,  262,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   60,   -1,   62,   41,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   41,
   42,   43,   44,   45,   -1,   47,   -1,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   91,   59,   60,   61,
   62,   41,   42,   43,   44,   45,   -1,   47,   41,   42,
   43,   44,   45,   -1,   47,   -1,   -1,   -1,   91,   59,
   60,   61,   62,   -1,   -1,   -1,   59,   60,   61,   62,
   -1,   93,   -1,   -1,   -1,   -1,   41,   42,   43,   44,
   45,   -1,   47,   41,   42,   43,   44,   45,   -1,   47,
   -1,   -1,   -1,   93,   59,   60,   61,   62,   -1,   -1,
   93,   59,   60,   61,   62,   41,   42,   43,   44,   45,
   -1,   47,   41,   42,   43,   44,   45,   -1,   47,   -1,
   -1,   -1,   -1,   59,   60,   61,   62,   -1,   93,   -1,
   59,   60,   61,   62,   -1,   93,   42,   43,   -1,   45,
   46,   47,   42,   43,   -1,   45,   46,   47,   42,   43,
   -1,   45,   46,   47,   60,   61,   62,   93,   -1,   59,
   60,   -1,   62,   -1,   93,   59,   60,   -1,   62,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   59,   60,   -1,   62,
   -1,   91,   -1,   -1,   -1,   42,   43,   91,   45,   46,
   47,   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,   59,   60,   -1,   62,   42,   43,   91,   45,
   46,   47,   -1,   -1,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   59,   60,   -1,   62,  259,  260,  261,
  262,   46,   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,  259,
  260,  261,  262,   -1,   -1,   91,  259,  260,  261,  262,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   91,   42,   60,   -1,
   62,   46,   47,   -1,  259,  260,  261,  262,   60,   61,
   62,  259,  260,  261,  262,   60,   -1,   62,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   93,   -1,  259,  260,  261,  262,   -1,   -1,   91,
  259,  260,  261,  262,   -1,   -1,   91,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  257,  258,  259,
  260,  261,  262,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
   59,   60,   -1,   62,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,   60,   61,   62,   -1,
   42,   43,   91,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,   -1,   60,   -1,
   62,   -1,   41,   42,   43,   44,   45,   91,   47,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,
   59,   -1,   61,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   93,  257,  258,  259,  260,  261,
  262,   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,   -1,   -1,   54,   55,   -1,   -1,   -1,   59,
   60,   61,   62,   63,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   76,   -1,   -1,   -1,
   -1,   -1,   -1,   83,   84,   -1,   86,   87,   88,   89,
   90,   91,   92,   93,   94,   95,   96,   97,   98,   -1,
  100,   -1,   -1,  103,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  134,   -1,   -1,  137,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"Y","O","DISTINTO","IGUALDAD",
"MAYORIGUAL","MENORIGUAL","\"STRUCT\"","\"IDENTIFICADOR\"","\"INT\"",
"\"FLOAT\"","\"CHAR\"","\"LITERAL_ENTERO\"","\"VAR\"","\"PRINT\"","\"PRINTSP\"",
"\"PRINTLN\"","\"READ\"","\"RETURN\"","\"IF\"","\"ELSE\"","\"WHILE\"",
"\"LITERAL_REAL\"","\"LITERAL_CARACTER\"","\"CAST\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : definiciones",
"programa :",
"definiciones : definiciones definicion",
"definiciones : definicion",
"definicion : defVar",
"definicion : defStruct",
"definicion : defFuncion",
"defStruct : \"STRUCT\" \"IDENTIFICADOR\" '{' defCampos '}' ';'",
"defCampos : defCampos \"IDENTIFICADOR\" ':' tipo ';'",
"defCampos :",
"tipo : \"INT\"",
"tipo : \"FLOAT\"",
"tipo : \"CHAR\"",
"tipo : \"IDENTIFICADOR\"",
"tipo : '[' \"LITERAL_ENTERO\" ']' tipo",
"defFuncion : \"IDENTIFICADOR\" '(' parametrosOpt ')' tipoRetorno '{' defVarLocales sentencias '}'",
"defFuncion : \"IDENTIFICADOR\" '(' parametrosOpt ')' '{' defVarLocales sentencias '}'",
"tipoRetorno : ':' tipo",
"parametrosOpt : parametros",
"parametrosOpt :",
"parametros : parametros ',' \"IDENTIFICADOR\" ':' tipo",
"parametros : \"IDENTIFICADOR\" ':' tipo",
"defVarLocales : defVarLocales defVarLocal",
"defVarLocales :",
"defVarLocal : \"VAR\" \"IDENTIFICADOR\" ':' tipo ';'",
"defVar : \"VAR\" \"IDENTIFICADOR\" ':' tipo ';'",
"sentencias : sentencias sentencia",
"sentencias :",
"sentencia : expr '=' expr ';'",
"sentencia : \"PRINT\" expr ';'",
"sentencia : \"PRINTSP\" expr ';'",
"sentencia : \"PRINTLN\" expr ';'",
"sentencia : \"READ\" expr ';'",
"sentencia : \"RETURN\" expr ';'",
"sentencia : \"RETURN\" ';'",
"sentencia : \"IF\" '(' expr ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expr ')' '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
"sentencia : \"WHILE\" '(' expr ')' '{' sentencias '}'",
"sentencia : \"IDENTIFICADOR\" '(' listaExprSeparadorOpt ')' ';'",
"listaExprSeparadorOpt : listaExprSeparador",
"listaExprSeparadorOpt :",
"listaExprSeparador : listaExprSeparador ',' expr",
"listaExprSeparador : expr",
"expr : expr '+' expr",
"expr : expr '-' expr",
"expr : expr '*' expr",
"expr : expr '/' expr",
"expr : expr MAYORIGUAL expr",
"expr : expr MENORIGUAL expr",
"expr : expr '<' expr",
"expr : expr '>' expr",
"expr : expr DISTINTO expr",
"expr : expr IGUALDAD expr",
"expr : '!' expr",
"expr : expr Y expr",
"expr : expr O expr",
"expr : \"IDENTIFICADOR\"",
"expr : \"LITERAL_ENTERO\"",
"expr : \"LITERAL_REAL\"",
"expr : \"LITERAL_CARACTER\"",
"expr : '(' expr ')'",
"expr : \"CAST\" '<' tipo '>' expr",
"expr : expr '[' expr ']'",
"expr : expr '.' \"IDENTIFICADOR\"",
"expr : \"IDENTIFICADOR\" '(' listaExprSeparadorOpt ')'",
};

//#line 157 "sintac.y"

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

//#line 549 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 35 "sintac.y"
{ raiz = new Programa(val_peek(0)); }
break;
case 2:
//#line 36 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 3:
//#line 39 "sintac.y"
{ yyval = val_peek(1); ((List)yyval).add(val_peek(0)); }
break;
case 4:
//#line 40 "sintac.y"
{ yyval = new ArrayList<Definicion>(); ((List)yyval).add(val_peek(0)); }
break;
case 5:
//#line 43 "sintac.y"
{ yyval = val_peek(0); }
break;
case 6:
//#line 44 "sintac.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 45 "sintac.y"
{ yyval = val_peek(0); }
break;
case 8:
//#line 48 "sintac.y"
{ yyval = new DefStruct(val_peek(4), val_peek(2)); }
break;
case 9:
//#line 51 "sintac.y"
{ yyval = val_peek(4); ((List)yyval).add(new DefCampo(val_peek(3), val_peek(1))); }
break;
case 10:
//#line 52 "sintac.y"
{ yyval = new ArrayList<DefCampo>(); }
break;
case 11:
//#line 55 "sintac.y"
{ yyval = new TipoEntero(); 	}
break;
case 12:
//#line 56 "sintac.y"
{ yyval = new TipoReal(); 		}
break;
case 13:
//#line 57 "sintac.y"
{ yyval = new TipoCaracter(); 	}
break;
case 14:
//#line 58 "sintac.y"
{ yyval = new TipoStruct(val_peek(0)); 	}
break;
case 15:
//#line 59 "sintac.y"
{ yyval = new TipoArray(val_peek(2), val_peek(0)); }
break;
case 16:
//#line 62 "sintac.y"
{ yyval = new DefFuncion(val_peek(8), val_peek(6), val_peek(4), val_peek(2), val_peek(1)); }
break;
case 17:
//#line 63 "sintac.y"
{ yyval = new DefFuncion(val_peek(7), val_peek(5), new TipoVoid(), val_peek(2), val_peek(1)); }
break;
case 18:
//#line 66 "sintac.y"
{ yyval = val_peek(0); }
break;
case 19:
//#line 69 "sintac.y"
{ yyval = val_peek(0); }
break;
case 20:
//#line 70 "sintac.y"
{ yyval = new ArrayList<DefVariable>(); }
break;
case 21:
//#line 73 "sintac.y"
{ yyval = val_peek(4); ((List)yyval).add(new DefVariable(val_peek(0), val_peek(2), ContextoVariable.PARAMETRO)); }
break;
case 22:
//#line 74 "sintac.y"
{ yyval = new ArrayList<DefVariable>(); ((List)yyval).add(new DefVariable(val_peek(0), val_peek(2), ContextoVariable.PARAMETRO)); }
break;
case 23:
//#line 77 "sintac.y"
{ yyval = val_peek(1); ((List)yyval).add(val_peek(0)); }
break;
case 24:
//#line 78 "sintac.y"
{ yyval = new ArrayList<DefVariable>(); }
break;
case 25:
//#line 81 "sintac.y"
{ yyval = new DefVariable( val_peek(1), val_peek(3), ContextoVariable.LOCAL); }
break;
case 26:
//#line 84 "sintac.y"
{ yyval = new DefVariable(val_peek(1), val_peek(3), ContextoVariable.GLOBAL); }
break;
case 27:
//#line 88 "sintac.y"
{ yyval = val_peek(1); ((List)yyval).add(val_peek(0)); }
break;
case 28:
//#line 89 "sintac.y"
{ yyval = new ArrayList<Sentencia>(); }
break;
case 29:
//#line 92 "sintac.y"
{ yyval = new Asignacion(val_peek(3), val_peek(1)); }
break;
case 30:
//#line 94 "sintac.y"
{ yyval = new Escritura(val_peek(1), "print"); }
break;
case 31:
//#line 95 "sintac.y"
{ yyval = new Escritura(val_peek(1), "printsp"); }
break;
case 32:
//#line 96 "sintac.y"
{ yyval = new Escritura(val_peek(1), "println"); }
break;
case 33:
//#line 98 "sintac.y"
{ yyval = new Lectura(val_peek(1)); }
break;
case 34:
//#line 100 "sintac.y"
{ yyval = new Return(val_peek(1)); }
break;
case 35:
//#line 101 "sintac.y"
{ yyval = new Return(null); }
break;
case 36:
//#line 103 "sintac.y"
{ yyval = new Ifelse(val_peek(4), val_peek(1), null); }
break;
case 37:
//#line 104 "sintac.y"
{ yyval = new Ifelse(val_peek(8), val_peek(5), val_peek(1)); }
break;
case 38:
//#line 106 "sintac.y"
{ yyval = new While(val_peek(4), val_peek(1)); }
break;
case 39:
//#line 109 "sintac.y"
{ yyval = new InvFuncSent(val_peek(4), val_peek(2)); }
break;
case 40:
//#line 112 "sintac.y"
{ yyval = val_peek(0); }
break;
case 41:
//#line 113 "sintac.y"
{ yyval = new ArrayList<Expresion>(); }
break;
case 42:
//#line 116 "sintac.y"
{ yyval = val_peek(2); ((List)yyval).add(val_peek(0)); }
break;
case 43:
//#line 117 "sintac.y"
{ yyval = new ArrayList<Expresion>(); ((List)yyval).add(val_peek(0)); }
break;
case 44:
//#line 120 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "+" ,val_peek(0)); }
break;
case 45:
//#line 121 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "-" ,val_peek(0)); }
break;
case 46:
//#line 122 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "*" ,val_peek(0)); }
break;
case 47:
//#line 123 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "/" ,val_peek(0)); }
break;
case 48:
//#line 125 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), ">=" ,val_peek(0)); }
break;
case 49:
//#line 126 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "<=" ,val_peek(0)); }
break;
case 50:
//#line 127 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "<" ,val_peek(0)); }
break;
case 51:
//#line 128 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), ">" ,val_peek(0)); }
break;
case 52:
//#line 130 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "!=" ,val_peek(0)); }
break;
case 53:
//#line 131 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "==" ,val_peek(0)); }
break;
case 54:
//#line 133 "sintac.y"
{ yyval = new ExpresionUnariaNegacion(val_peek(0)); }
break;
case 55:
//#line 134 "sintac.y"
{ yyval = new ExpresionLogica(val_peek(2), "&&" ,val_peek(0)); }
break;
case 56:
//#line 135 "sintac.y"
{ yyval = new ExpresionLogica(val_peek(2), "||" ,val_peek(0)); }
break;
case 57:
//#line 137 "sintac.y"
{ yyval = new Variable(val_peek(0)); }
break;
case 58:
//#line 138 "sintac.y"
{ yyval = new LiteralInt(val_peek(0)); }
break;
case 59:
//#line 139 "sintac.y"
{ yyval = new LiteralReal(val_peek(0)); }
break;
case 60:
//#line 140 "sintac.y"
{ yyval = new LiteralCaracter(val_peek(0)); }
break;
case 61:
//#line 142 "sintac.y"
{ yyval = val_peek(1); }
break;
case 62:
//#line 144 "sintac.y"
{ yyval = new Cast(val_peek(2), val_peek(0)); }
break;
case 63:
//#line 147 "sintac.y"
{ yyval = new AccesoArray(val_peek(3), val_peek(1)); }
break;
case 64:
//#line 150 "sintac.y"
{ yyval = new AccesoStruct(val_peek(2), val_peek(0)); }
break;
case 65:
//#line 153 "sintac.y"
{ yyval = new InvFuncExpr(val_peek(3), val_peek(1)); }
break;
//#line 958 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################

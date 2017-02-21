//### This file created by BYACC 1.8(/Java extension  1.14)
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
public final static short READ=270;
public final static short RETURN=271;
public final static short IF=272;
public final static short ELSE=273;
public final static short WHILE=274;
public final static short PRINT=275;
public final static short PRINTSP=276;
public final static short PRINTLN=277;
public final static short LITERAL_REAL=278;
public final static short LITERAL_CARACTER=279;
public final static short CAST=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    2,    4,    6,    6,
    7,    7,    7,    7,    7,    8,    8,    5,    5,   10,
   10,    9,    9,   11,   11,    3,   12,   12,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   15,   15,   15,
   16,   16,   17,   17,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,    1,    6,    5,    0,
    1,    1,    1,    1,    1,    4,    3,    9,    8,    2,
    0,    5,    3,    2,    0,    5,    2,    0,    4,    2,
    3,    3,    2,    7,   11,    7,    2,    2,    2,    2,
    4,    3,    3,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    3,    3,    1,    1,    1,
    1,    3,    5,    4,    3,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    4,    5,    6,    7,    0,
    0,    0,    3,   10,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   14,   11,   12,   13,    0,
   15,    0,    0,   20,   25,   23,    0,    0,    0,   26,
    0,    8,    0,   25,    0,    0,    0,   24,    0,    0,
   22,   16,    9,    0,    0,    0,   19,   59,    0,    0,
    0,    0,    0,    0,    0,   60,   61,    0,   27,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   33,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   30,   37,   18,    0,   62,   42,    0,    0,   31,
   32,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   65,    0,    0,
    0,    0,    0,    0,    0,   64,   29,   66,    0,   28,
   28,    0,    0,    0,    0,   36,    0,   28,    0,   35,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   19,   30,   31,   17,   21,
   43,   49,   69,   70,   71,   72,  109,
};
final static short yysindex[] = {                      -220,
 -238,   -5, -218,    0, -220,    0,    0,    0,    0,  -70,
  -40,   -3,    0,    0,    2,   14,   -4,  -75, -106,  -75,
  -59,  -75,    2, -188, -190,    0,    0,    0,    0,   20,
    0,   24,   25,    0,    0,    0,  -35,   31,    3,    0,
  -75,    0, -179,    0,  -75,  -75,   32,    0,  -33, -179,
    0,    0,    0,   88,   88,   54,    0,    0,   88,   40,
   55,   58,   88,   88,   88,    0,    0,   41,    0,  506,
   43,   45,  -16,   60,  -10,  141,   75,  516,    0,  588,
   88,   88,  709,  709,  709,  -75,   88,   88,   88,   88,
   88,   88,   88,   88,   88,   88,   88,   88,   88, -159,
   88,    0,    0,    0,   88,    0,    0,  709,   -2,    0,
    0,  334,  344,   44,  -10,  -10,   -8,   -8,   -8,   -8,
  737,  737,  528,  528,   -8,   -8,  598,    0,  623,    6,
    0,   88,  -12,  -11,   88,    0,    0,    0,  709,    0,
    0,   -8,    1,   18, -166,    0,   -9,    0,   35,    0,
};
final static short yyrindex[] = {                       113,
    0,    0,    0,    0,  117,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    8,    0,    0,    0,    0,    0,
    0,    0,    8,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   53,    0,    0,    4,    0,    0,    0,   53,
    0,    0,    0,    0,    0,  658,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -32,  -39,    0,    0,    0,    0,    0,
    0,    0,   59,   61,   20,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   21,    0,    0,
    0,    0,    0,    0,  103,  112,  367,  377,  402,  412,
   26,  372,  539,  751,  437,  447,    0,    0,    0,    0,
  482,    0,    0,    0,    0,    0,    0,    0,   33,    0,
    0,  472,    0,    0,   70,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  119,  -25,    0,    0,    0,   11,    0,    0,  102,
   85,  -18,    0,  809,    0,    0,   27,
};
final static int YYTABLESIZE=999;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
   15,   55,   55,   55,   55,   55,   55,   55,   58,   58,
   58,   58,   58,   58,   58,   25,   54,   48,   33,   55,
   55,   55,   55,   55,   48,   10,   58,   58,   58,   58,
   34,   73,   36,   54,   11,  100,   23,  100,  131,   24,
   55,  132,    1,    2,   17,   12,  138,   17,    3,  132,
   54,   47,   14,   55,   18,   51,   52,   55,   58,   20,
   58,   44,   17,   35,   44,   17,   45,   54,   45,   45,
   45,   22,   54,   43,   55,   38,   43,   39,   40,   55,
   99,   41,   99,   42,   45,   28,   45,   44,   45,    3,
   53,   57,   28,   77,   81,   46,  114,   82,   79,  105,
   86,  102,   34,  103,  128,  135,  147,   54,  104,   34,
  140,  141,    2,  148,   55,  107,    1,   38,   45,   39,
   54,  143,  144,   13,   37,  145,   17,   55,   50,  149,
   21,  130,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  146,   56,   56,   56,   56,   56,    0,   56,
    0,    0,   57,   57,   57,   57,   57,   32,   57,  150,
    0,   56,   56,   56,   56,    0,    0,    0,    0,    0,
   57,   57,   57,   57,    0,    0,    0,   28,    0,    0,
    0,  106,   95,   93,    0,   94,  100,   96,   26,   27,
   28,   29,    0,    0,   34,   56,    0,    0,    0,    0,
   98,    0,   97,    0,   57,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   55,   55,   55,
   55,   55,   55,   16,   58,   58,   58,   58,   58,   58,
   56,   99,    0,    0,   58,    0,   59,   60,   61,    0,
   62,   63,   64,   65,   66,   67,   68,   56,   87,   88,
    0,   58,    0,   59,   60,   61,    0,   62,   63,   64,
   65,   66,   67,   68,   56,    0,    0,    0,   58,    0,
   59,   60,   61,    0,   62,   63,   64,   65,   66,   67,
   68,   56,    0,    0,    0,   58,    0,   59,   60,   61,
    0,   62,   63,   64,   65,   66,   67,   68,   56,    0,
    0,    0,   58,   74,   59,   60,   61,   58,   62,   63,
   64,   65,   66,   67,   68,    0,   28,   66,   67,   68,
   28,    0,   28,   28,   28,    0,   28,   28,   28,   28,
   28,   28,   28,   34,    0,    0,    0,   34,   74,   34,
   34,   34,   58,   34,   34,   34,   34,   34,   34,   34,
    0,   74,   66,   67,   68,   58,    0,    0,    0,   56,
   56,   56,   56,   56,   56,   66,   67,   68,   57,   57,
   57,   57,   57,   57,  133,   95,   93,    0,   94,  100,
   96,    0,    0,    0,  134,   95,   93,    0,   94,  100,
   96,    0,    0,   98,    0,   97,    0,   87,   88,   89,
   90,   91,   92,   98,    0,   97,    0,   53,   53,   53,
   53,   53,   46,   53,   46,   46,   46,   54,   54,   54,
   54,   54,    0,   54,   99,   53,   53,   53,   53,    0,
   46,    0,   46,    0,   99,   54,   54,   54,   54,    0,
    0,    0,   49,   49,   49,   49,   49,    0,   49,    0,
    0,    0,   50,   50,   50,   50,   50,    0,   50,   53,
   49,   49,   49,   49,   46,    0,    0,    0,    0,   54,
   50,   50,   50,   50,    0,    0,    0,   52,   52,   52,
   52,   52,    0,   52,    0,    0,    0,   51,   51,   51,
   51,   51,    0,   51,   49,   52,   52,   52,   52,    0,
    0,    0,    0,    0,   50,   51,   51,   51,   51,    0,
    0,    0,   63,   63,   63,   63,   63,    0,   63,    0,
    0,    0,    0,   66,   66,    0,   66,   66,   66,   52,
   63,   63,   63,   63,    0,    0,    0,    0,    0,   51,
   41,   66,   66,   66,    0,    0,    0,   95,   93,    0,
   94,  100,   96,    0,    0,    0,    0,   95,   93,    0,
   94,  100,   96,    0,   63,   98,  101,   97,    0,    0,
    0,    0,   66,  100,  110,   98,    0,   97,    0,   47,
   47,   47,   47,   47,    0,   47,    0,   98,    0,   97,
   87,   88,   89,   90,   91,   92,   99,   47,    0,   47,
   87,   88,   89,   90,   91,   92,   99,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   99,    0,
    0,    0,    0,    0,    0,   53,   53,   53,   53,   95,
   93,   47,   94,  100,   96,   54,   54,   54,   54,   95,
   93,    0,   94,  100,   96,    0,  111,   98,    0,   97,
    0,    0,    0,    0,    0,    0,    0,   98,    0,   97,
   49,   49,   49,   49,   95,   93,    0,   94,  100,   96,
   50,   50,   50,   50,    0,    0,    0,    0,   99,    0,
    0,  137,   98,    0,   97,    0,    0,    0,   99,    0,
  136,    0,    0,    0,    0,   52,   52,   52,   52,   58,
   58,    0,   58,   58,   58,   51,   51,   51,   51,    0,
    0,    0,    0,   99,    0,    0,    0,   58,   58,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   63,   63,   63,   63,    0,    0,    0,    0,   66,   66,
   66,   66,   66,   66,    0,    0,    0,    0,   58,    0,
   95,   93,    0,   94,  100,   96,    0,    0,    0,    0,
    0,    0,   87,   88,   89,   90,   91,   92,   98,    0,
   97,    0,   87,   88,   89,   90,   91,   92,   95,    0,
    0,    0,  100,   96,   87,   88,   89,   90,   91,   92,
    0,   48,   48,   48,   48,   48,   98,   48,   97,   99,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   48,
    0,   48,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   99,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   48,   87,   88,   89,   90,   91,   92,
    0,    0,    0,    0,   87,   88,   89,   90,   91,   92,
    0,    0,   75,   76,    0,    0,    0,   78,   80,    0,
    0,   83,   84,   85,    0,    0,    0,    0,    0,   87,
   88,   89,   90,   91,   92,  108,    0,    0,    0,  112,
  113,    0,    0,    0,    0,  115,  116,  117,  118,  119,
  120,  121,  122,  123,  124,  125,  126,  127,    0,  129,
    0,    0,    0,  108,   58,   58,   58,   58,   58,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  139,    0,    0,  142,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   87,   88,   89,   90,   91,
   92,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   87,   88,   89,   90,   91,   92,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   41,   41,   42,   43,   44,   45,   40,   47,   41,   42,
   43,   44,   45,   46,   47,   91,   33,   43,  125,   59,
   60,   61,   62,   40,   50,  264,   59,   60,   61,   62,
   20,   50,   22,   33,   40,   46,   41,   46,   41,   44,
   40,   44,  263,  264,   41,  264,   41,   44,  269,   44,
   33,   41,  123,   93,   58,   45,   46,   40,   91,   58,
   93,   41,   59,  123,   44,   62,   41,   33,   43,   44,
   45,   58,   33,   41,   40,  264,   44,  268,   59,   40,
   91,   58,   91,   59,   59,   33,   61,  123,   58,  269,
   59,  125,   40,   40,   40,   93,   86,   40,   59,   40,
   60,   59,   33,   59,  264,   62,  273,   33,  125,   40,
  123,  123,    0,  123,   40,   41,    0,   59,   93,   59,
   33,  140,  141,    5,   23,  125,  123,   40,   44,  148,
  123,  105,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  125,   41,   42,   43,   44,   45,   -1,   47,
   -1,   -1,   41,   42,   43,   44,   45,  264,   47,  125,
   -1,   59,   60,   61,   62,   -1,   -1,   -1,   -1,   -1,
   59,   60,   61,   62,   -1,   -1,   -1,  125,   -1,   -1,
   -1,   41,   42,   43,   -1,   45,   46,   47,  264,  265,
  266,  267,   -1,   -1,  125,   93,   -1,   -1,   -1,   -1,
   60,   -1,   62,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  264,  257,  258,  259,  260,  261,  262,
  264,   91,   -1,   -1,  268,   -1,  270,  271,  272,   -1,
  274,  275,  276,  277,  278,  279,  280,  264,  257,  258,
   -1,  268,   -1,  270,  271,  272,   -1,  274,  275,  276,
  277,  278,  279,  280,  264,   -1,   -1,   -1,  268,   -1,
  270,  271,  272,   -1,  274,  275,  276,  277,  278,  279,
  280,  264,   -1,   -1,   -1,  268,   -1,  270,  271,  272,
   -1,  274,  275,  276,  277,  278,  279,  280,  264,   -1,
   -1,   -1,  268,  264,  270,  271,  272,  268,  274,  275,
  276,  277,  278,  279,  280,   -1,  264,  278,  279,  280,
  268,   -1,  270,  271,  272,   -1,  274,  275,  276,  277,
  278,  279,  280,  264,   -1,   -1,   -1,  268,  264,  270,
  271,  272,  268,  274,  275,  276,  277,  278,  279,  280,
   -1,  264,  278,  279,  280,  268,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,  278,  279,  280,  257,  258,
  259,  260,  261,  262,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   60,   -1,   62,   -1,  257,  258,  259,
  260,  261,  262,   60,   -1,   62,   -1,   41,   42,   43,
   44,   45,   41,   47,   43,   44,   45,   41,   42,   43,
   44,   45,   -1,   47,   91,   59,   60,   61,   62,   -1,
   59,   -1,   61,   -1,   91,   59,   60,   61,   62,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   93,
   59,   60,   61,   62,   93,   -1,   -1,   -1,   -1,   93,
   59,   60,   61,   62,   -1,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,   -1,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,   93,   59,   60,   61,   62,   -1,
   -1,   -1,   -1,   -1,   93,   59,   60,   61,   62,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   93,
   59,   60,   61,   62,   -1,   -1,   -1,   -1,   -1,   93,
   59,   60,   61,   62,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   93,   60,   61,   62,   -1,   -1,
   -1,   -1,   91,   46,   59,   60,   -1,   62,   -1,   41,
   42,   43,   44,   45,   -1,   47,   -1,   60,   -1,   62,
  257,  258,  259,  260,  261,  262,   91,   59,   -1,   61,
  257,  258,  259,  260,  261,  262,   91,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,
   -1,   -1,   -1,   -1,   -1,  259,  260,  261,  262,   42,
   43,   93,   45,   46,   47,  259,  260,  261,  262,   42,
   43,   -1,   45,   46,   47,   -1,   59,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,
  259,  260,  261,  262,   42,   43,   -1,   45,   46,   47,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   91,   -1,
   -1,   59,   60,   -1,   62,   -1,   -1,   -1,   91,   -1,
   93,   -1,   -1,   -1,   -1,  259,  260,  261,  262,   42,
   43,   -1,   45,   46,   47,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   60,   61,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   91,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,   60,   -1,
   62,   -1,  257,  258,  259,  260,  261,  262,   42,   -1,
   -1,   -1,   46,   47,  257,  258,  259,  260,  261,  262,
   -1,   41,   42,   43,   44,   45,   60,   47,   62,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,
   -1,   61,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   93,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
   -1,   -1,   54,   55,   -1,   -1,   -1,   59,   60,   -1,
   -1,   63,   64,   65,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   77,   -1,   -1,   -1,   81,
   82,   -1,   -1,   -1,   -1,   87,   88,   89,   90,   91,
   92,   93,   94,   95,   96,   97,   98,   99,   -1,  101,
   -1,   -1,   -1,  105,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  132,   -1,   -1,  135,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
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
"\"FLOAT\"","\"CHAR\"","\"LITERAL_ENTERO\"","\"VAR\"","\"READ\"","\"RETURN\"",
"\"IF\"","\"ELSE\"","\"WHILE\"","\"PRINT\"","\"PRINTSP\"","\"PRINTLN\"",
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
"tipo : tipoArray",
"tipoArray : '[' \"LITERAL_ENTERO\" ']' tipo",
"tipoArray : '[' \"LITERAL_ENTERO\" ']'",
"defFuncion : \"IDENTIFICADOR\" '(' parametros ')' tipoRetornoOpt '{' defVarLocales sentencias '}'",
"defFuncion : \"IDENTIFICADOR\" '(' ')' tipoRetornoOpt '{' defVarLocales sentencias '}'",
"tipoRetornoOpt : ':' tipo",
"tipoRetornoOpt :",
"parametros : parametros ',' \"IDENTIFICADOR\" ':' tipo",
"parametros : \"IDENTIFICADOR\" ':' tipo",
"defVarLocales : defVarLocales defVar",
"defVarLocales :",
"defVar : \"VAR\" \"IDENTIFICADOR\" ':' tipo ';'",
"sentencias : sentencias sentencia",
"sentencias :",
"sentencia : expr '=' expr ';'",
"sentencia : escritura ';'",
"sentencia : \"READ\" expr ';'",
"sentencia : \"RETURN\" expr ';'",
"sentencia : \"RETURN\" ';'",
"sentencia : \"IF\" '(' expr ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expr ')' '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
"sentencia : \"WHILE\" '(' expr ')' '{' sentencias '}'",
"sentencia : invocacionFuncion ';'",
"escritura : \"PRINT\" expr",
"escritura : \"PRINTSP\" expr",
"escritura : \"PRINTLN\" expr",
"invocacionFuncion : \"IDENTIFICADOR\" '(' listaExprSeparador ')'",
"invocacionFuncion : \"IDENTIFICADOR\" '(' ')'",
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
"expr : \"IDENTIFICADOR\" '(' listaExprSeparador ')'",
};

//#line 149 "sintac.y"

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

//#line 537 "Parser.java"
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
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
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

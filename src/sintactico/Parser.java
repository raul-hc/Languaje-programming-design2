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
    7,    7,    7,    7,    7,    8,    5,    5,   10,    9,
    9,   13,   13,   11,   11,    3,   12,   12,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   16,   16,   16,
   17,   17,   18,   18,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,    1,    6,    5,    0,
    1,    1,    1,    1,    1,    4,    9,    8,    2,    1,
    0,    5,    3,    2,    0,    5,    2,    0,    4,    2,
    3,    3,    2,    7,   11,    7,    2,    2,    2,    2,
    4,    3,    3,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    3,    3,    1,    1,    1,
    1,    3,    5,    4,    3,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    4,    5,    6,    7,    0,
    0,    0,    3,   10,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   14,   11,   12,   13,    0,   15,    0,
    0,   23,   25,    0,    0,    0,    0,   26,    0,    8,
    0,   19,   25,    0,    0,    0,   24,    0,    0,   22,
   16,    9,    0,    0,    0,   18,   59,    0,    0,    0,
    0,    0,    0,    0,   60,   61,    0,   27,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   33,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   30,   37,   17,    0,   62,   42,    0,    0,   31,   32,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   65,    0,    0,    0,
    0,    0,    0,    0,   64,   29,   66,    0,   28,   28,
    0,    0,    0,    0,   36,    0,   28,    0,   35,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   19,   28,   29,   16,   35,
   41,   48,   17,   68,   69,   70,   71,  108,
};
final static short yysindex[] = {                      -227,
 -238,   -7, -226,    0, -227,    0,    0,    0,    0,  -83,
 -217,    6,    0,    0,    7,   21,   19,  -75, -106,  -75,
  -40, -198, -196,    0,    0,    0,    0,   15,    0,   20,
   22,    0,    0,  -75,  -46,   24,  -14,    0,  -75,    0,
 -185,    0,    0,  -75,  -75,   29,    0,  -33, -185,    0,
    0,    0,   88,   88,   49,    0,    0,   88,   40,   51,
   54,   88,   88,   88,    0,    0,   36,    0,  506,   38,
   39,  -16,   55,  -15,  141,   75,  516,    0,  588,   88,
   88,  709,  709,  709,  -75,   88,   88,   88,   88,   88,
   88,   88,   88,   88,   88,   88,   88,   88, -164,   88,
    0,    0,    0,   88,    0,    0,  709,    4,    0,    0,
  334,  344,   42,  -15,  -15,  -45,  -45,  -45,  -45,  737,
  737,  528,  528,  -45,  -45,  598,    0,  623,   11,    0,
   88,  -22,  -21,   88,    0,    0,    0,  709,    0,    0,
  -45,    1,   18, -168,    0,  -12,    0,   35,    0,
};
final static short yyrindex[] = {                       106,
    0,    0,    0,    0,  107,    0,    0,    0,    0,    0,
   21,    0,    0,    0,    0,    0,   71,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   53,    0,    0,    0,    0,    0,    0,    0,   53,    0,
    0,    0,    0,    0,  658,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -32,  -39,    0,    0,    0,    0,    0,    0,
    0,   15,   58,   22,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   12,    0,    0,    0,
    0,    0,    0,  103,  112,  367,  377,  402,  412,   26,
  372,  539,  751,  437,  447,    0,    0,    0,    0,  482,
    0,    0,    0,    0,    0,    0,    0,   16,    0,    0,
  472,    0,    0,   70,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  108,   -6,    0,    0,    0,    5,    0,    0,    0,
   77,  -17,    0,    0,  810,    0,    0,   10,
};
final static int YYTABLESIZE=999;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
   99,   55,   55,   55,   55,   55,   54,   55,   58,   58,
   58,   58,   58,   58,   58,   23,   53,   34,   31,   55,
   55,   55,   55,   54,   32,   10,   58,   58,   58,   58,
   99,   72,   11,   53,   47,    1,    2,   12,   42,   14,
   54,    3,   47,   46,  130,   98,   15,  131,   50,   51,
   53,  137,   44,   55,  131,   44,   43,   54,   58,   43,
   58,   21,   22,   18,   20,   36,   45,   53,   45,   45,
   45,   37,   53,   38,   54,   98,   43,   39,   45,   54,
   40,   44,   33,    3,   45,   28,   45,   52,   76,  113,
   80,   56,   28,   81,  104,   85,  101,  102,   78,  127,
  139,  140,   34,  134,  146,    2,    1,   53,  103,   34,
  147,   20,   13,  129,   54,  106,   39,    0,   45,   49,
   53,  142,  143,    0,    0,  144,    0,   54,    0,  148,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  145,   56,   56,   56,   56,   56,    0,   56,
    0,    0,   57,   57,   57,   57,   57,   30,   57,  149,
    0,   56,   56,   56,   56,    0,    0,    0,    0,    0,
   57,   57,   57,   57,    0,    0,    0,   28,    0,    0,
    0,  105,   94,   92,    0,   93,   99,   95,   24,   25,
   26,   27,    0,    0,   34,   56,    0,    0,    0,    0,
   97,    0,   96,    0,   57,    0,    0,    0,    0,    0,
    0,   86,   87,    0,    0,    0,    0,   55,   55,   55,
   55,   55,   55,    0,   58,   58,   58,   58,   58,   58,
   55,   98,    0,    0,   57,    0,   58,   59,   60,    0,
   61,   62,   63,   64,   65,   66,   67,   55,    0,    0,
    0,   57,    0,   58,   59,   60,    0,   61,   62,   63,
   64,   65,   66,   67,   55,    0,    0,    0,   57,    0,
   58,   59,   60,    0,   61,   62,   63,   64,   65,   66,
   67,   55,    0,    0,    0,   57,    0,   58,   59,   60,
    0,   61,   62,   63,   64,   65,   66,   67,   55,    0,
    0,    0,   57,   73,   58,   59,   60,   57,   61,   62,
   63,   64,   65,   66,   67,    0,   28,   65,   66,   67,
   28,    0,   28,   28,   28,    0,   28,   28,   28,   28,
   28,   28,   28,   34,    0,    0,    0,   34,   73,   34,
   34,   34,   57,   34,   34,   34,   34,   34,   34,   34,
    0,   73,   65,   66,   67,   57,    0,    0,    0,   56,
   56,   56,   56,   56,   56,   65,   66,   67,   57,   57,
   57,   57,   57,   57,  132,   94,   92,    0,   93,   99,
   95,    0,    0,    0,  133,   94,   92,    0,   93,   99,
   95,    0,    0,   97,    0,   96,    0,   86,   87,   88,
   89,   90,   91,   97,    0,   96,    0,   53,   53,   53,
   53,   53,   46,   53,   46,   46,   46,   54,   54,   54,
   54,   54,    0,   54,   98,   53,   53,   53,   53,    0,
   46,    0,   46,    0,   98,   54,   54,   54,   54,    0,
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
   41,   66,   66,   66,    0,    0,    0,   94,   92,    0,
   93,   99,   95,    0,    0,    0,    0,   94,   92,    0,
   93,   99,   95,    0,   63,   97,  100,   96,    0,    0,
    0,    0,   66,   99,  109,   97,    0,   96,    0,   47,
   47,   47,   47,   47,    0,   47,    0,   97,    0,   96,
   86,   87,   88,   89,   90,   91,   98,   47,    0,   47,
   86,   87,   88,   89,   90,   91,   98,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   98,    0,
    0,    0,    0,    0,    0,   53,   53,   53,   53,   94,
   92,   47,   93,   99,   95,   54,   54,   54,   54,   94,
   92,    0,   93,   99,   95,    0,  110,   97,    0,   96,
    0,    0,    0,    0,    0,    0,    0,   97,    0,   96,
   49,   49,   49,   49,   94,   92,    0,   93,   99,   95,
   50,   50,   50,   50,    0,    0,    0,    0,   98,    0,
    0,  136,   97,    0,   96,    0,    0,    0,   98,    0,
  135,    0,    0,    0,    0,   52,   52,   52,   52,   58,
   58,    0,   58,   58,   58,   51,   51,   51,   51,    0,
    0,    0,    0,   98,    0,    0,    0,   58,   58,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   63,   63,   63,   63,    0,    0,    0,    0,   66,   66,
   66,   66,   66,   66,    0,    0,    0,    0,   58,    0,
   94,   92,    0,   93,   99,   95,    0,    0,    0,    0,
    0,    0,   86,   87,   88,   89,   90,   91,   97,    0,
   96,    0,   86,   87,   88,   89,   90,   91,   94,    0,
    0,    0,   99,   95,   86,   87,   88,   89,   90,   91,
    0,   48,   48,   48,   48,   48,   97,   48,   96,   98,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   48,
    0,   48,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   98,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   48,   86,   87,   88,   89,   90,   91,
    0,    0,    0,    0,   86,   87,   88,   89,   90,   91,
    0,    0,   74,   75,    0,    0,    0,   77,   79,    0,
    0,   82,   83,   84,    0,    0,    0,    0,    0,   86,
   87,   88,   89,   90,   91,  107,    0,    0,    0,  111,
  112,    0,    0,    0,    0,  114,  115,  116,  117,  118,
  119,  120,  121,  122,  123,  124,  125,  126,    0,  128,
    0,    0,    0,  107,   58,   58,   58,   58,   58,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  138,    0,    0,  141,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   86,   87,   88,   89,   90,
   91,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   86,   87,   88,   89,   90,   91,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   46,   41,   42,   43,   44,   45,   40,   47,   41,   42,
   43,   44,   45,   46,   47,   91,   33,   58,  125,   59,
   60,   61,   62,   40,   20,  264,   59,   60,   61,   62,
   46,   49,   40,   33,   41,  263,  264,  264,   34,  123,
   40,  269,   49,   39,   41,   91,  264,   44,   44,   45,
   33,   41,   41,   93,   44,   44,   41,   40,   91,   44,
   93,   41,   44,   58,   58,  264,   41,   33,   43,   44,
   45,  268,   33,   59,   40,   91,  123,   58,   93,   40,
   59,   58,  123,  269,   59,   33,   61,   59,   40,   85,
   40,  125,   40,   40,   40,   60,   59,   59,   59,  264,
  123,  123,   33,   62,  273,    0,    0,   33,  125,   40,
  123,   41,    5,  104,   40,   41,   59,   -1,   93,   43,
   33,  139,  140,   -1,   -1,  125,   -1,   40,   -1,  147,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  125,   41,   42,   43,   44,   45,   -1,   47,
   -1,   -1,   41,   42,   43,   44,   45,  264,   47,  125,
   -1,   59,   60,   61,   62,   -1,   -1,   -1,   -1,   -1,
   59,   60,   61,   62,   -1,   -1,   -1,  125,   -1,   -1,
   -1,   41,   42,   43,   -1,   45,   46,   47,  264,  265,
  266,  267,   -1,   -1,  125,   93,   -1,   -1,   -1,   -1,
   60,   -1,   62,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,  257,  258,  259,  260,  261,  262,
  264,   91,   -1,   -1,  268,   -1,  270,  271,  272,   -1,
  274,  275,  276,  277,  278,  279,  280,  264,   -1,   -1,
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
   -1,   -1,   53,   54,   -1,   -1,   -1,   58,   59,   -1,
   -1,   62,   63,   64,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   76,   -1,   -1,   -1,   80,
   81,   -1,   -1,   -1,   -1,   86,   87,   88,   89,   90,
   91,   92,   93,   94,   95,   96,   97,   98,   -1,  100,
   -1,   -1,   -1,  104,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  131,   -1,   -1,  134,   -1,   -1,   -1,   -1,   -1,   -1,
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
"defFuncion : \"IDENTIFICADOR\" '(' parametrosOpt ')' tipoRetorno '{' defVarLocales sentencias '}'",
"defFuncion : \"IDENTIFICADOR\" '(' parametrosOpt ')' '{' defVarLocales sentencias '}'",
"tipoRetorno : ':' tipo",
"parametrosOpt : parametros",
"parametrosOpt :",
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

//#line 154 "sintac.y"

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
{ yyval = new TipoEntero(); }
break;
case 12:
//#line 56 "sintac.y"
{ yyval = new TipoReal(); }
break;
case 13:
//#line 57 "sintac.y"
{ yyval = new TipoCaracter(); }
break;
case 14:
//#line 58 "sintac.y"
{ yyval = new TipoIdent(val_peek(0)); }
break;
case 15:
//#line 59 "sintac.y"
{ yyval = val_peek(0); }
break;
case 16:
//#line 62 "sintac.y"
{ yyval = new TipoArray(val_peek(2), val_peek(0)); }
break;
case 17:
//#line 65 "sintac.y"
{ yyval = new DefFuncion(val_peek(8), val_peek(6), val_peek(4), val_peek(2), val_peek(1)); }
break;
case 18:
//#line 66 "sintac.y"
{ yyval = new DefFuncion(val_peek(7), val_peek(5), null, val_peek(2), val_peek(1)); }
break;
case 19:
//#line 69 "sintac.y"
{ yyval = val_peek(0); }
break;
case 20:
//#line 72 "sintac.y"
{ yyval = val_peek(0); }
break;
case 21:
//#line 73 "sintac.y"
{ yyval = new ArrayList<DefVariable>(); }
break;
case 22:
//#line 76 "sintac.y"
{ yyval = val_peek(4); ((List)yyval).add(new DefVariable(val_peek(0), val_peek(2))); }
break;
case 23:
//#line 77 "sintac.y"
{ yyval = new ArrayList<DefVariable>(); ((List)yyval).add(new DefVariable(val_peek(0), val_peek(2))); }
break;
case 24:
//#line 80 "sintac.y"
{ yyval = val_peek(1); ((List)yyval).add(val_peek(0)); }
break;
case 25:
//#line 81 "sintac.y"
{ yyval = new ArrayList<DefVariable>(); }
break;
case 26:
//#line 84 "sintac.y"
{ yyval = new DefVariable(val_peek(1), val_peek(3)); }
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
//#line 93 "sintac.y"
{ yyval = val_peek(1); }
break;
case 31:
//#line 94 "sintac.y"
{ yyval = new Lectura(val_peek(1)); }
break;
case 32:
//#line 95 "sintac.y"
{ yyval = new Return(val_peek(1)); }
break;
case 33:
//#line 96 "sintac.y"
{ yyval = new Return(null); }
break;
case 34:
//#line 97 "sintac.y"
{ yyval = new Ifelse(val_peek(4), val_peek(1), null); }
break;
case 35:
//#line 98 "sintac.y"
{ yyval = new Ifelse(val_peek(8), val_peek(5), val_peek(1)); }
break;
case 36:
//#line 99 "sintac.y"
{ yyval = new While(val_peek(4), val_peek(1)); }
break;
case 37:
//#line 101 "sintac.y"
{ yyval = val_peek(1); }
break;
case 38:
//#line 104 "sintac.y"
{ yyval = new Escritura(val_peek(0), "print"); }
break;
case 39:
//#line 105 "sintac.y"
{ yyval = new Escritura(val_peek(0), "printsp"); }
break;
case 40:
//#line 106 "sintac.y"
{ yyval = new Escritura(val_peek(0), "println"); }
break;
case 41:
//#line 109 "sintac.y"
{ yyval = new InvFuncSent(val_peek(3), val_peek(1)); }
break;
case 42:
//#line 110 "sintac.y"
{ yyval = new InvFuncSent(val_peek(2), null); }
break;
case 43:
//#line 113 "sintac.y"
{ yyval = val_peek(2); ((List)yyval).add(val_peek(0)); }
break;
case 44:
//#line 114 "sintac.y"
{ yyval = new ArrayList<Expresion>(); ((List)yyval).add(val_peek(0)); }
break;
case 45:
//#line 117 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "+" ,val_peek(0)); }
break;
case 46:
//#line 118 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "-" ,val_peek(0)); }
break;
case 47:
//#line 119 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "*" ,val_peek(0)); }
break;
case 48:
//#line 120 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "/" ,val_peek(0)); }
break;
case 49:
//#line 122 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), ">=" ,val_peek(0)); }
break;
case 50:
//#line 123 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "<=" ,val_peek(0)); }
break;
case 51:
//#line 124 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "<" ,val_peek(0)); }
break;
case 52:
//#line 125 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), ">" ,val_peek(0)); }
break;
case 53:
//#line 127 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "!=" ,val_peek(0)); }
break;
case 54:
//#line 128 "sintac.y"
{ yyval = new ExpresionBinaria(val_peek(2), "==" ,val_peek(0)); }
break;
case 55:
//#line 130 "sintac.y"
{ yyval = new ExpresionUnariaNegacion(val_peek(0)); }
break;
case 56:
//#line 131 "sintac.y"
{ yyval = new ExpresionLogica(val_peek(2), "&&" ,val_peek(0)); }
break;
case 57:
//#line 132 "sintac.y"
{ yyval = new ExpresionLogica(val_peek(2), "||" ,val_peek(0)); }
break;
case 58:
//#line 134 "sintac.y"
{ yyval = new Variable(val_peek(0)); }
break;
case 59:
//#line 135 "sintac.y"
{ yyval = new LiteralInt(val_peek(0)); }
break;
case 60:
//#line 136 "sintac.y"
{ yyval = new LiteralReal(val_peek(0)); }
break;
case 61:
//#line 137 "sintac.y"
{ yyval = new LiteralCaracter(val_peek(0)); }
break;
case 62:
//#line 139 "sintac.y"
{ yyval = val_peek(1); }
break;
case 63:
//#line 141 "sintac.y"
{ yyval = new Cast(val_peek(2), val_peek(0)); }
break;
case 64:
//#line 144 "sintac.y"
{ yyval = new AccesoArray(val_peek(3), val_peek(1)); }
break;
case 65:
//#line 147 "sintac.y"
{ yyval = new AccesoStruct(val_peek(2), val_peek(0)); }
break;
case 66:
//#line 150 "sintac.y"
{ yyval = new InvFuncExpr(val_peek(3), val_peek(1)); }
break;
//#line 949 "Parser.java"
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

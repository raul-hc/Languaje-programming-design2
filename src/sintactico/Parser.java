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
public final static short LITERAL_ENTERO=257;
public final static short LITERAL_REAL=258;
public final static short LITERAL_CARACTER=259;
public final static short Y=260;
public final static short O=261;
public final static short DISTINTO=262;
public final static short IGUALDAD=263;
public final static short MAYORIGUAL=264;
public final static short MENORIGUAL=265;
public final static short STRUCT=266;
public final static short IDENTIFICADOR=267;
public final static short INT=268;
public final static short FLOAT=269;
public final static short CHAR=270;
public final static short VAR=271;
public final static short READ=272;
public final static short RETURN=273;
public final static short WHILE=274;
public final static short PRINT=275;
public final static short PRINTSP=276;
public final static short PRINTLN=277;
public final static short IF=278;
public final static short ELSE=279;
public final static short CAST=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    2,    4,    6,    6,
    7,    7,    7,    7,    7,    8,    8,    5,    5,   10,
   10,    9,    9,   11,   11,    3,   12,   12,   13,   13,
   13,   13,   13,   13,   13,   16,   16,   15,   15,   15,
   17,   17,   18,   18,   19,   19,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   20,
   20,   20,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,    1,    6,    5,    0,
    1,    1,    1,    1,    1,    4,    3,    9,    8,    2,
    0,    5,    3,    2,    0,    5,    2,    0,    4,    2,
    3,    3,    1,    7,    2,    1,    0,    2,    2,    2,
    7,   11,    4,    3,    3,    1,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    3,    3,    1,
    1,    1,    1,    3,    3,    5,    4,    3,    4,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    4,    5,    6,    7,    0,
    0,    0,    3,   10,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   14,   11,   12,   13,    0,
   15,    0,    0,   20,   25,   23,    0,    0,    0,   26,
    0,    8,    0,   25,    0,    0,    0,   24,    0,    0,
   22,   16,    9,   61,   62,   63,    0,    0,    0,    0,
   19,    0,    0,    0,    0,    0,    0,    0,    0,   27,
    0,    0,   33,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   30,   35,   18,    0,   64,   44,
    0,    0,   65,   31,   32,    0,    0,   70,   71,   72,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   68,    0,    0,    0,    0,    0,
    0,    0,   67,   29,   69,    0,   28,   28,    0,    0,
    0,   34,    0,    0,   28,    0,   42,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   19,   30,   31,   17,   21,
   43,   49,   70,   71,   72,   83,   73,   74,  112,  121,
};
final static short yysindex[] = {                      -244,
 -261,  -23, -246,    0, -244,    0,    0,    0,    0,  -90,
  -40,  -24,    0,    0,  -17,   -2,   -6,  -88, -121,  -88,
  -83,  -88,  -17, -215, -192,    0,    0,    0,    0,    7,
    0,   -1,   12,    0,    0,    0,  -50,   16,  -16,    0,
  -88,    0, -196,    0,  -88,  -88,   19,    0,  -33, -196,
    0,    0,    0,    0,    0,    0,   51,   51,   36,   51,
    0,   51,   51,   40,   51,   51,   51,   41,   22,    0,
  628,   24,    0,   26,  -21,   46,  -41,  -32,   65,  634,
  640,  715,   28,   51,  715,  715,  715,   51, -200,   51,
   51,   51,   51,   51,   51,   51,   51,   51,   51,   51,
   51,   51, -179,   51,    0,    0,    0,   51,    0,    0,
  715,    1,    0,    0,    0,  144,  581,    0,    0,    0,
   38,  -41,  -41,  -38,  -38,  -38,  -38,  523,  523,  -44,
  -44,  -38,  -38,  675,    0,  681,    5,    0,   51,  -34,
  -22,   51,    0,    0,    0,  715,    0,    0,  -38,   -9,
    3,    0, -176,  -14,    0,   15,    0,
};
final static short yyrindex[] = {                       107,
    0,    0,    0,    0,  110,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -11,    0,    0,    0,    0,    0,
    0,    0,  -11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   27,    0,    0,  -15,    0,    0,    0,   27,
    0,    0,    0,    0,    0,    0,    0,    0,  709,    0,
    0,    0,   56,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  292,  299,    0,    0,    0,
    0,   60,    0,    0,   61,   62,    7,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   10,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  327,  334,  369,  394,  404,  441,  735,  754,   52,
   90,  448,  483,    0,    0,    0,    0,  602,    0,    0,
    0,    0,    0,    0,    0,   20,    0,    0,  559,    0,
    0,    0,   39,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  117,  -18,    0,    0,    0,   17,    0,    0,  100,
   80,  -30,    0,  924,    0,    0,    0,    0,   21,    0,
};
final static int YYTABLESIZE=1066;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         57,
   15,  103,   25,   33,  103,   10,   58,  103,  109,   98,
   96,   57,   97,  103,   99,  101,   11,  100,   58,   75,
   12,    1,    2,   57,   48,   17,    3,  101,   17,  100,
   58,   48,   14,   18,   23,   57,   34,   24,   36,   35,
   20,  138,   58,   17,  139,  145,  102,   57,  139,  102,
   46,   38,  102,   46,   58,   22,   41,   47,  102,   28,
   45,   51,   52,   45,   39,   40,   28,  118,  119,  120,
   42,   41,   44,   45,    3,   79,   46,   53,   41,   84,
   88,   89,  105,   57,  106,  108,  115,  135,  147,   60,
   58,   61,   49,   49,   49,   49,   49,   57,   49,  142,
  148,   60,  154,  107,   58,  110,    2,   17,  155,    1,
   49,   21,   49,   60,   37,  152,  150,  151,   36,   38,
   39,   13,   37,   50,  156,   60,    0,  153,  137,    0,
   50,   50,   50,   50,   50,    0,   50,   60,    0,  157,
    0,    0,    0,    0,   49,   32,    0,    0,   50,   28,
   50,   28,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   41,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   60,    0,    0,   49,    0,   26,   27,
   28,   29,   50,    0,  140,   98,   96,   60,   97,  103,
   99,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  101,    0,  100,    0,    0,    0,    0,
    0,    0,    0,    0,   50,   90,   91,   92,   93,   94,
   95,   90,   91,   54,   55,   56,   16,   90,   91,   92,
   93,   94,   95,   59,  102,   54,   55,   56,   62,   63,
   64,   65,   66,   67,   68,   59,   69,   54,   55,   56,
   62,   63,   64,   65,   66,   67,   68,   59,   69,   54,
   55,   56,   62,   63,   64,   65,   66,   67,   68,   59,
   69,   54,   55,   56,   62,   63,   64,   65,   66,   67,
   68,   59,   69,   28,   28,   28,   62,   63,   64,   65,
   66,   67,   68,   28,   69,   41,   41,   41,   28,   28,
   28,   28,   28,   28,   28,   41,   28,   54,   55,   56,
   41,   41,   41,   41,   41,   41,   41,   76,   41,    0,
    0,   54,   55,   56,    0,    0,    0,    0,    0,    0,
   69,   76,   60,   60,   60,   60,   60,   60,   60,   57,
   57,   57,   57,   57,   69,   57,    0,    0,    0,    0,
   60,   60,   60,   60,    0,    0,    0,   57,   57,   57,
   57,    0,    0,    0,    0,    0,    0,   58,   58,   58,
   58,   58,    0,   58,   59,   59,   59,   59,   59,    0,
   59,    0,   60,    0,   60,   58,   58,   58,   58,    0,
    0,   57,   59,   59,   59,   59,    0,    0,    0,    0,
    0,    0,    0,   90,   91,   92,   93,   94,   95,   55,
   55,   55,   55,   55,    0,   55,   60,    0,    0,   58,
    0,    0,    0,   57,    0,    0,   59,   55,   55,   55,
   55,    0,    0,    0,   56,   56,   56,   56,   56,    0,
   56,    0,    0,    0,   51,   51,   51,   51,   51,    0,
   51,   58,   56,   56,   56,   56,    0,    0,   59,    0,
    0,   55,   51,   51,   51,   51,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   52,   52,   52,   52,   52,   56,   52,   54,   54,
   54,   54,   54,   55,   54,    0,   51,    0,    0,   52,
   52,   52,   52,    0,    0,    0,   54,   54,   54,   54,
    0,    0,    0,    0,    0,    0,    0,    0,   56,    0,
    0,    0,    0,   53,   53,   53,   53,   53,   51,   53,
    0,    0,    0,   52,    0,    0,    0,    0,    0,    0,
   54,   53,   53,   53,   53,    0,    0,    0,    0,    0,
    0,   60,   60,   60,   60,   60,   60,    0,   57,   57,
   57,   57,   57,   57,   98,   52,    0,    0,  103,   99,
    0,    0,   54,    0,    0,   53,    0,    0,    0,    0,
    0,    0,  101,    0,  100,    0,   58,   58,   58,   58,
   58,   58,    0,   59,   59,   59,   59,   59,   59,   66,
   66,   66,   66,   66,    0,   66,    0,   53,    0,    0,
    0,    0,    0,  102,    0,    0,    0,   66,   66,   66,
   66,  141,   98,   96,    0,   97,  103,   99,    0,    0,
   55,   55,   55,   55,    0,    0,    0,    0,    0,    0,
  101,    0,  100,   69,   69,    0,   69,   69,   69,    0,
    0,   66,    0,    0,    0,   56,   56,   56,   56,    0,
   43,   69,   69,   69,    0,   51,   51,   51,   51,   98,
   96,  102,   97,  103,   99,   98,   96,    0,   97,  103,
   99,   98,   96,   66,   97,  103,   99,  101,  104,  100,
    0,    0,   69,  101,    0,  100,    0,    0,  114,  101,
    0,  100,   52,   52,   52,   52,    0,    0,    0,   54,
   54,   54,   54,    0,    0,    0,   98,   96,  102,   97,
  103,   99,   98,   96,  102,   97,  103,   99,    0,    0,
  102,    0,    0,    0,  101,    0,  100,    0,    0,  144,
  101,    0,  100,    0,   53,   53,   53,   53,    0,    0,
   60,   60,    0,   60,   60,   60,   98,   96,  113,   97,
  103,   99,    0,    0,    0,  102,    0,  143,   60,   60,
   60,  102,    0,    0,  101,   47,  100,   47,   47,   47,
    0,    0,   90,   91,   92,   93,   94,   95,    0,    0,
    0,    0,    0,   47,   48,   47,   48,   48,   48,   60,
    0,    0,    0,    0,    0,  102,    0,    0,    0,    0,
    0,    0,   48,    0,   48,    0,    0,    0,    0,    0,
   66,   66,   66,   66,    0,    0,    0,   47,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   90,   91,   92,   93,   94,   95,   48,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   47,
    0,   69,   69,   69,   69,   69,   69,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   48,    0,
    0,    0,    0,    0,    0,    0,    0,   90,   91,   92,
   93,   94,   95,   90,   91,   92,   93,   94,   95,   90,
   91,   92,   93,   94,   95,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   90,   91,   92,   93,   94,   95,
   90,   91,   92,   93,   94,   95,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   60,   60,
   60,   60,   60,   60,   90,   91,   92,   93,   94,   95,
   77,   78,    0,   80,    0,   81,   82,    0,   85,   86,
   87,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  111,    0,    0,    0,    0,  116,    0,    0,
    0,  117,    0,  122,  123,  124,  125,  126,  127,  128,
  129,  130,  131,  132,  133,  134,    0,  136,    0,    0,
    0,  111,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  146,    0,    0,  149,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   41,   46,   91,  125,   46,  267,   40,   46,   41,   42,
   43,   33,   45,   46,   47,   60,   40,   62,   40,   50,
  267,  266,  267,   33,   43,   41,  271,   60,   44,   62,
   40,   50,  123,   58,   41,   33,   20,   44,   22,  123,
   58,   41,   40,   59,   44,   41,   91,   33,   44,   91,
   41,  267,   91,   44,   40,   58,   58,   41,   91,   33,
   41,   45,   46,   44,  257,   59,   40,  268,  269,  270,
   59,   33,  123,   58,  271,   40,   93,   59,   40,   40,
   40,   60,   59,   33,   59,   40,   59,  267,  123,  123,
   40,  125,   41,   42,   43,   44,   45,   33,   47,   62,
  123,  123,  279,  125,   40,   41,    0,  123,  123,    0,
   59,  123,   61,  123,   59,  125,  147,  148,   59,   59,
   59,    5,   23,   44,  155,  123,   -1,  125,  108,   -1,
   41,   42,   43,   44,   45,   -1,   47,  123,   -1,  125,
   -1,   -1,   -1,   -1,   93,  267,   -1,   -1,   59,  123,
   61,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  123,   -1,   -1,  125,   -1,  267,  268,
  269,  270,   93,   -1,   41,   42,   43,  123,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  125,  260,  261,  262,  263,  264,
  265,  260,  261,  257,  258,  259,  267,  260,  261,  262,
  263,  264,  265,  267,   91,  257,  258,  259,  272,  273,
  274,  275,  276,  277,  278,  267,  280,  257,  258,  259,
  272,  273,  274,  275,  276,  277,  278,  267,  280,  257,
  258,  259,  272,  273,  274,  275,  276,  277,  278,  267,
  280,  257,  258,  259,  272,  273,  274,  275,  276,  277,
  278,  267,  280,  257,  258,  259,  272,  273,  274,  275,
  276,  277,  278,  267,  280,  257,  258,  259,  272,  273,
  274,  275,  276,  277,  278,  267,  280,  257,  258,  259,
  272,  273,  274,  275,  276,  277,  278,  267,  280,   -1,
   -1,  257,  258,  259,   -1,   -1,   -1,   -1,   -1,   -1,
  280,  267,   41,   42,   43,   44,   45,   46,   47,   41,
   42,   43,   44,   45,  280,   47,   -1,   -1,   -1,   -1,
   59,   60,   61,   62,   -1,   -1,   -1,   59,   60,   61,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,   41,   42,   43,   44,   45,   -1,
   47,   -1,   91,   -1,   93,   59,   60,   61,   62,   -1,
   -1,   93,   59,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  260,  261,  262,  263,  264,  265,   41,
   42,   43,   44,   45,   -1,   47,  125,   -1,   -1,   93,
   -1,   -1,   -1,  125,   -1,   -1,   93,   59,   60,   61,
   62,   -1,   -1,   -1,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   -1,   41,   42,   43,   44,   45,   -1,
   47,  125,   59,   60,   61,   62,   -1,   -1,  125,   -1,
   -1,   93,   59,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   93,   47,   41,   42,
   43,   44,   45,  125,   47,   -1,   93,   -1,   -1,   59,
   60,   61,   62,   -1,   -1,   -1,   59,   60,   61,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,
   -1,   -1,   -1,   41,   42,   43,   44,   45,  125,   47,
   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   93,   59,   60,   61,   62,   -1,   -1,   -1,   -1,   -1,
   -1,  260,  261,  262,  263,  264,  265,   -1,  260,  261,
  262,  263,  264,  265,   42,  125,   -1,   -1,   46,   47,
   -1,   -1,  125,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   60,   -1,   62,   -1,  260,  261,  262,  263,
  264,  265,   -1,  260,  261,  262,  263,  264,  265,   41,
   42,   43,   44,   45,   -1,   47,   -1,  125,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   59,   60,   61,
   62,   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,
  262,  263,  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,
   60,   -1,   62,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   93,   -1,   -1,   -1,  262,  263,  264,  265,   -1,
   59,   60,   61,   62,   -1,  262,  263,  264,  265,   42,
   43,   91,   45,   46,   47,   42,   43,   -1,   45,   46,
   47,   42,   43,  125,   45,   46,   47,   60,   61,   62,
   -1,   -1,   91,   60,   -1,   62,   -1,   -1,   59,   60,
   -1,   62,  262,  263,  264,  265,   -1,   -1,   -1,  262,
  263,  264,  265,   -1,   -1,   -1,   42,   43,   91,   45,
   46,   47,   42,   43,   91,   45,   46,   47,   -1,   -1,
   91,   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,   59,
   60,   -1,   62,   -1,  262,  263,  264,  265,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   42,   43,  125,   45,
   46,   47,   -1,   -1,   -1,   91,   -1,   93,   60,   61,
   62,   91,   -1,   -1,   60,   41,   62,   43,   44,   45,
   -1,   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,
   -1,   -1,   -1,   59,   41,   61,   43,   44,   45,   91,
   -1,   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,
   -1,   -1,   59,   -1,   61,   -1,   -1,   -1,   -1,   -1,
  262,  263,  264,  265,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  260,  261,  262,  263,  264,  265,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  125,
   -1,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  260,  261,  262,
  263,  264,  265,  260,  261,  262,  263,  264,  265,  260,
  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  260,  261,  262,  263,  264,  265,
  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  260,  261,
  262,  263,  264,  265,  260,  261,  262,  263,  264,  265,
   57,   58,   -1,   60,   -1,   62,   63,   -1,   65,   66,
   67,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   79,   -1,   -1,   -1,   -1,   84,   -1,   -1,
   -1,   88,   -1,   90,   91,   92,   93,   94,   95,   96,
   97,   98,   99,  100,  101,  102,   -1,  104,   -1,   -1,
   -1,  108,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  139,   -1,   -1,  142,
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
null,null,null,null,null,null,null,null,null,"LITERAL_ENTERO","LITERAL_REAL",
"LITERAL_CARACTER","Y","O","DISTINTO","IGUALDAD","MAYORIGUAL","MENORIGUAL",
"\"STRUCT\"","\"IDENTIFICADOR\"","\"INT\"","\"FLOAT\"","\"CHAR\"","\"VAR\"",
"\"READ\"","\"RETURN\"","\"WHILE\"","\"PRINT\"","\"PRINTSP\"","\"PRINTLN\"",
"\"IF\"","\"ELSE\"","\"CAST\"",
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
"tipoArray : '[' LITERAL_ENTERO ']' tipo",
"tipoArray : '[' LITERAL_ENTERO ']'",
"defFuncion : \"IDENTIFICADOR\" '(' parametros ')' tipoRetorno '{' defVarLocales sentencias '}'",
"defFuncion : \"IDENTIFICADOR\" '(' ')' tipoRetorno '{' defVarLocales sentencias '}'",
"tipoRetorno : ':' tipo",
"tipoRetorno :",
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
"sentencia : \"RETURN\" exprOpt ';'",
"sentencia : if",
"sentencia : \"WHILE\" '(' expr ')' '{' sentencias '}'",
"sentencia : invocacionFuncion ';'",
"exprOpt : expr",
"exprOpt :",
"escritura : \"PRINT\" expr",
"escritura : \"PRINTSP\" expr",
"escritura : \"PRINTLN\" expr",
"if : \"IF\" '(' expr ')' '{' sentencias '}'",
"if : \"IF\" '(' expr ')' '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
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
"expr : LITERAL_ENTERO",
"expr : LITERAL_REAL",
"expr : LITERAL_CARACTER",
"expr : '(' expr ')'",
"expr : '{' expr '}'",
"expr : \"CAST\" '<' tipoBasico '>' expr",
"expr : expr '[' expr ']'",
"expr : expr '.' \"IDENTIFICADOR\"",
"expr : \"IDENTIFICADOR\" '(' listaExprSeparador ')'",
"tipoBasico : \"INT\"",
"tipoBasico : \"FLOAT\"",
"tipoBasico : \"CHAR\"",
};

//#line 166 "sintac.y"

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

//#line 562 "Parser.java"
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

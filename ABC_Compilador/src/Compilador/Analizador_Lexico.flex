/*------------  1ra Area: Codigo de Usuario ---------*/
//------> Paquetes,importaciones
package Compilador;
import java_cup.runtime.*;
import java.util.ArrayList;

/*------------  2da Area: Opciones y Declaraciones ---------*/
%%
%{
    //----> Codigo de usuario en sintaxis java
    //public static LinkedList<TError> TablaEL = new LinkedList<TError>(); 
%}

//-------> Directivas
%public 
%class Analizador_Lexico
%cupsym sym
%cup
%char
%column
%full
%ignorecase
%line
%unicode

//------> Expresiones Regulares
L = [a-zA-Z_]
D = [0-9]
WHITE=[ \t\r]
ENDLINE=[\n]
Exponent = [eE] [\+\-]? 
Float = {D}+ \. {D}+ 
CChar = [^\'\\\n\r] | {EscChar}
SChar = [^\"\\\n\r] | {EscChar}
EscChar = \\[ntbrf\\\'\"] | {OctalEscape}
OctalEscape = \\[0-7] | \\[0-7][0-7] | \\[0-3][0-7][0-7]
ComentarioLinea = "//".*
ComentarioBloque = "\(\*" ({WHITE}|{ENDLINE}|{L}|{D}|{SChar}|[\"])* "\*\)" | "{" ([^\}]|.|{WHITE}|{ENDLINE})* "}"
CaracterInvalidoIdentificador = [ \,\<\>\`\~\!\&\#\|\.\/\@\$\%\^\*\=\+]

//------> Estados

%%
/*------------  3raa Area: Reglas Lexicas ---------*/

//-----> Simbolos

{ComentarioBloque} {/*Ignore*/}
{ComentarioLinea} {/*Ignore*/}
{WHITE} {/*Ignore*/}
{ENDLINE} {/*Ignore*/}

\'{CChar}\' | \# {D}+ {return new Symbol(sym.L_CHAR, yycolumn, yyline, yytext());}
\"({WHITE}|{ENDLINE}|{SChar})*\" {return new Symbol(sym.L_STRING, yycolumn, yyline, yytext());}
{Float} | {Float} {Exponent} {D}+  {return new Symbol(sym.L_FLOAT, yycolumn, yyline, yytext());}
{D}+ {ScannerABC.agregarToken(yytext(),Token.L_INTEGER,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.L_INTEGER, yycolumn, yyline, yytext());}

<YYINITIAL> "XOR" {ScannerABC.agregarToken(yytext(),Token.O_XOR,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_XOR, yycolumn, yyline, yytext());}

<YYINITIAL> "WRITE" {ScannerABC.agregarToken(yytext(),Token.PR_WRITE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_WRITE, yycolumn, yyline, yytext());}

<YYINITIAL> "WITH" {ScannerABC.agregarToken(yytext(),Token.PR_WITH,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_WITH, yycolumn, yyline, yytext());}

<YYINITIAL> "WHILE" {ScannerABC.agregarToken(yytext(),Token.PR_WHILE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_WHILE, yycolumn, yyline, yytext());}

<YYINITIAL> "VAR" {ScannerABC.agregarToken(yytext(),Token.PR_VAR,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_VAR, yycolumn, yyline, yytext());}

<YYINITIAL> "UNTIL" {ScannerABC.agregarToken(yytext(),Token.PR_UNTIL,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_UNTIL, yycolumn, yyline, yytext());}

<YYINITIAL> "TYPE" {ScannerABC.agregarToken(yytext(),Token.PR_TYPE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_TYPE, yycolumn, yyline, yytext());}

<YYINITIAL> "TRUE" {ScannerABC.agregarToken(yytext(),Token.PR_TRUE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_TRUE, yycolumn, yyline, yytext());}

<YYINITIAL> "TO" {ScannerABC.agregarToken(yytext(),Token.PR_TO,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_TO, yycolumn, yyline, yytext());}

<YYINITIAL> "THEN" {ScannerABC.agregarToken(yytext(),Token.PR_THEN,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_THEN, yycolumn, yyline, yytext());}

<YYINITIAL> "STRING" {ScannerABC.agregarToken(yytext(),Token.PR_STRING,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_STRING, yycolumn, yyline, yytext());}

<YYINITIAL> "SHORTINT" {ScannerABC.agregarToken(yytext(),Token.PR_SHORTINT,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_SHORTINT, yycolumn, yyline, yytext());}

<YYINITIAL> "SET" {ScannerABC.agregarToken(yytext(),Token.PR_SET,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_SET, yycolumn, yyline, yytext());}

<YYINITIAL> "REPEAT" {ScannerABC.agregarToken(yytext(),Token.PR_REPEAT,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_REPEAT, yycolumn, yyline, yytext());}

<YYINITIAL> "RECORD" {ScannerABC.agregarToken(yytext(),Token.PR_RECORD,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_RECORD, yycolumn, yyline, yytext());}

<YYINITIAL> "REAL" {ScannerABC.agregarToken(yytext(),Token.PR_REAL,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_REAL, yycolumn, yyline, yytext());}

<YYINITIAL> "READ" {ScannerABC.agregarToken(yytext(),Token.PR_READ,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_READ, yycolumn, yyline, yytext());}

<YYINITIAL> "PROGRAM" {ScannerABC.agregarToken(yytext(),Token.PR_PROGRAM,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_PROGRAM, yycolumn, yyline, yytext());}

<YYINITIAL> "PROCEDURE" {ScannerABC.agregarToken(yytext(),Token.PR_PROCEDURE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_PROCEDURE, yycolumn, yyline, yytext());}

<YYINITIAL> "PACKED" {ScannerABC.agregarToken(yytext(),Token.PR_PACKED,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_PACKED, yycolumn, yyline, yytext());}

<YYINITIAL> "OR" {ScannerABC.agregarToken(yytext(),Token.O_OR,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_OR, yycolumn, yyline, yytext());}

<YYINITIAL> "OF" {ScannerABC.agregarToken(yytext(),Token.PR_OF,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_OF, yycolumn, yyline, yytext());}

<YYINITIAL> "NOT" {ScannerABC.agregarToken(yytext(),Token.O_NOT,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_NOT, yycolumn, yyline, yytext());}

<YYINITIAL> "NIL" {ScannerABC.agregarToken(yytext(),Token.PR_NIL,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_NIL, yycolumn, yyline, yytext());}

<YYINITIAL> "MOD" {ScannerABC.agregarToken(yytext(),Token.O_MOD,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_MOD, yycolumn, yyline, yytext());}

<YYINITIAL> "LONGINT" {ScannerABC.agregarToken(yytext(),Token.PR_LONGINT,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_LONGINT, yycolumn, yyline, yytext());}

<YYINITIAL> "LABEL" {ScannerABC.agregarToken(yytext(),Token.PR_LABEL,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_LABEL, yycolumn, yyline, yytext());}

<YYINITIAL> "INT" {ScannerABC.agregarToken(yytext(),Token.PR_INT,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_INT, yycolumn, yyline, yytext());}

<YYINITIAL> "INLINE" {ScannerABC.agregarToken(yytext(),Token.PR_INLINE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_INLINE, yycolumn, yyline, yytext());}

<YYINITIAL> "IN" {ScannerABC.agregarToken(yytext(),Token.PR_IN,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_IN, yycolumn, yyline, yytext());}

<YYINITIAL> "IF" {ScannerABC.agregarToken(yytext(),Token.PR_IF,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_IF, yycolumn, yyline, yytext());}

<YYINITIAL> "GOTO" {ScannerABC.agregarToken(yytext(),Token.PR_GOTO,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_GOTO, yycolumn, yyline, yytext());}

<YYINITIAL> "FUNCTION" {ScannerABC.agregarToken(yytext(),Token.PR_FUNCTION,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_FUNCTION, yycolumn, yyline, yytext());}

<YYINITIAL> "FORWARD" {ScannerABC.agregarToken(yytext(),Token.PR_FORWARD,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_FORWARD, yycolumn, yyline, yytext());}

<YYINITIAL> "FOR" {ScannerABC.agregarToken(yytext(),Token.PR_FOR,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_FOR, yycolumn, yyline, yytext());}

<YYINITIAL> "FILE" {ScannerABC.agregarToken(yytext(),Token.PR_FILE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_FILE, yycolumn, yyline, yytext());}

<YYINITIAL> "FALSE" {ScannerABC.agregarToken(yytext(),Token.PR_FALSE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_FALSE, yycolumn, yyline, yytext());}

<YYINITIAL> "END" {ScannerABC.agregarToken(yytext(),Token.PR_END,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_END, yycolumn, yyline, yytext());}

<YYINITIAL> "ELSE" {ScannerABC.agregarToken(yytext(),Token.PR_ELSE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_ELSE, yycolumn, yyline, yytext());}

<YYINITIAL> "DOWNTO" {ScannerABC.agregarToken(yytext(),Token.PR_DOWNTO,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_DOWNTO, yycolumn, yyline, yytext());}

<YYINITIAL> "DO" {ScannerABC.agregarToken(yytext(),Token.PR_DO,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_DO, yycolumn, yyline, yytext());}

<YYINITIAL> "DIV" {ScannerABC.agregarToken(yytext(),Token.O_DIV,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_DIV, yycolumn, yyline, yytext());}

<YYINITIAL> "CONST" {ScannerABC.agregarToken(yytext(),Token.PR_CONST,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_CONST, yycolumn, yyline, yytext());}

<YYINITIAL> "CHAR" {ScannerABC.agregarToken(yytext(),Token.PR_CHAR,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_CHAR, yycolumn, yyline, yytext());}

<YYINITIAL> "CASE" {ScannerABC.agregarToken(yytext(),Token.PR_CASE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_CASE, yycolumn, yyline, yytext());}

<YYINITIAL> "BYTE" {ScannerABC.agregarToken(yytext(),Token.PR_BYTE,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_BYTE, yycolumn, yyline, yytext());}

<YYINITIAL> "BOOLEAN" {ScannerABC.agregarToken(yytext(),Token.PR_BOOLEAN,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_BOOLEAN, yycolumn, yyline, yytext());}

<YYINITIAL> "BEGIN" {ScannerABC.agregarToken(yytext(),Token.PR_BEGIN,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_BEGIN, yycolumn, yyline, yytext());}

<YYINITIAL> "ARRAY" {ScannerABC.agregarToken(yytext(),Token.PR_ARRAY,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.PR_ARRAY, yycolumn, yyline, yytext());}

<YYINITIAL> "AND" {ScannerABC.agregarToken(yytext(),Token.O_AND,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_AND, yycolumn, yyline, yytext());}

<YYINITIAL> "]" {ScannerABC.agregarToken(yytext(),Token.O_RIGHT_SQUARE_BRACKET,(yyline+1));
                ScannerABC.nombreTokens.add(yytext());
                return new Symbol(sym.O_RIGHT_SQUARE_BRACKET, yycolumn, yyline, yytext());}

<YYINITIAL> "[" {ScannerABC.agregarToken(yytext(),Token.O_LEFT_SQUARE_BRACKET,(yyline+1));
                ScannerABC.nombreTokens.add(yytext());
                return new Symbol(sym.O_LEFT_SQUARE_BRACKET, yycolumn, yyline, yytext());}

<YYINITIAL> ">>=" {ScannerABC.agregarToken(yytext(),Token.O_GREATER_GREATER_EQUALS,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_GREATER_GREATER_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> ">>" {ScannerABC.agregarToken(yytext(),Token.O_GREATER_GREATER,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_GREATER_GREATER, yycolumn, yyline, yytext());}

<YYINITIAL> ">=" {ScannerABC.agregarToken(yytext(),Token.O_GREATER_EQUALS,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_GREATER_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> ">" {ScannerABC.agregarToken(yytext(),Token.O_GREATER,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_GREATER, yycolumn, yyline, yytext());}

<YYINITIAL> "=" {ScannerABC.agregarToken(yytext(),Token.O_EQUALS,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> "<>" {ScannerABC.agregarToken(yytext(),Token.O_LESS_GREATER,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_LESS_GREATER, yycolumn, yyline, yytext());}

<YYINITIAL> "<=" {ScannerABC.agregarToken(yytext(),Token.O_LESS_EQUALS,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_LESS_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> "<<=" {ScannerABC.agregarToken(yytext(),Token.O_LESS_LESS_EQUALS,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_LESS_LESS_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> "<<" {ScannerABC.agregarToken(yytext(),Token.O_LESS_LESS,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_LESS_LESS, yycolumn, yyline, yytext());}

<YYINITIAL> "<" {ScannerABC.agregarToken(yytext(),Token.O_LESS,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_LESS, yycolumn, yyline, yytext());}

<YYINITIAL> ";" {ScannerABC.agregarToken(yytext(),Token.O_SEMICOLON,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_SEMICOLON, yycolumn, yyline, yytext());}

<YYINITIAL> ":=" {ScannerABC.agregarToken(yytext(),Token.O_COLON_EQUALS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_COLON_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> ":" {ScannerABC.agregarToken(yytext(),Token.O_COLON,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_COLON, yycolumn, yyline, yytext());}

<YYINITIAL> "/=" {ScannerABC.agregarToken(yytext(),Token.O_SLASH_EQUALS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_SLASH_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> "/" {ScannerABC.agregarToken(yytext(),Token.O_SLASH,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_SLASH, yycolumn, yyline, yytext());}

<YYINITIAL> "." {ScannerABC.agregarToken(yytext(),Token.O_FULL_STOP,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_FULL_STOP, yycolumn, yyline, yytext());}

<YYINITIAL> "-=" {ScannerABC.agregarToken(yytext(),Token.O_MINUS_EQUALS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_MINUS_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> "--" {ScannerABC.agregarToken(yytext(),Token.O_MINUS_MINUS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_MINUS_MINUS, yycolumn, yyline, yytext());}

<YYINITIAL> "-" {ScannerABC.agregarToken(yytext(),Token.O_MINUS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_MINUS, yycolumn, yyline, yytext());}

<YYINITIAL> "," {ScannerABC.agregarToken(yytext(),Token.O_COMMA,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_COMMA, yycolumn, yyline, yytext());}

<YYINITIAL> "+=" {ScannerABC.agregarToken(yytext(),Token.O_PLUS_EQUALS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_PLUS_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> "++" {ScannerABC.agregarToken(yytext(),Token.O_PLUS_PLUS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_PLUS_PLUS, yycolumn, yyline, yytext());}

<YYINITIAL> "+" {ScannerABC.agregarToken(yytext(),Token.O_PLUS,(yyline+1));
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_PLUS, yycolumn, yyline, yytext());}

<YYINITIAL> "*=" {ScannerABC.agregarToken(yytext(),Token.O_ASTERISK_EQUALS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_ASTERISK_EQUALS, yycolumn, yyline, yytext());}

<YYINITIAL> "*" {ScannerABC.agregarToken(yytext(),Token.O_ASTERISK,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_ASTERISK, yycolumn, yyline, yytext());}

<YYINITIAL> ")" {ScannerABC.agregarToken(yytext(),Token.O_RIGHT_PARENTHESIS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_RIGHT_PARENTHESIS, yycolumn, yyline, yytext());}

<YYINITIAL> "(" {ScannerABC.agregarToken(yytext(),Token.O_LEFT_PARENTHESIS,yyline+1);
                    ScannerABC.nombreTokens.add(yytext());
                    return new Symbol(sym.O_LEFT_PARENTHESIS, yycolumn, yyline, yytext());}



//-------> Simbolos ER
<YYINITIAL> {L}({L}|{D})* {ScannerABC.agregarToken(yytext(),Token.IDENTIFICADOR,yyline+1);
                        ScannerABC.nombreTokens.add(yytext());
                        return new Symbol(sym.IDENTIFICADOR, yycolumn, yyline+1, yytext());}
//------> Espacios
[ \t\r\n\f]             {/* Espacios en blanco, se ignoran */}

//------> Errores Lexicos

{D}+"\." {ScannerABC.errores.add(new ErrorToken(yytext(),"ERROR_FLOAT","Error Léxico: Número decimal " + yytext() + " inválido. Linea: " + yyline + ".", yyline));}
"\."{D}+ {ScannerABC.errores.add(new ErrorToken(yytext(),"ERROR_FLOAT","Error Léxico: Número decimal " + yytext() + " inválido. Linea: " + yyline + ".", yyline));}
({D}+{L}+{D}*)+ {ScannerABC.errores.add(new ErrorToken(yytext(),"ERROR_INTEGER","Error Léxico: Número entero " + yytext() + " inválido. Linea: " + yyline + ".", yyline));}
{D}+ ["\."]? ({D}*{L}+{D}*)+ | ({D}+{L}+{D}*)+ ["\."]? {D}* | ({D}+{L}+{D}*)+ ["\."]? ({D}*{L}+{D}*)+ {ScannerABC.errores.add(new ErrorToken(yytext(),"ERROR_FLOAT","Error Léxico: Número decimal " + yytext() + " inválido. Linea: " + yyline + ".", yyline));}
. {ScannerABC.errores.add(new ErrorToken(yytext(),"ERROR_INVALID_SYMBOL","Error Léxico: Símbolo " + yytext() + " no reconocido. Linea: " + yyline + ".", yyline));}

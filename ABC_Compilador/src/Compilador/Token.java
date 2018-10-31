/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

/**
 *
 * @author orlandojose
 */
public enum Token 
{
    error,
    
    O_XOR,
    PR_WRITE,
    PR_WITH,
    PR_WHILE,
    PR_VAR,
    PR_UNTIL,
    PR_TYPE,
    PR_TRUE,
    PR_TO,
    PR_THEN,
    PR_STRING,
    PR_SHORTINT,
    PR_SET,
    PR_REPEAT,
    PR_RECORD,
    PR_REAL,
    PR_READ,
    PR_PROGRAM,
    PR_PROCEDURE,
    PR_PACKED,
    O_OR,
    PR_OF,
    O_NOT,
    PR_NIL,
    O_MOD,
    PR_LONGINT,
    PR_LABEL,
    PR_INT,
    PR_INLINE,
    PR_IN,
    PR_IF,
    PR_GOTO,
    PR_FUNCTION,
    PR_FORWARD,
    PR_FOR,
    PR_FILE,
    PR_FALSE,
    PR_END,
    PR_ELSE,
    PR_DOWNTO,
    PR_DO,
    O_DIV,
    PR_CONST,
    PR_CHAR,
    PR_CASE,
    PR_BYTE,
    PR_BOOLEAN,
    PR_BEGIN,
    PR_ARRAY,
    O_AND,

    O_RIGHT_SQUARE_BRACKET,
    O_LEFT_SQUARE_BRACKET,
    O_GREATER_GREATER_EQUALS,
    O_GREATER_GREATER,
    O_GREATER_EQUALS,
    O_GREATER,
    O_EQUALS,
    O_LESS_GREATER,
    O_LESS_EQUALS,
    O_LESS_LESS_EQUALS,
    O_LESS_LESS,
    O_LESS,
    O_SEMICOLON,
    O_COLON_EQUALS,
    O_COLON,
    O_SLASH_EQUALS,
    O_SLASH,
    O_FULL_STOP,
    O_MINUS_EQUALS,
    O_MINUS_MINUS,
    O_MINUS,
    O_COMMA,
    O_PLUS_EQUALS,
    O_PLUS_PLUS,
    O_PLUS,
    O_ASTERISK_EQUALS,
    O_ASTERISK,
    O_RIGHT_PARENTHESIS,
    O_LEFT_PARENTHESIS,
    
    IDENTIFICADOR,
    L_FLOAT,
    L_CHAR,
    L_STRING,
    L_INTEGER
}

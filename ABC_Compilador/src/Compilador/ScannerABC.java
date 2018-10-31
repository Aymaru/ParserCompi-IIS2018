/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import Compilador.ErrorToken;
import Compilador.Identificador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author Orlando José Hidalgo Ramírez
 * @author Aymarú Castillo Flores
 */
public class ScannerABC 
{
    public static ArrayList<Identificador> tokenslist = new ArrayList();;
    public static ArrayList<String> nombreTokens = new ArrayList();;
    //private ArrayList<String> errores;
    public static ArrayList<ErrorToken> errores = new ArrayList();;
    
    public ScannerABC() {}
    
    public static void generarLexer(String path)
    {
        File file=new File(path);
        jflex.Main.generate(file);
    }
    
    /*
    public void probarLexerFile(String nombreArchivo) throws IOException
    {
        tokenslist = new ArrayList();
        nombreTokens = new ArrayList();
        errores = new ArrayList();
        //Obtener nombre archivo 
        
        //Obtener nombre archivo resultado
        
        Reader r = new BufferedReader(new FileReader(nombreArchivo+""));//cambiar por .abc luego
        Compilador.Lexer lexer = new Compilador.Lexer(r);
        
        while (true)
        {
            Token token = lexer.yylex();
            if (token == null)
            {
                XML.XML.writeXML(this,nombreArchivo);
                return;
            }
            switch (token)
            {
                case PALABRAS_RESERVADAS:
                    agregarToken(lexer.lexeme,token,lexer.getYyline()+1);
                    nombreTokens.add(lexer.lexeme);
                    break;
                case OPERADOR:
                    agregarToken(lexer.lexeme,token,lexer.getYyline()+1);
                    nombreTokens.add(lexer.lexeme);
                    break;
                case LITERAL_ENTERO:
                    agregarToken(lexer.lexeme,token,lexer.getYyline()+1);
                    nombreTokens.add(lexer.lexeme);
                    break;
                case LITERAL_FLOTANTE:
                    agregarToken(lexer.lexeme,token,lexer.getYyline()+1);
                    nombreTokens.add(lexer.lexeme);
                    break;
                case LITERAL_STRING:
                    agregarToken(lexer.lexeme,token,lexer.getYyline()+1);
                    nombreTokens.add(lexer.lexeme);
                    break;
                case LITERAL_CHAR:
                    agregarToken(lexer.lexeme,token,lexer.getYyline()+1);
                    nombreTokens.add(lexer.lexeme);
                    break;
                case ERROR_FLOAT:
                    errores.add(new ErrorToken(lexer.lexeme,"Error Lexico","Número decimal inválido.",(1+ lexer.getYyline())));
                    System.out.println("Error Léxico: Número decimal " + lexer.lexeme + " inválido. Linea: " + (1+ lexer.getYyline()));
                    break;
                case ERROR_INT:
                    errores.add(new ErrorToken(lexer.lexeme,"Error Lexico","Número entero  inválido.",(1+ lexer.getYyline())));
                    System.out.println("Error Léxico: Número entero " + lexer.lexeme + " inválido. Linea: " + (1+ lexer.getYyline()));
                    break;
                case ERROR_IDENTIFICADOR:
                    errores.add(new ErrorToken(lexer.lexeme,"Error Lexico","Identificador contiene caracteres inválidos.",(1+ lexer.getYyline())));
                    System.out.println("Error Léxico: Identificador " + lexer.lexeme + " contiene caracteres inválidos. Linea: " + (1+ lexer.getYyline()));
                    break;
                case ERROR_CHAR:
                    errores.add(new ErrorToken(lexer.lexeme,"Error Lexico","Número decimal inválido.",(1+ lexer.getYyline())));
                    System.out.println("Error Léxico: Literal de Char " + lexer.lexeme + " inválido. Linea: " + (1+ lexer.getYyline()));
                    break;
                case ERROR:
                    errores.add(new ErrorToken(lexer.lexeme,"Error Lexico","Símbolo no reconocido.",(1+ lexer.getYyline())));
                    System.out.println("Error Léxico: Símbolo " + lexer.lexeme + " no reconocido. Linea: " + (1+ lexer.getYyline()));
                    break;
                case IDENTIFICADOR: 
                {
                    String identificador = lexer.lexeme;
                    if(!(identificador.length()<128))
                    {
                        errores.add(new ErrorToken(identificador,"Error Lexico","Identificador debe ser menor que 127 caracteres.",(1+ lexer.getYyline())));
                        System.out.println("Error Léxico: Identificador debe ser menor que 127 caracteres. Linea " + (lexer.getYyline()+1) + ". Identificador: " + identificador);
                    }
                    else
                    {
                        agregarToken(identificador.toLowerCase(),token,lexer.getYyline()+1);
                        nombreTokens.add(identificador.toLowerCase());
                    }
                    break;
                }
                default:
                    //resultado=resultado+ "<"+ lexer.lexeme + "> ";
            }
        }
    }
    */
    public static boolean agregarToken(String nombre, Token tipoToken, Integer linea)
    {
        if(nombreTokens.contains(nombre))
        {
            for(Identificador token:tokenslist)
            {
                if(token.getNombre().equals(nombre))
                {
                    token.nuevaAparicion(linea);
                    break;
                }
            }
        }
        else
        {
            tokenslist.add(new Identificador(nombre,tipoToken,linea));
        }
        return true;
    }
    
    
    public static String imprimir()
    {
        String resultado="|         Token        |       Tipo de Token     |    Linea\n";
        for(Identificador token: tokenslist)
        {
            resultado+=token.imprimir();
        }
        return resultado;
    }

    public ArrayList<Identificador> getTokenslist() 
    {
        return tokenslist;
    }

    public ArrayList<ErrorToken> getErrores() 
    {
        return errores;
    }    
}

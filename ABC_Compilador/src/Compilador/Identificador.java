/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author orlandojose
 */
public class Identificador 
{
    String nombre;
    Token tipoToken;
    ArrayList<Integer> apariciones;

    public Identificador(String name, Token tipoToken, Integer linea) 
    {
        this.nombre = name;
        this.tipoToken = tipoToken;
        apariciones = new ArrayList();
        apariciones.add(linea);
    }

    public String getNombre() 
    {
        return nombre;
    }

    public Token getTipoToken() 
    {
        return tipoToken;
    }
    
    public boolean nuevaAparicion(int linea)
    {
        apariciones.add(linea);
        return true;
    }
    
    public String getApariciones()
    {
        String resultado="|";
        
        Collections.sort(apariciones);//Sorting list
        
        for(int contador=0;contador<apariciones.size();contador++)
        {
            int linea = apariciones.get(contador);
            int contadorTemp = apariciones.lastIndexOf(linea);
            int cantidadApariciones = contadorTemp-contador+1;
            if(contador!=contadorTemp)
            {
                resultado+= " " + linea +"(" + (cantidadApariciones) + ") |"; 
                contador=contadorTemp;
            }
            else
                resultado+= " " + linea + " |";
        }
        return resultado;
    }
    public String imprimir()
    {
        int sizeToken1 = 25 - tipoToken.name().length();
        int sizeToken2 = sizeToken1/2;
        if (sizeToken1%2!=0)
            {
                sizeToken1=(sizeToken1/2) + 1;
            }
            else
            {
                sizeToken1=(sizeToken1/2);
            }
        if(nombre.length()<22)
        {
            int sizeNombre1 = (22 - nombre.length());
            int sizeNombre2 = sizeNombre1/2;
            if (sizeNombre1%2!=0)
            {
                sizeNombre1=(sizeNombre1/2) + 1;
            }
            else
            {
                sizeNombre1=(sizeNombre1/2);
            }
            
            String espacioNombre1 = new String(new char[sizeNombre1]).replace("\0", " ");
            String espacioNombre2 = new String(new char[sizeNombre2]).replace("\0", " ");
            String espacioToken1 = new String(new char[sizeToken1]).replace("\0", " ");
            String espacioToken2 = new String(new char[sizeToken2]).replace("\0", " ");
            return "|" + espacioNombre1 + nombre + espacioNombre2 + "|" + espacioToken1 + tipoToken.name() + espacioToken2 + "|    " + getApariciones() + "    \n";
        }
        else
        {
            return "|" + nombre  + "|    " + tipoToken.name() + "    |    " + getApariciones() + "    \n";
        }
    }
    
}

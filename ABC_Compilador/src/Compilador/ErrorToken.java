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
public class ErrorToken 
{
    String token;
    String tipoError;
    String detalle;
    ArrayList<Integer> apariciones;

    public ErrorToken(String token, String tipoError, String detalle ,Integer linea) 
    {
        this.token = token;
        this.tipoError = tipoError;
        this.detalle = detalle;
        apariciones = new ArrayList();
        apariciones.add(linea);
    }

    public String getToken() 
    {
        return token;
    }

    public String getTipoError() 
    {
        return tipoError;
    }

    public String getDetalle() {
        return detalle;
    }
    
    public boolean nuevaAparicion(int linea)
    {
        apariciones.add(linea);
        return true;
    }
    
    public String getApariciones()
    {
        String resultado="| ";
        
        Collections.sort(apariciones);//Sorting list
        
        for(int contador=0;contador<apariciones.size();contador++)
        {
            int linea = apariciones.get(contador);
            int contadorTemp = apariciones.lastIndexOf(linea);
            int cantidadApariciones = contadorTemp-contador+1;
            if(contador!=contadorTemp)
            {
                resultado+=linea +"(" + (cantidadApariciones) + ") |"; 
                contador=contadorTemp;
            }
            else
                resultado+=linea + " |";
        }
        return resultado;
    }
}

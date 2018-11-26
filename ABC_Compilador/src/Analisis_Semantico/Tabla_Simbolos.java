/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis_Semantico;

import Compilador.ErrorToken;
import Compilador.ScannerABC;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ayma-93
 */
public class Tabla_Simbolos {
    
    
    private HashMap<String,Simbolo> tabla_simbolos;
    
    
 
    public Tabla_Simbolos() {
        this.tabla_simbolos = new HashMap<>();
    }
    
       //agregar simbolo
    // *agregar funcion
    
    public boolean agregar_funcion(String nombre, String tipo, ArrayList parametros, int linea){
        
        if(tabla_simbolos.containsKey(nombre)){
            
            ScannerABC.errores.add(new ErrorToken(nombre,"ERROR_SEMANTICO","Error Semántico: Funcion " + nombre + " ya declarada. Linea: " + (linea), linea));
            return false;
        }
        
        Simbolo tmp = new Simbolo(nombre.toUpperCase(), tipo.toUpperCase(), "funcion", parametros, linea);
        tabla_simbolos.put(nombre, tmp);
        
        return true;
    }
    
    // *agregar var global
    
    public boolean agregar_var_global(String nombre, String tipo, int linea){
        
        if(tabla_simbolos.containsKey(nombre)){
            
            ScannerABC.errores.add(new ErrorToken(nombre,"ERROR_SEMANTICO","Error Semántico: Variable " + nombre + " ya declarada. Linea: " + (linea), linea));
            return false;
        }
        
        Simbolo tmp = new Simbolo(nombre.toUpperCase(), tipo.toUpperCase(), "variable_global", linea);
        tabla_simbolos.put(nombre, tmp);
        
        return true;
    }
    
    
    // *agregar const global
    
    public Boolean agregar_const_global(String nombre, String tipo, Object valor,  int linea)
    {
        if(tabla_simbolos.containsKey(nombre))
        {
            ScannerABC.errores.add(new ErrorToken(nombre,"ERROR_SEMANTICO","Error Semántico: Constante " + nombre + " ya declarada. Linea: " + (linea), linea));
            return false;
        }
      
        Simbolo s = new Simbolo(nombre.toUpperCase(), tipo.toUpperCase(), "constante", valor, linea);
        
        tabla_simbolos.put(nombre, s);
        return true;
 
    }
    
    
    // *agregar var local
    
    
    
    
    // *agregar const local
    
    
    
    
    // *elimiar
    public Boolean eliminarSimblolo(String key)
    {
        Simbolo tmp = tabla_simbolos.remove(key);
        if(tmp!=null)
            return true;
        return false;
    }
    
    
    // *buscar 
    public Simbolo buscarSimblolo(String key)
    {
        Simbolo tmp = tabla_simbolos.get(key);
        if(tmp!=null)
            return tmp;
        return null;
    }
    
    
    
    // *generar codigo 

    
}
